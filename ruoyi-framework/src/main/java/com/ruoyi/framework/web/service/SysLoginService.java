package com.ruoyi.framework.web.service;

import javax.annotation.Resource;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
@Slf4j
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
//        boolean captchaOnOff = configService.selectCaptchaOnOff();
//        // 验证码开关
//        if (captchaOnOff)
//        {
//            validateCaptcha(username, code, uuid);
//        }
        //判断是否存在用户
        LoginUser loginUser = userService.selectUserForLogin(username, null);
        if(loginUser==null || loginUser.getUser() == null){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "查询不到该用户,登录失败"));
            throw new CustomException("查询不到该用户,登录失败");
        }
        if(ObjectUtil.isEmpty(loginUser.getUser().getRoles())){
            throw new CustomException("没有分配角色,请联系管理员");
        }
        //判断用户是否被封禁
        if("1".equals(loginUser.getUser().getStatus())){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,Constants.LOGIN_FAIL,"该用户已被封禁，请联系管理员处理！！！"));
            throw new CustomException("该用户已被封禁，请联系管理员处理！！！");
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser1 = (LoginUser) authentication.getPrincipal();
        if (loginUser.getUser() != null && loginUser.getUser().getRoles() == null){
            loginUser.getUser().setRoles(sysRoleMapper.selectRolePermissionByUserId(loginUser1.getUserId()));
        }
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    public String loginForPda(String userName, String phone, String jobNumber) {
        LoginUser loginUser = userService.selectUserForPda(userName, phone, jobNumber);
        log.info("登录用户:"+loginUser.getPermissions());
        if(loginUser==null || loginUser.getUser() == null || loginUser.getUser().getStatus().equals("1")){
//        if(loginUser==null){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_FAIL, "查询不到该用户,登录失败"));
            throw new CustomException("查询不到该用户,登录失败");
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(user);
    }


}
