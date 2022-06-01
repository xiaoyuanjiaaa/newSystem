package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
@ApiModel("用户")
@TableName("sys_user")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("人员ID")
    @TableId
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门编号", type = Type.IMPORT)
    @ApiModelProperty("部门ID")
    private Long deptId;

    @TableField(exist = false)
    private String deptName;

    /**
     * 用户账号
     */
    @Excel(name = "登录账号")
    @ApiModelProperty("登录账号")
    private String userName;

    /**
     * 工号
     */
    @Excel(name = "工号")
    @ApiModelProperty("工号")
    private String jobNumber;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户名称")
    @Excel(name = "姓名")
    private String nickName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号")
    private String phonenumber;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", allowableValues = "0,1,2")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 盐加密
     */
    @TableField(exist = false)
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "账号状态", allowableValues = "0,1")
    private String status;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idNum;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标识", allowableValues = "0,2")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    @ApiModelProperty("最后一次登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    @TableField(exist = false)
    private SysDept dept;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @ApiModelProperty("角色组")
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @ApiModelProperty("岗位组")
    @TableField(exist = false)
    private Long[] postIds;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @TableField(exist = false)
    private Long roleId;

    /**
     * 人员ID
     */
    @ApiModelProperty("角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long personId;

    /**
     * 是否是自有0：本院，1：非本院
     */
    @ApiModelProperty("是否是自有0：本院，1：非本院")
    @Excel(name = "本院/非本院", readConverterExp = "0=本院,1=非本院")
    private String isPrivate;

    /**
     * 0：临床，1：非临床
     */
    @ApiModelProperty("0：临床，1：非临床")
    @Excel(name = "临床/非临床", readConverterExp = "0=临床,1=非临床")
    private String isClinical;

    /**
     * 0：临时，1：长期
     */
    @ApiModelProperty("0：临时，1：长期")
    @Excel(name = "临时人员/长期员工", readConverterExp = "0=临时人员,1=长期员工")
    private String isTemporary;

    /**
     * A、B、C风险等级
     */
    @ApiModelProperty("风险等级")
    @Excel(name = "风险等级", dictType = "sys_risk_level")
    private String riskLevel;

    /**
     * 职务等级
     */
    @ApiModelProperty("职务等级")
    @Excel(name = "职务等级")
    private String postLevel;

    /**
     * 是梯队id
     */
    @ApiModelProperty("梯队id")
    @Excel(name = "梯队id")
    private Long isEchelon;

    @TableField(exist = false)
    private Long echelonId;

    /**
     * 最后登录时间
     */
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("新增时间")
    private Date createTime;

    /**
     * 最后登录时间
     */
    @Excel(name = "停用时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("停用时间")
    private Date updateTime;


    /**
     * 工作场所
     */
    @Excel(name = "工作场所",prompt = "发热门诊、板房、负压病房 | 普通诊疗区 | 其它(不接触患者或特殊感染环境)")
    @ApiModelProperty(value = "工作场所",
            example = "A.发热门诊、板房、负压病房  B.普通诊疗区  C.其它(不接触患者或特殊感染环境")
    private String workPlace;

    /**
     * 核酸频次
     */
    @ApiModelProperty("核酸频次")
    private String frequency;

    /** 填报判断 **/
    @TableField(exist = false)
    @ApiModelProperty("填报判断")
    private String fillInJudge;

    public Long getEchelonId() {
        return echelonId;
    }

    public void setEchelonId(Long echelonId) {
        this.echelonId = echelonId;
    }

    public String getFillInJudge() {
        return fillInJudge;
    }

    public void setFillInJudge(String fillInJudge) {
        this.fillInJudge = fillInJudge;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SysUser() {

    }


    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getIsEchelon() {
        return isEchelon;
    }

    public void setIsEchelon(Long isEchelon) {
        this.isEchelon = isEchelon;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("jobNumber", getJobNumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("dept", getDept())
                .append("isEchelon", getIsEchelon())
                .append("deptName",getDeptName())
                .append("work_place",getWorkPlace())
                .append("frequency",getFrequency())
                .append("fillInJudge",getFillInJudge())
                .append("echelonId",getEchelonId())
                .toString();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getIsClinical() {
        return isClinical;
    }

    public void setIsClinical(String isClinical) {
        this.isClinical = isClinical;
    }

    public String getIsTemporary() {
        return isTemporary;
    }

    public void setIsTemporary(String isTemporary) {
        this.isTemporary = isTemporary;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }
}
