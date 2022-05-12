package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@ApiModel("人员体温")
public class PersonTemperature{

    @ApiModelProperty("时间")
    private Date date;
    @ApiModelProperty("温度")
    private String temperature;

}
