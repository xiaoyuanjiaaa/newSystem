package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_template_detail")
@ApiModel("模板详情")
public class AppTemplateDetail  implements Serializable {

	private static final long serialVersionUID =  4961278838514317692L;

	/**
	 * 详情ID
	 */
   	@TableField("detail_id" )
	@ApiModelProperty("详情id")
	@JsonSerialize(using= ToStringSerializer.class)
	@TableId
	private Long detailId;

	/**
	 * 模板ID
	 */
   	@TableField("template_id" )
	@ApiModelProperty("模板id")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long templateId;

	/**
	 * 字段名
	 */
   	@TableField("column_name" )
	@ApiModelProperty("名称")
	private String columnName;

	/**
	 * 中文名
	 */
   	@TableField("chinese_name" )
	@ApiModelProperty("中文名")
	private String chineseName;

	/**
	 * 是否开启缓存标识
	 */
   	@TableField("is_cached" )
	@ApiModelProperty("缓存开关")
	private String isCached;

	/**
	 * 字段类型
	 */
   	@TableField("column_type" )
	@ApiModelProperty("选项类型")
	private String columnType;

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

	/**
	 * 可选集
	 */
   	@TableField("select_options" )
	@ApiModelProperty("选项集")
	private String selectOptions;

   	@TableField("sort")
	@ApiModelProperty("排序")
	private String sort;

   	@TableField("is_enabled")
	@ApiModelProperty("排序")
	private String isEnabled;

	@TableField("is_required")
	@ApiModelProperty("是否必填")
	private String isRequired;

}
