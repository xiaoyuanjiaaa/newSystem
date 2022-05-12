package com.ruoyi.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: jianguo
 * @Date: 2022/3/15 10:12
 */
@ApiModel("AppPersonWxPubVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxPubVO {

    @ApiModelProperty(value = "预检分诊信息id")
    private String expectId;

    @ApiModelProperty(value = "填报者姓名")
    private String personName;

    @ApiModelProperty(value = "填报者手机号")
    private String personPhone;

    @ApiModelProperty(value = "填报者身份证号")
    private String personIdNum;

    @ApiModelProperty(value = "记录创建时间")
    private String createTime;

    @ApiModelProperty(value = "体温")
    private String symptoms;

    @ApiModelProperty(value = "目的：以json格式封装，分为就诊相关、办事两种，当值是办事的话，visit那几个字段才会有值")
    private String destination;

    @ApiModelProperty(value = "相关动态数据，以json格式封装")
    private String reportJson;

    @ApiModelProperty(value = "被访人姓名")
    private String visitName;

    @ApiModelProperty(value = "被访人手机")
    private String visitPhonenumber;

    @ApiModelProperty(value = "预约开始时间")
    private String visitStartTime;

    @ApiModelProperty(value = "备注")
    private String visitRemark;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "ture代表黄码，false代表绿码")
    private String flag;

    @ApiModelProperty(value = "是否代填 1:是,2:否", required = false)
    private Integer isAssistant;
    @ApiModelProperty(value = "患者名称")
    private String patientName;
    @ApiModelProperty(value = "患者身份证号")
    private String patientNum;
    @ApiModelProperty(value = "与患者关系")
    private String patentRelation;
}
