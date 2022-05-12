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
@TableName("app_car")
public class AppCar  implements Serializable {

	private static final long serialVersionUID =  6751255974235773366L;

   	@TableField("car_id" )
	private Long carId;

	/**
	 * 车辆类型
	 */
   	@TableField("car_type" )
	private String carType;

	/**
	 * 车牌号
	 */
   	@TableField("car_no" )
	private String carNo;

	/**
	 * 车身颜色
	 */
   	@TableField("car_color" )
	private String carColor;

	/**
	 * 车主
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

	/**
	 * 备注
	 */
   	@TableField("remark" )
	private String remark;

}
