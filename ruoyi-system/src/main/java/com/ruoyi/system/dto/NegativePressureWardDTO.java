package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class NegativePressureWardDTO {

    /**
     * 当天
     */
    private Integer today;

    /**
     * 本周
     */
    private Integer week;

    /**
     * 本月
     */
    private Integer month;

    /**
     * 本年
     */
    private Integer year;
}
