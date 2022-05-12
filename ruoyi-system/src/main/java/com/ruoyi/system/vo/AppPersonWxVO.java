package com.ruoyi.system.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("AppPersonWxVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxVO {
    @ApiModelProperty(value = "id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "身份证")
    private String idNum;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "personId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty(value = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "体温")
    private String symptoms;

    @ApiModelProperty(value = "预检分诊内容")
    private String content;

    @ApiModelProperty(value = "目的地json")
    private String destination;

    @ApiModelProperty(value = "车牌号")
    private String carNum;

    @ApiModelProperty(value = "1:公众号openId,2:小程序openId")
    private Integer type;

    @ApiModelProperty(value = "公众号openId")
    private String gzhOpenId;

    @ApiModelProperty(value = "小程序openId")
    private String xcxOpenId;

    @ApiModelProperty(value = "二维码")
    private String qcode;

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
     * 打印的二维码过期时间
     */
    private String endTime;

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

    /**
     * 预检分诊码
     */
    private String yjfzQrCode;


    /**
     * 具体部门
     */
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

    private String visitName;
    private String visitPhonenumber;
    private String visitStartTime;
    private String visitTime;
    private String visitRemark;
    private Integer enabled;
    private Integer isVisit;

    private Boolean flag = false;

    //是否代填
    private Integer isAssistant;
    //患者名称
    private String patientName;
    //患者身份证号
    private String patientNum;
    //与患者关系
    private String patentRelation;
}
