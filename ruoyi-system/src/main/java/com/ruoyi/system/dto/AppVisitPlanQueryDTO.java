package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("查询AppVisitPlan对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitPlanQueryDTO {
    @ApiModelProperty(value = "访客计划ID", required = false)
    private Long planId;

    @ApiModelProperty(value = "人员信息personId", required = false)
    private Long personId;

    @ApiModelProperty(value = "访客姓名", required = false)
    private String personName;

    @ApiModelProperty(value = "苏康码", required = false)
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色", required = false)
    private String qrcodeColor;

    @ApiModelProperty(value = "车牌号", required = false)
    private String carNo;

    @ApiModelProperty(value = "目的地json", required = false)
    private String destination;

    @ApiModelProperty(value = "身份证号", required = true)
    private String idNum;

    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;

    @ApiModelProperty(value = "进出口类型1：进口，2：出口，3：内部", required = false)
    private Integer type;

    @ApiModelProperty(value = "是否关闭的计划0正常1关闭", required = false)
    private Integer isClose;

    @ApiModelProperty(value = "判断是否只查询今天的数据true是，false否", required = false)
    private Boolean flag = false;

}
