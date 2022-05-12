package com.ruoyi.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("app_person_wx")
public class AppPersonWx implements Serializable {
    /**
     * (AppPersonWx)实体类
     *
     * @author makejava
     * @since 2021-08-30 17:09:43
     */


    @TableField("id" )
    @TableId(value = "id", type = IdType.INPUT)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    private Date createTime;

    /**
     * 体温
     */
    private String symptoms;

    /**
     * 预检分诊内容
     */
    private String content;

    /**
     * 目的地json
     */
    private String destination;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 1:公众号openId,2:小程序openId
     */
    private Integer type;

    /**
     * 公众号openId
     */
    private String gzhOpenId;

    /**
     * 小程序openId
     */
    private String xcxOpenId;

    /**
     * 流行病史，逗号分隔
     */
    private String epidemicHistory;

    /**
     * 接触史，逗号分隔
     */
    private String contactHistory;

    /**
     * 高风险岗位人员，逗号分隔
     */
    private String riskPosition;

    /**
     * 核酸检测频次
     */
    private String frequency;

    /**
     * 最近一次核酸检测时间
     */
    private String lastTestTime;


    /**
     * 第几针
     */
    private String needle;

    /**
     * 共几针
     */
    private String totalNeedle;

    /**
     * 最近一次接种时间
     */
    private String lastNeedleTime;

    private String destinationDeptName;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 来源
     */
    private String source;

    /**
     * 座机号
     */
    private String telphone;

    /**
     * json数据
     */
    private String reportJson;

    private Integer appointmentStatus;

    private Integer isVisit;

    private String visitName;
    private String visitPhonenumber;
    private String visitStartTime;
    private String visitTime;
    private String visitRemark;

    private Integer enabled;

    private Boolean flag;

    //是否代填
    private Integer isAssistant;
    //患者名称
    private String patientName;
    //患者身份证号
    private String patientNum;
    //与患者关系
    private String patentRelation;
}
