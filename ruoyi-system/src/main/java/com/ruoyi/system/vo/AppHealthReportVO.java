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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("AppHealthReportVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppHealthReportVO {
    @ApiModelProperty(value = "健康填报ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long reportId;

    @ApiModelProperty(value = "填报人")
    private String reportName;

    @ApiModelProperty(value = "填报人手机号")
    private String reportPhone;

    @ApiModelProperty(value = "性别0男1女")
    private Integer sex;

    @ApiModelProperty(value = "填报人体温")
    private String reportTemperature;

    @ApiModelProperty(value = "有无其它症状0无1有")
    private Integer otherSymptoms;

    @ApiModelProperty(value = "本人是否有密接史，0无1有")
    private Integer myselfIsCloseHistory;

    @ApiModelProperty(value = "家人是否有密接史，0无1有")
    private Integer familyIsCloseHistory;

    @ApiModelProperty(value = "是否关注每日疫情情况，0是1否")
    private Integer isAttentionPidemic;

    @ApiModelProperty(value = "所在片区 A-J")
    private String location;

    @ApiModelProperty(value = "单位名称")
    private String reportCompany;

    @ApiModelProperty(value = "填报日期")
    private Date reportTime;

    @ApiModelProperty(value = "填报内容")
    private String reportJson;

    @ApiModelProperty(value = "健康填报模板ID")
    private Long templateId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "personId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty("工作场所")
    private String workPlace;

    @ApiModelProperty("核酸频次")
    private String frequency;

    @ApiModelProperty("核酸频次备注")
    private String frequencyRemark;

    @ApiModelProperty("症状备注")
    private String symptomRemark;

    @ApiModelProperty("流行病史备注")
    private String epidemicRemark;

    @ApiModelProperty("家属流行病史备注")
    private String relationEpidemicRemark;

    @ApiModelProperty("代为填报备注")
    private String replaceRemark;

    @ApiModelProperty("是否接种疫苗：1-接种 2-未接种")
    private Integer vaccination;

    @ApiModelProperty("第一针接种时间")
    private String firstStitchTime;

    @ApiModelProperty("第二针接种时间")
    private String twoStitchTime;

    @ApiModelProperty("第三针接种时间")
    private String threeStitchTime;

    @ApiModelProperty("第一针未接种备注")
    private String firstStitchRemark;

    @ApiModelProperty("第二针未接种备注")
    private String twoStitchRemark;

    @ApiModelProperty("第三针未接种备注")
    private String threeStitchRemark;

    @ApiModelProperty("未接种备注")
    private String notVacRemark;

    @ApiModelProperty("接种图片url")
    private String vaccinationUrl;

    /**
     * 行程码图片url
     */
    @ApiModelProperty("行程码图片url")
    private String xccodeUrl;
    /**
     * 门铃码图片url
     */
    @ApiModelProperty("门铃码图片url")
    private String qrcodeUrl;
    /**
     * 核酸采样图片url
     */
    @ApiModelProperty("核酸采样图片url")
    private String hscycodeUrl;
}
