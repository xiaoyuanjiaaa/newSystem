package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("修改AppVisitPlan对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitPlanUpdateDTO {
    @ApiModelProperty(value = "访客计划ID", required = true)
    private Long planId;

    @ApiModelProperty(value = "访客姓名", required = false)
    private String personName;

    @ApiModelProperty(value = "访客ID", required = false)
    private Long personId;

    @ApiModelProperty(value = "苏康码", required = false)
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色", required = false)
    private String qrcodeColor;

    @ApiModelProperty(value = "车牌号", required = false)
    private String carNo;

    @ApiModelProperty(value = "体温", required = false)
    private String temperature;

    @ApiModelProperty(value = "目的地json", required = false)
    private String destination;

    @ApiModelProperty(value = "身份证号", required = false)
    private String idNum;

    @ApiModelProperty(value = "更新者", required = true)
    private String updateBy;

    @ApiModelProperty(value = "大门")
    private Long door;

    @ApiModelProperty(value = "码类型")
    private Integer qcodeType;

    @ApiModelProperty(value = "目的地部门")
    private String destinationDeptName;

    @ApiModelProperty(value = "appPersonWxId", required = false)
    private Long appPersonWxId;

    @ApiModelProperty(value = "entrance_name", required = false)
    private String entranceName;

    @ApiModelProperty(value = "visited_person", required = false)
    private String visitedPerson;
}
