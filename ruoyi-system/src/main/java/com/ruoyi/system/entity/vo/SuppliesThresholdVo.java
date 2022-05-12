package com.ruoyi.system.entity.vo;

import lombok.Data;

/**
 * 查询出物资的阈值为 大屏-物资储备/出库详情用
 */
@Data
public class SuppliesThresholdVo {

    private Long id ;

    private String materialName;

    private Integer number;

    private Integer safetyReserve;

    private Integer evaluateDays;

    private String materialCategory;

    private String specifications;

    private double numbers;
}
