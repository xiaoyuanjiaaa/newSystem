package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Set;

import com.ruoyi.common.core.domain.model.LoginPdaBody;
import com.ruoyi.common.core.domain.model.LoginPhoneBody;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.statics.ConstantDic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@Api(tags = {"登录接口相关"})
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @PostMapping("/loginForPda")
    @ApiOperation("PDA登录")
    public AjaxResult loginForPda(@RequestBody LoginPdaBody loginPdaBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.loginForPda(loginPdaBody.getUserName(), loginPdaBody.getPhone(), loginPdaBody.getJobNumber());
        ajax.put(Constants.TOKEN, token);
        // 记录值班点登录记录
        return ajax;
    }

    @PostMapping("/loginForPhone")
    @ApiOperation("手机号和验证码登录")
    public AjaxResult loginForPhone(@RequestBody LoginPhoneBody loginPhoneBody)
    {
        AjaxResult ajax = AjaxResult.success();
        if(env.equals("prod")){
            String key = ConstantDic.SMS_REGISTER_PREFIX + loginPhoneBody.getPhone();
            String code = redisTemplate.opsForValue().get(key)+"";
            System.out.println("-----验证码----"+code);
            // TODO 短信验证码登录
            if(CheckUtil.NullOrEmpty(code) || !loginPhoneBody.getVerificationCode().equals(code)){
                throw new CustomException("短信验证码不匹配");
            }
        }
        // 生成令牌
        String token = loginService.loginForPda(null, loginPhoneBody.getPhone(), null);
        ajax.put(Constants.TOKEN, token);
        // 记录值班点登录记录
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        //判断是否是默认密码
        if(SecurityUtils.matchesPassword("123456",user.getPassword())){
            ajax.put("isDefault", true);
        }else{
            ajax.put("isDefault", false);
        }
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
