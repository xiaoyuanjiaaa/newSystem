package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("PublicApiPersonInfoVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PublicApiPersonInfoVO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证")
    private String userid;

    @ApiModelProperty(value = "码状态1 green 2 yellow 3 red 4 未知")
    private String levelData;

    @ApiModelProperty(value = "苏康码状态核验 非苏康码默认未核验")
    private String xcValue;

    @ApiModelProperty(value = "异常信息")
    private String exceptionCodeReason;

}
