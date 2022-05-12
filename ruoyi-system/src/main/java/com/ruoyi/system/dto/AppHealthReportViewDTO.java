package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class AppHealthReportViewDTO {

    /**
     * 姓名
     */
    private String reportName;

    /**
     * 身份证
     */
    private String idNum;

    /**
     * 手机号
     */
    private String reportPhone;

    /**
     * 体温
     */
    private String reportTemperature;

    /**
     * 苏康码
     */
    private String qrcodeColor;
}
