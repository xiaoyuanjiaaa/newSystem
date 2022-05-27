package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.IdCardUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.HospitalPersonnelDTO;
import com.ruoyi.system.dto.SysUserDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.CountUserGroupDept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private IAppNotReportedService appNotReportedService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private AppPersonService appPersonService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private AppHealthReportMapper reportMapper;
    @Autowired
    private IAppChangeReportService appChangeReportService;

    @Autowired
    private IAppHealthReportService appHealthReportService;

    @Autowired
    private ISysRegisterService registerService;

    @Autowired
    private IAppEpidemicUserService appEpidemicUserService;

    @Autowired
    private IAppRosterUserService rosterUserService;

    @Autowired
    private AppNotReportedMapper appNotReportedMapper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * pda查询用户信息
     * @param userName
     * @param phone
     * @param jobNumber
     * @return
     */
    @Override
    public LoginUser selectUserForPda(String userName, String phone, String jobNumber)
    {
        SysUser sysUser = userMapper.selectUserForPda(userName,phone,jobNumber);
        if(sysUser==null){
            throw new CustomException("查询不到该用户,登录失败");
        }
        return createLoginUser(sysUser);
    }

    @Override
    public SysUser selectUserByJonNumber(String jobNumber) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.select("user_id","dept_id","user_name","nick_name","phonenumber","job_number","person_id");
        queryWrapper.eq("job_number",jobNumber);
        return getOne(queryWrapper);
    }

    public LoginUser createLoginUser(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()){
            perms.add("*:*:*");
        }else{
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return new LoginUser(user.getUserId(), user.getDeptId(), user, perms);
    }
    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<SysUser> selectUserByIds(List<Long> userIds) {
        return userMapper.selectUserByIds(userIds);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName)
    {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        user.setPassword("123456");
        user.setCreateBy(getUsername());
        user.setCreateTime(new Date());
        user.setStatus(null);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        //新增人员信息
//        if (StringUtils.isNotBlank(user.getIdNum())){
        Long personId = appPersonService.getPersonInfo(user.getIdNum(),user.getUserName(),user.getPhonenumber(), user.getDeptId());
        user.setPersonId(personId);
        user.setDelFlag("0");
//        }
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        SysUser sysUser = userMapper.selectUserById(user.getUserId());
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        //去除新增数据插入提示表
        if (rows == 1 && !"1".equals(user.getStatus())){
            appNotReportedService.saveData(user);
        }
        if(rows ==1 && !"1".equals(user.getStatus())){
            appChangeReportService.addChange(sysUser);
        }
        return rows;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {

        Long userId = user.getUserId();
        SysUser sysUser = selectUserById(userId);
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
//        if ("0".equals(user.getStatus())){
//            // 启用 需要新增未填报的数据
//        }
//        if("1".equals(user.getStatus())){
        Long[] ids = new Long[]{userId};
//            //  禁用 删除数据
        appNotReportedService.deleteDatas(ids);
//        appNotReportedService.saveData(user);
//        }
//        int num=0;
//        int i = userMapper.updateUser(user);
//        SysUser sysUser = userMapper.selectUserById(user.getUserId());
//        if(i==1){
//           num= appChangeReportService.updateChange(sysUser);
//        }
        int a = userMapper.updateUser(user);
        // 修改关联表数据
        if (a != 0){
            // 梯队人员信息
            if (!user.getNickName().equals(sysUser.getNickName()) ||
                    !user.getUserName().equals(sysUser.getUserName()) ||
               !user.getPhonenumber().equals(sysUser.getPhonenumber())){
                List<AppEpidemicUser> appEpidemicUsers = appEpidemicUserService.
                        list(new QueryWrapper<AppEpidemicUser>().eq("user_id", user.getUserId()));
                if (!CollectionUtils.isEmpty(appEpidemicUsers)){
                    for (AppEpidemicUser appEpidemicUser : appEpidemicUsers) {
                        appEpidemicUser.setUserName(user.getUserName());
                        appEpidemicUser.setNickName(user.getNickName());
                        appEpidemicUser.setPhone(user.getPhonenumber());
                    }
                    boolean b = appEpidemicUserService.updateBatchById(appEpidemicUsers);
                    if (!b){
                        throw new CustomException("修改梯队人员信息失败");
                    }
                    List<AppRosterUser> rosterUsers = rosterUserService.
                            list(new QueryWrapper<AppRosterUser>().eq("user_id", user.getUserId()));
                    if (!CollectionUtils.isEmpty(rosterUsers)){
                        for (AppRosterUser rosterUser : rosterUsers) {
                            rosterUser.setNikeName(user.getNickName());
                        }
                        boolean b1 = rosterUserService.updateBatchById(rosterUsers);
                        if (!b1){
                            throw new CustomException("修改人员排班信息失败");
                        }
                    }
                }
            }
            // 填报表数据
            if (!user.getNickName().equals(sysUser.getNickName()) ||
                    !user.getIdNum().equals(sysUser.getIdNum())){
                AppHealthReport appHealthReport = new AppHealthReport();
                appHealthReport.setReportName(user.getNickName());
                appHealthReport.setIdNum(user.getIdNum());
                appHealthReport.setPersonId(user.getPersonId());
                if (!CollectionUtils.isEmpty(appHealthReportService.
                        list(new QueryWrapper<AppHealthReport>().eq("person_id",user.getPersonId())))){
                    int i = appHealthReportService.updateByPersonId(appHealthReport);
                    if (i == 0){
                        throw  new CustomException("修改填报信息失败");
                    }
                }
            }
            return a;
        }
        return a;
    }

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotNull(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
//                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                SysUser u1 = userMapper.selectUserByPhone(user.getPhonenumber());
                if (StringUtils.isNull(u1))
                {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    Long personId = appPersonService.getPersonInfo(user.getIdNum(),user.getUserName(),user.getPhonenumber(), user.getDeptId());
                    user.setPersonId(personId);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "手机号 " + user.getPhonenumber() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "手机号 " + user.getPhonenumber() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum +":"+user.getPhonenumber() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、手机号 " + user.getPhonenumber() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");


        }
        return successMsg.toString();
    }




    @Override
    public List<CountUserGroupDept> countUserGroupDept(AppHealthReportCountDTO countDTO) {
        return userMapper.countUserGroupDept(countDTO);
    }

    @Override
    public SysUser selectUserByUserPhone(String phone) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phoneNumber",phone);
