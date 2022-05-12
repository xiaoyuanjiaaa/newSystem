package com.ruoyi.system.excel;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppHealthReportExcel {

    @Excel(name = "部门")
    @ApiModelProperty("部门")
    private String deptName;

    @Excel(name = "总人数")
    @ApiModelProperty("总人数")
    private Integer totalNum;

    @Excel(name = "已填报")
    @ApiModelProperty("已填报")
    private Integer filled;

    @Excel(name = "未填报")
    @ApiModelProperty(value = "身份证号" )
    private Integer notFilled;

    @Excel(name = "填报率")
    @ApiModelProperty("填报率")
    private String reportingRate;

}
