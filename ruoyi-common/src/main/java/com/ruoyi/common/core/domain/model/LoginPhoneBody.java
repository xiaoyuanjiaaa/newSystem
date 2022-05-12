package com.ruoyi.common.core.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * PDA登录对象
 * 
 * @author ruoyi
 */
@Data
public class LoginPhoneBody
{

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;


}
