package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("人员体温检查")
@Data
public class PersonTemperatureVO {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("工号")
    private String jobNumber;

    @ApiModelProperty("部门名")
    private String deptName;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("今日体温")
    private String temperature;

    @ApiModelProperty("工作区域")
    private String destinationName;

    @ApiModelProperty("14日体温趋势")
    private List<PersonTemperature> temperatures;



}


