package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/10/19 16:03
 */
@Data
public class SysRegisterDto extends PageDomain {

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
}
