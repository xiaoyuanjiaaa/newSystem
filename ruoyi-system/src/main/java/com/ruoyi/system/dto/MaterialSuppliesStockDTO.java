package com.ruoyi.system.dto;

import lombok.Data;

/**
 * 大屏-物资库存
 */
@Data
public class MaterialSuppliesStockDTO {

    private Long id;
    /**
     * 物资名称
     */
    private String materialName;
    /**
     * 物资数量
     */
    private Integer number;
}
