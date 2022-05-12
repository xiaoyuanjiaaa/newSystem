package com.ruoyi.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("扫码获取用户基本信息入参实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonQueryByCodeDTO {

    @ApiModelProperty(value = "扫苏康码获取的url,二维码获取的字符串,员工号", required = true)
    private String urlContent;

    @ApiModelProperty(value = "当前登录用户的userId", required = true)
    private Long userId;

    @ApiModelProperty(value = "区别码的类型1:苏康码,2:二维码,3:员工码", required = true)
    private Integer type;
}
