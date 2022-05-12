package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: jianguo
 * @Date: 2021/10/19 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_register")
public class SysRegister extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ID_WORKER   )
    private Long id;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户名称")
    private String nickName;

    /**
     * 用户账号
     */
    @ApiModelProperty("登录账号")
    private String userName;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号")
    private String phonenumber;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", allowableValues = "0,1,2")
    private String sex;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idNum;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 状态(1:发起 2:驳回)
     */
    @ApiModelProperty("状态(1:发起 2:驳回)")
    private Integer status;

    /**
     * 是否接种疫苗：1-接种 2-未接种
     */
    @ApiModelProperty("是否接种疫苗：1-接种 2-未接种")
    private Integer vaccination;

    /**
     * 第一针接种时间
     */
    @ApiModelProperty("第一针接种时间")
    private String firstStitchTime;

    /**
     * 第二针接种时间
     */
    @ApiModelProperty("第二针接种时间")
    private String twoStitchTime;

    /**
     * 第三针接种时间
     */
    @ApiModelProperty("第三针接种时间")
    private String threeStitchTime;

    /**
     * 所属科室
     */
    @ApiModelProperty("所属科室")
    private String department;

    /**
     * 1:本院职工 2:外包单位 3:临时人员
     */
    @ApiModelProperty("1:本院职工 2:外包单位 3:临时人员")
    private Integer personIdentity;

    /**
     * 岗前培训情况
     */
    @ApiModelProperty("岗前培训情况")
    private String training;


    /**
     * 注册动态json
     */
    @ApiModelProperty("注册动态json")
    private String registerJson;
}
