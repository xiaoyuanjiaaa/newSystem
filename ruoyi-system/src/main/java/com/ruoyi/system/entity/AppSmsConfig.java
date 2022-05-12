package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_" +
		"sms_config")
public class AppSmsConfig  implements Serializable {

	private static final long serialVersionUID =  161746790177120735L;

	/**
	 * 短信推送时间
	 */
   	@TableField("sms_time" )
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private Date smsTime;

	/**
	 * 短信配置ID
	 */
   	@TableField("sms_config_id" )
	@TableId
	private Long smsConfigId;

	/**
	 * 短信配置开启标识
	 */
   	@TableField("is_enabled" )
	private Long isEnabled;

	/**
	 * 更新时间
	 */
   	@TableField("update_time" )
	private Date updateTime;

	/**
	 * 更新者
	 */
   	@TableField("update_by" )
	private String updateBy;

	/**
	 * 创建时间
	 */
   	@TableField("create_time" )
	private Date createTime;

	/**
	 * 创建者
	 */
   	@TableField("create_by" )
	private String createBy;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	private String remark;


}
