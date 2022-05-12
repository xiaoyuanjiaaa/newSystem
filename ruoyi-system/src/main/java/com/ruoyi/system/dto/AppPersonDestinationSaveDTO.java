package com.ruoyi.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@ApiModel("新增AppPersonDestination对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonDestinationSaveDTO {

    @NotEmpty(message = "值班地点ID不能为空")
    @ApiModelProperty(value = "值班地点ID", required = true)
    private Long destinationId;

    @NotEmpty(message = "人员基础信息ID不能为空")
    @ApiModelProperty(value = "人员基础信息ID", required = true)
    private Long personId;

    @NotEmpty(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

}
