package com.ruoyi.system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;


@ApiModel("修改AppPersonWx对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxUpdateDTO {
    @ApiModelProperty(value = "姓名", required = true)
    private String personName;

    @ApiModelProperty(value = "身份证", required = true)
    private String idNum;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "体温", required = false)
    private String symptoms;

    @ApiModelProperty(value = "预检分诊内容", required = false)
    private String content;

    @ApiModelProperty(value = "目的地json", required = false)
    private String destination;

    @ApiModelProperty(value = "车牌号", required = false)
    private String carNum;

    @ApiModelProperty(value = "1:公众号openId,2:小程序openId", required = false)
    private Integer type;

    @ApiModelProperty(value = "公众号openId", required = false)
    private String gzhOpenId;

    @ApiModelProperty(value = "小程序openId", required = false)
    private String xcxOpenId;

    @ApiModelProperty(value = "true:黄码,flase:黑码", required = true)
    private Boolean flag = false;

    @NotEmpty(message = "是否代填不能为空")
    @ApiModelProperty(value = "是否代填 1:是,2:否", required = true)
    private Integer isAssistant;
    @ApiModelProperty(value = "患者名称")
    private String patientName;
    @ApiModelProperty(value = "患者身份证号")
    private String patientNum;
    @ApiModelProperty(value = "与患者关系")
    private String patentRelation;

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
     * 行政楼具体部门
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

    /**
     * json数据
     */
    private Integer appointmentStatus;

    private String visitName;
    private String visitPhonenumber;
    private String visitStartTime;
    private String visitTime;
    private String visitRemark;
    private Integer enabled;
    private Integer isVisit;
}
