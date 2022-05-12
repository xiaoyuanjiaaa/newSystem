package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("app_visit_record")
public class AppVisitRecord  implements Serializable {

	private static final long serialVersionUID =  1875719562420279743L;

	/**
	 * 访客记录ID
	 */
   	@TableField("record_id" )
	@TableId(value = "record_id", type = IdType.INPUT)
	private Long recordId;

	/**
	 * 访客记录时间
	 */
   	@TableField("record_time" )
	private Date recordTime;

	/**
	 * 被访问人名
	 */
   	@TableField("visited_person" )
	private String visitedPerson;

	/**
	 * 访问人名
	 */
   	@TableField("visitor" )
	private String visitor;

	/**
	 * 访问地点
	 */
   	@TableField("destination_name" )
	private String destinationName;

	/**
	 * 访问点ID
	 */
   	@TableField("destination_id" )
	private Long destinationId;

	/**
	 * 访客计划ID
	 */
   	@TableField("plan_id" )
	private Long planId;

	/**
	 * 人员id
	 */
	@TableField("person_id" )
	private Long personId;


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
