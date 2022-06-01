package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.IdCardUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.SysUserDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.excel.SysUserExcel;
import com.ruoyi.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = {"用户信息"})
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WorkPlaceService workPlaceService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IAppHealthReportService appHealthReportService;

    @Autowired
    private IRegisterDataService registerDataService;


    /**
     * 获取用户列表
     */
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
//    @GetMapping("/list")
//    public ResultVO<IPage<SysUser>> list(SysUserDTO sysUserDTO)
//    {
//        startPage();
//        List<SysUser> list = userService.selectUserList(user);
////        return new ResultVO<IPage<SysUser>>(SuccessEnums.QUERY_SUCCESS,userService.pageList(sysUserDTO));
//        return getDataTable(list);
//    }

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        Long deptId = user.getDeptId();
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if(sysDept==null){
                        throw new CustomException("部门管理员请先绑定片区");
                    }else if(sysDept!=null) {
                        user.setDeptId(sysDept.getDeptId());
                    }
                }else {
                    user.setDeptId(null);
                }
            }
        }
        //如果传了deptId，强制赋值
        if(deptId!=null){
            user.setDeptId(deptId);
        }
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }


    /**
     * 获取用户
     */
    @GetMapping("/getUsers")
    public AjaxResult list(String name)
    {
        List<SysUser> list = userService.list(new QueryWrapper<SysUser>().like(StringUtils.isNotEmpty(name),"nick_name",name).eq("status","0").last("limit 20"));
        return AjaxResult.success(list);
    }

    /**
     * 获取用户
     */
    @GetMapping("/getEcUser")
    public TableDataInfo getEcUser(SysUser sysUser)
    {
        startPage();
        List<SysUser> list  = userService.getEcUser(sysUser);
        return getDataTable(list);
    }

    @ApiOperation("根据手机号获取用户信息")
    @GetMapping("/getUserInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String"),
            @ApiImplicitParam(name = "idNum", value = "身份证号", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String")
    })
    public ResultVO<SysUser> getUserInfo(String name, String idNum, String mobile) {
        SysUser list = userService.getUserInfo(name, idNum, mobile);
        return new ResultVO<SysUser>(SuccessEnums.QUERY_SUCCESS, list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    public AjaxResult export(SysUser user)
    {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    user.setDeptId(sysDept.getDeptId());
                }else {
                    user.setDeptId(null);
                }
            }
        }
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser sysUser : list) {
            if (sysUser.getWorkPlace() != null){
                WorkPlaceFrequency workPlaceFrequency = workPlaceService.
                        getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", sysUser.getWorkPlace()));
                if (workPlaceFrequency != null){
                    sysUser.setWorkPlace(workPlaceFrequency.getWorkPlaceName());
                }
            }
        }
        List<SysUserExcel> sysUserExcelList=new ArrayList<>();
        for (SysUser sysUser : list) {
//            SysUserExcel sysUserExcel=new SysUserExcel();
            if("1".equals(sysUser.getStatus())){
                SysUserExcel sysUserExcel=new SysUserExcel();
                sysUserExcel.setUserName(sysUser.getNickName());
                sysUserExcel.setJobNumber(sysUser.getJobNumber());
                sysUserExcel.setPhonenumber(sysUser.getPhonenumber());
                sysUserExcel.setIdNum(sysUser.getIdNum());
                sysUserExcel.setLoginIp(sysUser.getLoginIp());
                sysUserExcel.setLoginDate(sysUser.getLoginDate());
                sysUserExcel.setDept(sysUser.getDept());
                sysUserExcel.setIsPrivate(sysUser.getIsPrivate());
                sysUserExcel.setIsClinical(sysUser.getIsClinical());
                sysUserExcel.setIsTemporary(sysUser.getIsTemporary());
                sysUserExcel.setRiskLevel(sysUser.getRiskLevel());
                sysUserExcel.setPostLevel(sysUser.getPostLevel());
                sysUserExcel.setCreateTime(sysUser.getCreateTime());
                sysUserExcel.setUpdateTime(sysUser.getUpdateTime());
                sysUserExcel.setWorkPlace(sysUser.getWorkPlace());
                sysUserExcelList.add(sysUserExcel);
            }else if("0".equals(sysUser.getStatus())){
                SysUserExcel sysUserExcel1=new SysUserExcel();
                sysUserExcel1.setUserName(sysUser.getNickName());
                sysUserExcel1.setJobNumber(sysUser.getJobNumber());
                sysUserExcel1.setPhonenumber(sysUser.getPhonenumber());
                sysUserExcel1.setIdNum(sysUser.getIdNum());
                sysUserExcel1.setLoginIp(sysUser.getLoginIp());
                sysUserExcel1.setLoginDate(sysUser.getLoginDate());
                sysUserExcel1.setDept(sysUser.getDept());
                sysUserExcel1.setIsPrivate(sysUser.getIsPrivate());
                sysUserExcel1.setIsClinical(sysUser.getIsClinical());
                sysUserExcel1.setIsTemporary(sysUser.getIsTemporary());
                sysUserExcel1.setRiskLevel(sysUser.getRiskLevel());
                sysUserExcel1.setPostLevel(sysUser.getPostLevel());
                sysUserExcel1.setCreateTime(sysUser.getCreateTime());
                sysUserExcel1.setWorkPlace(sysUser.getWorkPlace());
                sysUserExcelList.add(sysUserExcel1);
            }
        }
        ExcelUtil<SysUserExcel> util = new ExcelUtil<SysUserExcel>(SysUserExcel.class);
        return util.exportExcel(sysUserExcelList, "用户数据");
    }
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/exportList")
    public AjaxResult exportList(SysUser user)
    {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    user.setDeptId(sysDept.getDeptId());
                }else {
                    user.setDeptId(null);
                }
            }
        }
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser sysUser : list) {
            if (sysUser.getWorkPlace() != null){
                WorkPlaceFrequency workPlaceFrequency = workPlaceService.
                        getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", sysUser.getWorkPlace()));
                if (workPlaceFrequency != null){
                    sysUser.setWorkPlace(workPlaceFrequency.getWorkPlaceName());
                }
            }
        }
        List<SysUserExcel> sysUserExcelList=new ArrayList<>();
        for (SysUser sysUser : list) {
//            SysUserExcel sysUserExcel=new SysUserExcel();
            if("1".equals(sysUser.getStatus())){
                SysUserExcel sysUserExcel=new SysUserExcel();
                sysUserExcel.setUserName(sysUser.getNickName());
                sysUserExcel.setJobNumber(sysUser.getJobNumber());
                sysUserExcel.setPhonenumber(sysUser.getPhonenumber());
                sysUserExcel.setIdNum(sysUser.getIdNum());
                sysUserExcel.setLoginIp(sysUser.getLoginIp());
                sysUserExcel.setLoginDate(sysUser.getLoginDate());
                sysUserExcel.setDept(sysUser.getDept());
                sysUserExcel.setIsPrivate(sysUser.getIsPrivate());
                sysUserExcel.setIsClinical(sysUser.getIsClinical());
                sysUserExcel.setIsTemporary(sysUser.getIsTemporary());
                sysUserExcel.setRiskLevel(sysUser.getRiskLevel());
                sysUserExcel.setPostLevel(sysUser.getPostLevel());
                sysUserExcel.setCreateTime(sysUser.getCreateTime());
                sysUserExcel.setUpdateTime(sysUser.getUpdateTime());
                sysUserExcel.setWorkPlace(sysUser.getWorkPlace());
                sysUserExcelList.add(sysUserExcel);
            }else if("0".equals(sysUser.getStatus())){
                SysUserExcel sysUserExcel1=new SysUserExcel();
                sysUserExcel1.setUserName(sysUser.getNickName());
                sysUserExcel1.setJobNumber(sysUser.getJobNumber());
                sysUserExcel1.setPhonenumber(sysUser.getPhonenumber());
                sysUserExcel1.setIdNum(sysUser.getIdNum());
                sysUserExcel1.setLoginIp(sysUser.getLoginIp());
                sysUserExcel1.setLoginDate(sysUser.getLoginDate());
                sysUserExcel1.setDept(sysUser.getDept());
                sysUserExcel1.setIsPrivate(sysUser.getIsPrivate());
                sysUserExcel1.setIsClinical(sysUser.getIsClinical());
                sysUserExcel1.setIsTemporary(sysUser.getIsTemporary());
                sysUserExcel1.setRiskLevel(sysUser.getRiskLevel());
                sysUserExcel1.setPostLevel(sysUser.getPostLevel());
                sysUserExcel1.setCreateTime(sysUser.getCreateTime());
                sysUserExcel1.setWorkPlace(sysUser.getWorkPlace());
                sysUserExcelList.add(sysUserExcel1);
            }
        }
        ExcelUtil<SysUserExcel> util = new ExcelUtil<SysUserExcel>(SysUserExcel.class);
        return AjaxResult.success("用户数据", JSON.toJSONString(sysUserExcelList));
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        for (SysUser sysUser : userList) {
            if (sysUser.getWorkPlace() != null){
                SysDictData sysDictData  = dictDataService.selectBylabel(sysUser.getWorkPlace());
                if (sysDictData != null){
                    sysUser.setWorkPlace(sysDictData.getDictValue());
                }
            }
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }


    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        if (user.getDeptId() == null){
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，请选择部门");
        }
        if (!IdCardUtil.isValidCard(user.getIdNum())){
            return AjaxResult.error("新增用户'" + user.getIdNum() + "'失败，身份证号非法");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user){
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        if (!IdCardUtil.isValidCard(user.getIdNum())){
            return AjaxResult.error("修改用户'" + user.getIdNum() + "'失败，身份证号非法");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
//    @PreAuthorize("@ss.hasPermi('system:user:remove')")
//    @Log(title = "用户管理", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{userIds}")
//    public AjaxResult remove(@PathVariable Long[] userIds)
//    {
//        if (ArrayUtils.contains(userIds, getUserId()))
//        {
//            return error("当前用户不能删除");
//        }
//        return toAjax(userService.deleteUserByIds(userIds));
//    }

    /**
     * 逻辑删除
     * @param userIds
     * @return
     */
    // TODO 逻辑删除人员
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public ResultVO<Boolean> removeTwo(@PathVariable Long[] userIds)
    {
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return new ResultVO<Boolean>(FailEnums.DELETE_FAIL);
        }
        int i = userService.deleteByIds(userIds);
        if (i == 1){
            return new ResultVO<Boolean>(SuccessEnums.DELETE_SUCCESS,true);
        }
        return new ResultVO<Boolean>(FailEnums.DELETE_FAIL);
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }
    /**
     * 批量重置密码
     * 不可重置admin的密码
     * @data 2022.5.26
     */
//    @PreAuthorize("@ss.hasPermi('system:user:resetPwds')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping ("/resetPwds")
    public ResultVO resetPwds(@RequestParam String userIds) {
        //判断是否为admin
//        boolean admin = SecurityUtils.getLoginUser().getUser().isAdmin();
//        if(!admin){
//            throw new CustomException("没有权限操作");
//        }
        List<String> ids=Arrays.asList(userIds.split(","));
        if(ids.contains("1")){
            return new ResultVO<>(FailEnums.NOT_RESET_PASWORD);
        }
        for(String str:ids){
            SysUser user = new SysUser();
            Long userId=Long.parseLong(str);
            user.setUserId(userId);
            user.setPassword(SecurityUtils.encryptPassword("123456"));
            userService.resetUsersPwd(user);
        }
        return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    /**
     * 启用/禁用
     * @param userId
     * @return
     */
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(Long userId)
    {
        String status = userService.enable(userId);
        return AjaxResult.success(status);
    }


    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysRegister userRegister){
        if (userRegister.getId() == null){
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(userRegister,sysUser);
            if (StringUtils.isNotEmpty(userRegister.getPhonenumber())
                    && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(sysUser)))
            {
                return AjaxResult.error("用户注册'" + userRegister.getNickName() + "'失败，手机号码已存在");
            }
            else if (StringUtils.isNotEmpty(userRegister.getEmail())
                    && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(sysUser)))
            {
                return AjaxResult.error("用户注册'" + userRegister.getNickName() + "'失败，邮箱账号已存在");
            }
            if (!IdCardUtil.isValidCard(userRegister.getIdNum())){
                return AjaxResult.error("用户注册'" + userRegister.getNickName() + "'失败，身份证号非法");
            }
            //获取第一针接种时间
            String firstStitchTime = userRegister.getFirstStitchTime ();
            //获取第二针接种时间
            String twoStitchTime = userRegister.getTwoStitchTime ();
            //获取第三针接种时间
            String threeStitchTime = userRegister.getThreeStitchTime ();
            //比较第一针,第二针接种时间接种时间,第二针接种时间早于第一针接种时间compare小于0
            int compare = twoStitchTime.compareTo (firstStitchTime);
            //比较第二针,第三针接种时间接种时间,第三针接种时间早于第二针接种时间compare1小于0
            int compare1 = threeStitchTime.compareTo (twoStitchTime);
            if ( compare <= 0 || compare1 <= 0 ){
                //返回异常信息
                throw  new CustomException("疫苗接种时间应不早于上一针时间!!!");
            }
            userRegister.setCreateTime(new Date());
            userRegister.setUpdateTime(new Date());
        }
        if (userRegister.getId() != null){
            userRegister.setUpdateTime(new Date());
        }
        Boolean register = userService.register(userRegister);
        //数据解析
        registerDataService.jsonTransToDB(userRegister);
        return AjaxResult.success(register);
    }

    @PostMapping("/pass")
    public AjaxResult pass(@RequestBody SysRegister userRegister){
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userRegister,user);
        user.setUserName(userRegister.getNickName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getNickName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getNickName() + "'失败，邮箱账号已存在");
        }
        if (!IdCardUtil.isValidCard(user.getIdNum())){
            return AjaxResult.error("新增用户'" + user.getIdNum() + "'失败，身份证号非法");
        }
        Boolean b = userService.updateRegister(userRegister);
        if (b){
            return toAjax(userService.insertUser(user));
        }
        throw new CustomException("注册信息修改失败");
    }
//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
////  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//        params.add("url","https://h5.dingtalk.com/healthAct/index.html?qrCode=V5742bf7487ae983392a0f72dc083b66d3320000&b=0SbR0zUpbf53IESIz6UtcEUX9Pu0v0yZhuk9vyWvyw8rU65Ys493L9xjpKXfw5XJ#/result");
//        params.add("source","hospital_wuXiDiWu");
//        params.add("position","江苏省,无锡市,梁溪区,广瑞路1215号");
//        params.add("verifierDept","无锡市第五人民医院");
//        params.add("verifierName","梁皓");
//        params.add("verifierPhone","15295537017");
//        params.add("token","nrj40iNUhzmw7tdJvEaxBh8iqGAEF20JnyhcxSmzjVXKkKRpHOD5qlMM+5cClCuy");
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//        ResponseEntity<String> response  = restTemplate.exchange("https://ht-zl.com/5h/jkm/", HttpMethod.POST,requestEntity,String.class);
//        System.out.println(response.getBody());
//    }

    /**
     * 获取用户列表
     */
    @GetMapping("/listByphone")
    public TableDataInfo listByphone(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

}