//        queryWrapper.eq("del_flag",0);
//        queryWrapper.eq("status",0);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysUser> selectUserByPersonId(Long personId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u.person_id",personId);
        queryWrapper.eq("u.del_flag",0);
        queryWrapper.eq("u.status",0);
        return userMapper.selectByQueryWrapper(queryWrapper);
    }

    @Override
    public SysUser getUserInfo(String name, String idNum, String mobile) {
        return userMapper.getUserInfo(name, idNum, mobile);
    }

    @Override
    public IPage<SysUser> pageList(SysUserDTO sysUserDTO) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        if (sysUserDTO.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("create_time");
        }else {
            queryWrapper.orderByAsc("create_time");
        }
        IPage<SysUser> page = new Page<>();
        page.setCurrent(sysUserDTO.getPageNum());
        page.setSize(sysUserDTO.getPageSize());
        IPage <SysUser> rsult = page(page,queryWrapper);
        return rsult;
    }

    @Override
    @Transactional
    public int deleteByIds(Long[] userIds) {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        List<SysUser> sysUsers = baseMapper.selectUserByIds(Arrays.asList(userIds));
        List<Long> personIds = sysUsers.stream().map(sysUser -> sysUser.getPersonId()).collect(Collectors.toList());
        reportMapper.deleteByPersonIds(personIds);
        int i = baseMapper.deleteByIds(userIds);
        if (i == 1){
          appNotReportedService.deleteDatas(userIds);
        }
        return i;
    }

    @Override
    @Transactional
    public String enable(Long userId) {
        SysUser sysUser = selectUserById(userId);
        String status = sysUser.getStatus();
        // 禁用 需要删除未填报的数据
        if ("0".equals(status)){
            boolean update = update(new UpdateWrapper<SysUser>().eq("user_id", userId).set("status", "1").set("update_time",new Date()));
            if (!update){
                throw new CustomException("禁用失败");
            }
            Long[] ids = new Long[]{userId};
            appNotReportedService.deleteDatas(ids);
            appChangeReportService.updateChange(sysUser);
        }
        // 启用 需要新增未填报的数据
        if("1".equals(status)){
            boolean update = update(new UpdateWrapper<SysUser>().eq("user_id", userId).set("status", "0").set("update_time",new Date()));
            if (!update){
                throw new CustomException("启用失败");
            }
            //启用不生成填报数据
//            appNotReportedService.saveData(sysUser);
            appChangeReportService.addChange(sysUser);
        }
        return status;
    }

    @Override
    public List<SysUser> getEcUser(SysUser sysUser) {
        List<SysUser> list = baseMapper.getEcUser(sysUser.getDeptId(),sysUser.getNickName(),sysUser.getEchelonId());
        for (SysUser user : list) {
            user.setDept(deptMapper.selectDeptById(user.getDeptId()));
        }
        return list;
    }

    @Override
    public int updateUserSetEL(Long userId) {
        return baseMapper.updateUserSetEL(userId);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserRosterList(SysUser user)
    {
        return userMapper.selectUserRosterList(user);
    }

    @Override
    @Transactional
    public Boolean register(SysRegister userRegister) {
        if (userRegister.getStatus() == null){
            userRegister.setStatus(1);
        }
        Boolean save = registerService.saveOrUpdate(userRegister);
        return save;
    }

    @Override
    @Transactional
    public Boolean updateRegister(SysRegister userRegister) {
        userRegister.setStatus(3);
        boolean b = registerService.updateById(userRegister);
        return b;
    }



}
