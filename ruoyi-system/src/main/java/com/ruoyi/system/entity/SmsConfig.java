package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sms_config")
public class SmsConfig implements Serializable {

	private static final long serialVersionUID =  161746790177120735L;

	/**
	 * id
	 */
	@TableField("id" )
	@TableId
	private Long id;

	/**
	 * 短信内容
	 */
	@TableField("message" )
	private String message;

	/**
	 * 短信推送时间
	 */
   	@TableField("send_time" )
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private Date sendTime;

	/**
	 * 短信接收人
	 */
	@TableField("mobiles" )
	private String mobiles;

	/**
	 * 短信配置类型
	 */
	@TableField("type" )
	@NotEmpty(message = "type不能为空")
	private String type;

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
	 * 人名数组
	 */
	@TableField(exist = false)
	private List<SysUser> sysUsers;

}
