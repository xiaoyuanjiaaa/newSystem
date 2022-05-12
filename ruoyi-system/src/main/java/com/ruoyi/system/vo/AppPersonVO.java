package com.ruoyi.system.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("AppPersonVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonVO {

    @ApiModelProperty(value = "personId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty(value = "parentPersonId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long parentPersonId;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "身份证")
    private String idNum;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    private Date birthDate;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "苏康码")
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色")
    private String qrcodeColor;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "图片")
    private String picUrl;

    @ApiModelProperty(value = "症状")
    private String symptoms;



}
