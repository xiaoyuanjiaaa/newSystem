package com.ruoyi.system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("查询AppPersonWx对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxQueryDTO {

    @ApiModelProperty(value = "人员personId", required = false)
    private Long personId;

    @ApiModelProperty(value = "身份证", required = false)
    private String idNum;

    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;

    @ApiModelProperty(value = "姓名", required = false)
    private String personName;

    @ApiModelProperty(value = "1:公众号openId,2:小程序openId")
    private Integer type;

    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "人员id", required = false)
    private Long id;

}
