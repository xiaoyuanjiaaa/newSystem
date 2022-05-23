package com.ruoyi.system.dto;

import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("新增AppHealthReport对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppHealthReportSaveDTO {

    @NotEmpty(message = "personId不能为空")
    @ApiModelProperty(value = "personId", required = true)
    private Long personId;

    @NotEmpty(message = "填报人不能为空")
    @ApiModelProperty(value = "填报人", required = true)
    private String reportName;

    @ApiModelProperty(value = "身份证", required = true)
    private String idNum;

    @NotEmpty(message = "填报人手机号不能为空")
    @ApiModelProperty(value = "填报人手机号", required = true)
    private String reportPhone;

    @ApiModelProperty(value = "性别0男1女", required = false)
    private Integer sex;

    @NotEmpty(message = "填报人体温不能为空")
    @ApiModelProperty(value = "填报人体温", required = false)
    private String reportTemperature;

    @ApiModelProperty(value = "有无其它症状0无1有", required = true)
    private Integer otherSymptoms;

    @ApiModelProperty(value = "本人是否有密接史，0无1有", required = true)
    private Integer myselfIsCloseHistory;

    @ApiModelProperty(value = "家人是否有密接史，0无1有", required = true)
    private Integer familyIsCloseHistory;

    @ApiModelProperty(value = "是否关注每日疫情情况，0是1否", required = true)
    private Integer isAttentionPidemic;

    @ApiModelProperty(value = "所在片区 A-J", required = true)
    private String location;

    @NotEmpty(message = "工作区域")
    @ApiModelProperty(value = "工作区域", required = true)
    private String reportCompany;

    @NotEmpty(message = "填报日期不能为空")
    @ApiModelProperty(value = "填报日期", required = true)
    private Date reportTime;

    @NotEmpty(message = "填报内容不能为空")
    @ApiModelProperty(value = "填报内容", required = false)
    private String reportJson;

    @NotEmpty(message = "健康填报模板ID不能为空")
    @ApiModelProperty(value = "健康填报模板ID", required = false)
    private Long templateId;

    @NotEmpty(message = "创建者不能为空")
    @ApiModelProperty(value = "创建者", required = true)
    private String createBy;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "如果是黄码传true", required = true)
    private Boolean flag = false;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("工号")
    private String jobNumber;

    @ApiModelProperty("是否是自有0：本院，1：非本院")
    private String isPrivate;

    @ApiModelProperty("0：临时，1：长期")
    private String isTemporary;

    @ApiModelProperty("职务等级")
    private String postLevel;

    @ApiModelProperty("填报来源：1.小程序填报；2.代为填报；3.批量填报；4无需填报；")
    private Integer appSource;

	@ApiModelProperty("工作场所")
    private String workPlace;

    @ApiModelProperty("核酸频次")
    private String frequency;

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
    //增加行程码,门铃码,核酸采样截图url字段
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

    @ApiModelProperty("异常项配置 1异常")
    private Integer exceptionStatus;
}
