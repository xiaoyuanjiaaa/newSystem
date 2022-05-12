package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("人员流动统计")
public class AppRouteReportsVO {

    @ApiModelProperty("地点")
    private String place;
    @ApiModelProperty("用户数量")
    private Integer userCount;


}
