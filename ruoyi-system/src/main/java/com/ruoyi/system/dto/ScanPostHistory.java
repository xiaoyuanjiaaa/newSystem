package com.ruoyi.system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScanPostHistory {


    private String url;
    private String param;
    private String headers;
    private LocalDateTime createTime;
    private String response;
    private Integer success;

}
