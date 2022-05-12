package com.ruoyi.system.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("AppVisitPlanVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitPlanVO {
    @ApiModelProperty(value = "访客计划ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "访客姓名")
    private String personName;

    @ApiModelProperty(value = "访客ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty(value = "苏康码")
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色")
    private String qrcodeColor;

    @ApiModelProperty(value = "车牌号")
    private String carNo;

    @ApiModelProperty(value = "体温")
    private String temperature;

    @ApiModelProperty(value = "目的地json")
    private String destination;

    @ApiModelProperty(value = "身份证号")
    private String idNum;

    @ApiModelProperty(value = "是否关闭标识")
    private Integer isClose;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "图片地址")
    private String picUrl;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否发热，0不发热，1：发热")
    private Integer isHot;

    @ApiModelProperty(value = "是否去过高风险地区，0否，1：是")
    private Integer isHighRiskArea;

    @ApiModelProperty(value = "十大症状json数据字符串")
    private String symptoms;

    @ApiModelProperty(value = "访客记录")
    private List<AppVisitRecordVO> items;

}
