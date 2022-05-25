package com.ruoyi.common.core.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class Option {
    private Long detailId;
    private Long templateId;
    private String columnName;
    private String chineseName;
    private String isCached;
    private String columnType;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    private String selectOptions;
    private Object value;
    private String sort;
    private String isEnabled;
    private String hasRemark;
    private String remarkTitle;
    private String remarkValue;
    private List<SelectOptions> selectOptionsList;

    @Data
    public  static class SelectOptions{
        private String label;
        private int value;
        private Boolean exceptionStatus;

    }
}
