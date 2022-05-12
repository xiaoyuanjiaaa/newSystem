package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("新增AppRouteReport对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppRouteReportSaveDTO {

    @NotEmpty(message = "计划行程开始时间不能为空")
    @ApiModelProperty(value = "计划行程开始时间", required = true)
    private String routeTime;

    @ApiModelProperty(value = "人员id", required = true)
    private Long personId;

    @NotEmpty(message = "行程地址不能为空")
    @ApiModelProperty(value = "行程地址", required = true)
    private String routeAddr;

    @NotEmpty(message = "审核状态不能为空")
    @ApiModelProperty(value = "审核状态0:待审核,1:审核通过,2:审核不通过", required = true)
    private Long status;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @NotEmpty(message = "更新者不能为空")
    @ApiModelProperty(value = "更新者", required = false)
    private String updateBy;

    @NotEmpty(message = "创建者不能为空")
    @ApiModelProperty(value = "创建者", required = true)
    private String createBy;

}
