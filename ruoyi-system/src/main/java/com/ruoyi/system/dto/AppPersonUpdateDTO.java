package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("修改AppPerson对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonUpdateDTO {
    @ApiModelProperty(value = "personId", required = false)
    private Long personId;

    @ApiModelProperty(value = "姓名", required = false)
    private String personName;

    @ApiModelProperty(value = "籍贯", required = false)
    private String nativePlace;

    @ApiModelProperty(value = "性别", required = false)
    private String sex;

    @ApiModelProperty(value = "出生日期", required = false)
    private Object birthDate;

    @ApiModelProperty(value = "民族", required = false)
    private String nation;

    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;

    @ApiModelProperty(value = "电话", required = false)
    private String telephone;

    @ApiModelProperty(value = "公司", required = false)
    private String company;

    @ApiModelProperty(value = "苏康码", required = false)
    private String qrcode;

    @ApiModelProperty(value = "苏康码颜色", required = false)
    private String qrcodeColor;

    @ApiModelProperty(value = "创建者", required = false)
    private String createBy;

    @ApiModelProperty(value = "更新者", required = false)
    private String updateBy;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

}
