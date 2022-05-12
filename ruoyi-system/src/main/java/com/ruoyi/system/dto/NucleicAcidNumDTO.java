package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class NucleicAcidNumDTO {

    /**
     * 确诊人数
     */
    private Integer diagnosis;

    /**
     * 发热观察人数
     */
    private Integer fever;

    /**
     * 康复人数
     */
    private Integer recovery;

    /**
     * 清零天数
     */
    private Integer clear;
}
