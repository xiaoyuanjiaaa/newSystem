package com.ruoyi.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.system.entity.AppPersonWx;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("AppPersonVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonInfoVO {
    @ApiModelProperty(value = "personId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "身份证")
    private String idNum;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "苏康码qrcode值")
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色")
    private String qrcodeColor;

    @ApiModelProperty(value = "1苏康码，2二维码，3员工码")
    private Integer type;

    @ApiModelProperty(value = "判断是否是员工，true是，false否")
    private Boolean flag=false;

    @ApiModelProperty(value = "判断是预检分诊还是每日填报")
    private String qcodeType;

    @ApiModelProperty(value = "每日健康填报信息")
    private AppHealthReportVO healthReportStr = new AppHealthReportVO();

    @ApiModelProperty(value = "每日预检分诊信息")
    private AppPersonWx inspectionTriageStr = new AppPersonWx();

    @ApiModelProperty(value = "访客计划")
    private AppVisitPlanVO appVisitPlanVO = new AppVisitPlanVO();

    @ApiModelProperty(value = "二维码有效时间")
    private String endTime;

}
