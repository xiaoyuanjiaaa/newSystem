package com.ruoyi.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询实体")
public class AppTemplateDetailQueryDTO {

    @ApiModelProperty("是否启用")
    private String isEnabled;
}
