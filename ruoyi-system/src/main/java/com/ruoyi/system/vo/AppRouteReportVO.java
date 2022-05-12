package com.ruoyi.system.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("AppRouteReportVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppRouteReportVO {
    @ApiModelProperty(value = "行程填报ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long routeId;

    @ApiModelProperty(value = "计划行程开始时间")
    private Date routeTime;

    @ApiModelProperty(value = "行程地址")
    private String routeAddr;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "手机号")
    private String mobile;

}
