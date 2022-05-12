package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_route_report")
public class AppRouteReport  implements Serializable {

	private static final long serialVersionUID =  1684272685006002256L;

	/**
	 * 行程填报ID
	 */
   	@TableField("route_id" )
	@TableId(value = "route_id", type = IdType.INPUT)
	private Long routeId;

	@TableField("person_id" )
	private Long personId;

	/**
	 * 计划行程开始时间
	 */
   	@TableField("route_time" )
	private Date routeTime;

	/**
	 * 行程地址
	 */
   	@TableField("route_addr" )
	private String routeAddr;

	/**
	 * 审核状态
	 */
   	@TableField("status" )
	private Long status;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	private String remark;

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
