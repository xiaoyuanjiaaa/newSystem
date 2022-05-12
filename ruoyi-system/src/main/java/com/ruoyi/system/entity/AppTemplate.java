package com.ruoyi.system.entity;

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
import java.util.Date;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14
 */

@Data
@TableName("app_template")
@ApiModel("模板")
public class AppTemplate {


	/**
	 * 模板ID
	 */
   	@TableField("template_id" )
	@JsonSerialize(using= ToStringSerializer.class)
	@ApiModelProperty("模板id")
	@TableId
	private Long templateId;

	/**
	 * 是否启用标识
	 */
   	@TableField("is_enabled" )
	@ApiModelProperty("启用标志")
	private String isEnabled;

	/**
	 * 模板名称
	 */
   	@TableField("template_name" )
	@ApiModelProperty("模板名")
	private String templateName;

	/**
	 * 创建者
	 */
   	@TableField("create_by" )
	private String createBy;

	/**
	 * 创建时间
	 */
   	@TableField("create_time" )
	private Date createTime;

	/**
	 * 更新者
	 */
   	@TableField("update_by" )
	private String updateBy;

	/**
	 * 更新时间
	 */
   	@TableField("update_time" )
	private Date updateTime;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	@ApiModelProperty("备注")
	private String remark;

}
