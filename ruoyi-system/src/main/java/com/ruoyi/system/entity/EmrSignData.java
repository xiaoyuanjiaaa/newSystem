package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("emr_sign_data")
public class EmrSignData {

    private static final long serialVersionUID = 5009844800786673916L;

    private Long id;

    /**
     * UHID
     */
    private String uhid;

    /**
     * HISID
     */
    private String hisid;

    /**
     * 表单名
     */
    private String formName;

    /**
     * SN_ID
     */
    private String snId;

    /**
     * RIW_HEAD
     */
    private String riwHead;

    /**
     * PAGE_ID
     */
    private String pageId;

    /**
     * PAGE_HEAD
     */
    private String pageHead;

    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 意识
     */
    private String consciousness;

    /**
     * 体温
     */
    private String temperature;

    /**
     * 脉搏
     */
    private String pulse;

    /**
     * 呼吸
     */
    private String breathing;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 血氧饱和度
     */
    private String spo2;

    /**
     * 意识清晰度
     */
    private String avpuScore;

    /**
     * 病情及预后评估系统
     */
    private String mews;

    /**
     * 吸氧
     */
    private String oxygenInhalation;

    /**
     * 疼痛
     */
    private String pain;

    /**
     * 入量名称
     */
    private String inputName;

    /**
     * 入量量
     */
    private String inputQuantity;

    /**
     * 出量名称
     */
    private String outputName;

    /**
     * 出量量
     */
    private String outputQuantity;

    /**
     * 病情观察
     */
    private String conditionObservation;

    /**
     * 签名
     */
    private String autograph;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date updatedTime;

    /**
     * 删除状态
     */
    private int isDeleted;

}
