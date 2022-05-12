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
@TableName("workplace_frequency")
public class WorkPlaceFrequency implements Serializable {

	private static final long serialVersionUID =  6751255974235773366L;

   	@TableField("id" )
	private Long id;

	/**
	 * 车辆类型
	 */
   	@TableField("work_place_name" )
	private String workPlaceName;

	/**
	 * 车牌号
	 */
   	@TableField("work_place" )
	private String workPlace;

	/**
	 * 车身颜色
	 */
   	@TableField("frequency_name" )
	private String frequencyName;

	/**
	 * 车主
	 */
   	@TableField("frequency" )
	private String frequency;

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
