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

import javax.validation.constraints.NotNull;
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
@TableName("app_destination")
public class AppDestination  implements Serializable {

	private static final long serialVersionUID =  5812627873079028226L;

	/**
	 * 目的地ID
	 */
   	@TableField("destination_id" )
	@TableId(value = "destination_id",type= IdType.AUTO)
	private Long destinationId;

	/**
	 * 目的地名称
	 */
   	@TableField("destination_name" )
	@NotNull(message = "目的地名称不能为空")
	private String destinationName;

	/**
	 * 父级目的地ID
	 */
   	@TableField("parent_id" )
	private Long parentId;

	/**
	 * 删除标识
	 */
   	@TableField("is_del" )
	@NotNull(message = "删除标识不能为空")
	private Long isDel;

	/**
	 * 级别
	 */
   	@TableField("level" )
	private Long level;

	/**
	 * 父级目的地名称
	 */
	private String parentName;

	/**
	 * 是否有pda
	 */
   	@TableField("has_pda" )
	private Long hasPda;

	/**
	 * 类型
	 */
   	@TableField("type" )
	@NotNull(message = "目的地类型不能为空")
	private Long type;

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

   	@TableField("in_out" )
	private Integer inOut;

	@TableField("status" )
	private Integer status;

	@TableField("temperature_low" )
	private Integer temperatureLow;

	@TableField("temperature_high" )
	private Integer temperatureHigh;

}
