package com.ruoyi.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("出库折线图")
public class MaterialOutputVo {

    @ApiModelProperty(value = "id")
    private Long id;

    /** 物资类别 */
    @ApiModelProperty(value = "出库日期")
    private String outputDate;

    /** 物资名称 */
    @ApiModelProperty(value = "物资名称")
    private Integer number;


}
