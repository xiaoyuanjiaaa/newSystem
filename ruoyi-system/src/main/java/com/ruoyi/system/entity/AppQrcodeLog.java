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
@TableName("app_qrcode_log")
public class AppQrcodeLog  implements Serializable {

	private static final long serialVersionUID =  8132346579806613978L;

	/**
	 * 日志ID
	 */
   	@TableField("log_id" )
	private Long logId;

	/**
	 * 二维码内容
	 */
   	@TableField("content" )
	private String content;

	/**
	 * 手机号
	 */
   	@TableField("phone" )
	private String phone;

	/**
	 * 身份证号
	 */
   	@TableField("id_num" )
	private String idNum;

	/**
	 * 人员id
	 */
   	@TableField("person_id" )
	private Long personId;

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

}
