package com.ruoyi.system.dto;

import lombok.Data;

/***
 * 物资储备/出库详情
 */
@Data
public class MaterialSuppliesDTO {

    private Long id;

    /**
     * 物资名称
     */
    private String materialName;

    /**
     * 物资数量
     */
    private Integer number;

    /**
     * 物资可用天数
     */
    private Integer availableDays;

    /**
     * 物资状态
     */
    private String state;
}
