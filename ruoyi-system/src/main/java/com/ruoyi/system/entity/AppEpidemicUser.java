package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 【请填写功能名称】对象 app_epidemic_user
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("防疫人员管理")
@TableName("app_epidemic_user")
public class AppEpidemicUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 用户id */
    private Long userId;

    /** 用户姓名 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户电话 */
    private String phone;

    /** 工作状态，1：负压 2：隔离 3：正常 无则为0 */
    private Integer workStatus;

    /** 工作地址 */
    private String workAddress;

    /** 删除状态 */
    @TableLogic
    private Integer isDeleted;

    /** 进入时间 */
    private String inTime;

    /** 建议出去时间 */
    private String suggestOutTime;

    /** 梯队id  **/
    private Long echelonId;

    /** 梯队名称 **/
    private String echelonName;

    /** 部门id **/
    private Long deptId;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<AppRosterUser> appRosterUserList;

    @TableField(exist = false)
    private String rest;

}
