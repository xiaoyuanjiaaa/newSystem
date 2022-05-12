package com.ruoyi.system.dto;

import java.util.Date;

import com.ruoyi.common.core.domain.BasePageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("查询行程填报对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppRouteReportQueryDTO extends BasePageEntity {

    @ApiModelProperty(value = "人员id", required = false)
    private Long personId;

    @ApiModelProperty(value = "部门id", required = false)
    private Long deptId;

    @ApiModelProperty(value = "行程地址", required = false)
    private String routeAddr;

    @ApiModelProperty(value = "姓名", required = false)
    private String userName;

    @ApiModelProperty(value = "工号", required = false)
    private String jobNumber;

    @ApiModelProperty(value = "审核状态0:待审核,1:审核通过,2:审核不通过", required = false)
    private Integer status;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
