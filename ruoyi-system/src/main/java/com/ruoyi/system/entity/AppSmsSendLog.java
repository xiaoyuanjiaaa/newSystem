package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("app_sms_send_log")
public class AppSmsSendLog  implements Serializable {

	private static final long serialVersionUID =  1288395869682601438L;

	/**
	 * 日志ID
	 */
   	@TableField("log_id" )
	private Long logId;

	/**
	 * 短信发送内容
	 */
   	@TableField("sms_content" )
	private String smsContent;

	/**
	 * 短信发送状态
	 */
   	@TableField("sms_status" )
	private Long smsStatus;

	/**
	 * 短信发送时间
	 */
   	@TableField("sms_time" )
	private Date smsTime;

	/**
	 * 短信发送手机号
	 */
   	@TableField("sms_mobile" )
	private String smsMobile;

	/**
	 * 短信发送失败原因
	 */
   	@TableField("sms_err_msg" )
	private String smsErrMsg;

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
	private String remark;

}
