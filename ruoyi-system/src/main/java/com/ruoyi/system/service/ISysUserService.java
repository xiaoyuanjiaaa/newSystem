package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.HospitalPersonnelDTO;
import com.ruoyi.system.dto.SysUserDTO;
import com.ruoyi.system.entity.SysRegister;
import com.ruoyi.system.vo.CountUserGroupDept;
import org.springframework.stereotype.Service;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface ISysUserService extends IService<SysUser>
{
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * pda查询用户信息
     * @param userName
     * @param phone
     * @param jobNumber
     * @return
     */
    public LoginUser selectUserForPda(String userName, String phone, String jobNumber);

    /**
     * pda查询用户信息
     * @param username
     * @param password
     * @return
     */
    public LoginUser selectUserForLogin(String username, String password);
    /**
     * 通过jobNumber查询用户信息
     * @param jobNumber
     * @return
     */
    public SysUser selectUserByJonNumber(String jobNumber);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户id集合查询对应的用户集合
     * @param userIds
     * @return
     */
    public List<SysUser> selectUserByIds(List<Long> userIds);
    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserPostGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public String checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserStatus(SysUser user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserProfile(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetPwd(SysUser user);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String userName, String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    List<CountUserGroupDept> countUserGroupDept(AppHealthReportCountDTO countDTO);
    /**
     * 通过手机号码查询用户信息
     * @param phone
     * @return
     */
    SysUser selectUserByUserPhone(String phone);

    /**
     * 通过personId查询用户信息
     * @param personId
     * @return
     */
    List<SysUser> selectUserByPersonId(Long personId);
    SysUser getUserInfo(String name, String idNum, String mobile);

    //分页
    IPage<SysUser> pageList(SysUserDTO sysUserDTO);

    /**
     * 逻辑删除
     * @param userIds
     * @return
     */
    int deleteByIds(Long[] userIds);

    /**
     * 启用禁用
     * @param userId
     * @return
     */
    String enable(Long userId);

    /**
     * 获取可加入梯队人员信息
     * @param sysUser
     * @return
     */
    List<SysUser> getEcUser(SysUser sysUser);

    int updateUserSetEL(Long userId);

    public List<SysUser> selectUserRosterList(SysUser user);

    /**
     * 用户注册
     * @param userRegister
     */
    Boolean register(SysRegister userRegister);

    /**
     * 修改注册信息状态
     * @param userRegister
     * @return
     */
    Boolean updateRegister(SysRegister userRegister);


    int resetUsersPwd(SysUser user);
}
