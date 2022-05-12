package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@TableName("sys_scan_log")
public class SysScanLog implements Serializable {

	private static final long serialVersionUID =  1288395869682601438L;

	/**
	 * 日志ID
	 */
   	@TableField("id" )
	private Long id;

   	@TableField("id_num" )
	private String idNum;


   	@TableField("person_name" )
	private String personName;


   	@TableField("json_result" )
	private String jsonResult;

	@TableField("entrance_name" )
	private String entranceName;

	/**
	 * 创建时间
	 */
   	@TableField("create_time" )
	private Date createTime;


}
