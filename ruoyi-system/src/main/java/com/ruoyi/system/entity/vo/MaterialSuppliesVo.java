package com.ruoyi.system.entity.vo;

import com.ruoyi.common.annotation.Excel;
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
@ApiModel("物资评估")
public class MaterialSuppliesVo {


    @ApiModelProperty(value = "id")
    private Long id;

    /** 物资类别 */
    @ApiModelProperty(value = "物资类别")
    private String materialCategory;

    /** 物资名称 */
    @ApiModelProperty(value = "物资名称")
    private String materialName;

    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer number;

    @ApiModelProperty(value = "安全储备量")
    private Long safetyReserve;

    @ApiModelProperty(value = "储备天数")
    private Long reserveDays;

    /** 库存状态 */
    @ApiModelProperty(value = "库存状态")
    private String numberStats;

    /** 是否方面短信 */
    private Integer isSend;
}
