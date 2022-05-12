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
@TableName("app_visit_plan")
public class AppVisitPlan  implements Serializable {

	private static final long serialVersionUID =  1364294847912056297L;

	/**
	 * 访客计划ID
	 */
   	@TableField("plan_id" )
	@TableId(value = "plan_id", type = IdType.INPUT)
	private Long planId;

	/**
	 * 访客姓名
	 */
   	@TableField("person_name" )
	private String personName;

	/**
	 * 访客ID
	 */
   	@TableField("person_id" )
	private Long personId;

	/**
	 * 苏康码
	 */
   	@TableField("qrcode" )
	private String qrcode;

   	@TableField("pic_url" )
	private String picUrl;

	/**
	 * 苏康码颜色
	 */
   	@TableField("qrcode_color" )
	private String qrcodeColor;

	/**
	 * 车牌号
	 */
   	@TableField("car_no" )
	private String carNo;

	/**
	 * 体温
	 */
   	@TableField("temperature" )
	private String temperature;

	/**
	 * 目的地json
	 */
   	@TableField("destination" )
	private String destination;

	@TableField("destination_name" )
	private String destinationName;

	/**
	 * 身份证号
	 */
   	@TableField("id_num" )
	private String idNum;

   	@TableField("mobile" )
	private String mobile;

	@TableField("land_line")
	private String landLine;

	/**
	 * 是否关闭标识
	 */
   	@TableField("is_close" )
	private Long isClose;

	/**
	 * 备注
	 */
   	@TableField("remark" )
	private String remark;

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

   	@TableField("is_hot" )
	private Integer isHot;

   	@TableField("is_high_risk_area" )
	private Integer isHighRiskArea;

	/**
	 * 入口
	 */
   	@TableField("entrance" )
	private Long entrance;

	/**
	 * 出口
	 */
	@TableField("export" )
	private Long export;


	@TableField("code_source")
	private Integer codeSource;

	@ApiModelProperty(value = "destination_dept_name")
	private String destinationDeptName;


	@ApiModelProperty(value = "app_person_wx_id")
	private Long appPersonWxId;

	@ApiModelProperty(value = "yjfzcode_color")
	private String yjfzcodeColor;

	@ApiModelProperty(value = "entrance_name", required = false)
	private String entranceName;

	@ApiModelProperty(value = "visited_person", required = false)
	private String visitedPerson;

}
