package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_health_report")
@ApiModel(value = "健康填报实体")
public class AppHealthReport  implements Serializable {

	private static final long serialVersionUID =  5892550896716915535L;

	/**
	 * 健康填报ID
	 */
	@TableId(value = "report_id", type = IdType.INPUT)
	@ApiModelProperty(value = "健康填报ID")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long reportId;

	/**
	 * 人员id
	 */
	@TableField("person_id" )
	@ApiModelProperty("人员ID")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long personId;

	/**
	 * 填报人
	 */
   	@TableField("report_name" )
	@ApiModelProperty("填报人")
	private String reportName;

	/**
	 * 填报人手机号
	 */
   	@TableField("report_phone" )
	@ApiModelProperty("填报人手机号")
	private String reportPhone;

	@ApiModelProperty(value = "性别0男1女")
	@TableField("sex" )
	private Integer sex;

	/**
	 * 填报人体温
	 */
	@ApiModelProperty("填报人体温")
	@TableField("report_temperature" )
	private String reportTemperature;

	@TableField("other_symptoms" )
	@ApiModelProperty(value = "有无其它症状0无1有")
	private Integer otherSymptoms;

	@TableField("myself_is_close_history" )
	@ApiModelProperty(value = "本人是否有密接史，0无1有")
	private Integer myselfIsCloseHistory;

	@TableField("family_is_close_history" )
	@ApiModelProperty(value = "家人是否有密接史，0无1有")
	private Integer familyIsCloseHistory;

	@TableField("is_attention_pidemic" )
	@ApiModelProperty(value = "是否关注每日疫情情况，0是1否")
	private Integer isAttentionPidemic;

	@TableField("location" )
	@ApiModelProperty(value = "所在片区 A-J")
	private String location;

	/**
	 * 单位名称
	 */
   	@TableField("report_company" )
	@ApiModelProperty("工作区域")
	private String reportCompany;

	/**
	 * 填报日期
	 */
   	@TableField("report_time" )
	@ApiModelProperty("填报日期")
	private Date reportTime;

	/**
	 * 填报内容
	 */
   	@TableField("report_json" )
	@ApiModelProperty("填报内容")
	private String reportJson;

	/**
	 * 健康填报模板ID
	 */
   	@TableField("template_id" )
	@ApiModelProperty("健康填报模板ID")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long templateId;

	/**
	 * 创建者
	 */
   	@TableField("create_by" )
	@ApiModelProperty("创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
   	@TableField("create_time" )
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新者
	 */
   	@TableField("update_by" )
	@ApiModelProperty("更新人")
	private String updateBy;

	/**
	 * 更新时间
	 */
   	@TableField("update_time" )
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	@ApiModelProperty("备注")
	private String remark;


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

	@ApiModelProperty(value = "身份证")
	private String idNum;

	@TableField("app_source" )
	@ApiModelProperty(value = "填报来源：1.小程序填报；2.代为填报；3.批量填报；4无需填报")
	private Integer appSource ;

	//增加了一个注解
	@TableField("work_place")
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
