package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_person_car")
public class AppPersonCar  implements Serializable {

	private static final long serialVersionUID =  8658696794649401891L;

   	@TableField("car_id" )
	private Long carId;

   	@TableField("person_id" )
	private Long personId;

}
