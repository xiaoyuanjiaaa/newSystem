package com.ruoyi.system.vo;

import com.ruoyi.system.entity.AppHealthReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("每日填报健康状态查询实体")
@Data
public class AppHealthReportQueryVO extends AppHealthReport {


//    @ApiModelProperty("部门名")
//    private String deptName;

    @ApiModelProperty("部门id")
    private Long deptId;

//    @ApiModelProperty("工号")
//    private String jobNumber;

}
