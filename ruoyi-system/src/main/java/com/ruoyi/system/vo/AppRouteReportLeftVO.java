package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("行程上报人员统计")
public class AppRouteReportLeftVO {

    @ApiModelProperty("行程上报返回list")
    private List<AppRouteReportsVO> appRouteReportsVOS;


    @ApiModelProperty("总人员数量")
    private Integer userCount;

    @ApiModelProperty("已计算人员数量")
    private Integer userCounted;

}
