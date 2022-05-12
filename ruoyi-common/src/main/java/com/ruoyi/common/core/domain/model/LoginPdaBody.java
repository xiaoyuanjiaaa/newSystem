package com.ruoyi.common.core.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * PDA登录对象
 * 
 * @author ruoyi
 */
@Data
public class LoginPdaBody
{
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户姓名", required = true)
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", required = false)
    private String jobNumber;

    /**
     * 工号
     */
    @ApiModelProperty(value = "值班点id", required = true)
    private Long destinationId;

}
