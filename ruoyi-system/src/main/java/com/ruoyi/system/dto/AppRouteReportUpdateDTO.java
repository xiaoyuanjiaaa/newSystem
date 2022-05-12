package com.ruoyi.system.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改AppRouteReport对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppRouteReportUpdateDTO {
    @ApiModelProperty(value = "行程填报ID", required = true)
    private List<Long> routeIds;

    @ApiModelProperty(value = "审核状态0:待审核,1:审核通过,2:审核不通过", required = true)
    private Long status;

    @ApiModelProperty(value = "更新者", required = true)
    private String updateBy;

}
