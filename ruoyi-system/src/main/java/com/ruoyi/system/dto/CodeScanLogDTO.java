package com.ruoyi.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(value = "职工扫码记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeScanLogDTO {

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /** 大门 */
    @ApiModelProperty(value = "大门", required = true)
    private String door;

}
