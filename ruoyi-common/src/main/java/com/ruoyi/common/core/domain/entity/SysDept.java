package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 部门表 sys_dept
 *
 * @author ruoyi
 */
@ApiModel("部门")
public class SysDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门ID */
    @ApiModelProperty("部门id")
    private Long deptId;

    /** 父部门ID */
    @ApiModelProperty("父级部门id")
    private Long parentId;


    /**
     * 祖级列表
     */
    @ApiModelProperty("祖级列表")
    private String ancestors;


    /** 部门名称 */
    @ApiModelProperty("部门名称")
    private String deptName;

    /** 显示顺序 */
    @ApiModelProperty("显示顺序")
    private String orderNum;

    /** 负责人ID */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("负责人ID")
    private Long leaderId;

    /** 负责人 */
    @ApiModelProperty("负责人")
    private String leader;

    /** 联系电话 */
    @ApiModelProperty("联系人电话")
    private String phone;

    /** 邮箱 */
    @ApiModelProperty("邮箱")
    private String email;

    /** 部门状态:0正常,1停用 */
    @ApiModelProperty(value = "部门状态",allowableValues = "0,1")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "删除标识",allowableValues = "0,2")
    private String delFlag;

    /** 父部门名称 */
    @ApiModelProperty("父部门名称")
    @TableField(exist = false)
    private String parentName;

    /** 等级 */
    @ApiModelProperty("等级")
    private String level;

    /** 子部门 */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<SysDept>();

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<SysDept> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDept> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("deptName", getDeptName())
            .append("orderNum", getOrderNum())
            .append("leaderId", getLeaderId())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
