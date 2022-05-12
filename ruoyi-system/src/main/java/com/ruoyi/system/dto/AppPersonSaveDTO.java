package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("新增AppPerson对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonSaveDTO {

    @ApiModelProperty(value = "主卡号", required = false)
    private Long parentPersonId;

    @NotEmpty(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String personName;

    @NotEmpty(message = "身份证不能为空")
    @ApiModelProperty(value = "身份证", required = false)
    private String idNum;

    @NotEmpty(message = "籍贯不能为空")
    @ApiModelProperty(value = "籍贯", required = false)
    private String nativePlace;

    @NotEmpty(message = "性别不能为空")
    @ApiModelProperty(value = "性别", required = false)
    private String sex;

    @NotEmpty(message = "出生日期不能为空")
    @ApiModelProperty(value = "出生日期", required = false)
    private Date birthDate;

    @NotEmpty(message = "民族不能为空")
    @ApiModelProperty(value = "民族", required = false)
    private String nation;

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @NotEmpty(message = "电话不能为空")
    @ApiModelProperty(value = "电话", required = false)
    private String telephone;

    @NotEmpty(message = "公司不能为空")
    @ApiModelProperty(value = "公司", required = false)
    private String company;

    @NotEmpty(message = "苏康码不能为空")
    @ApiModelProperty(value = "苏康码", required = false)
    private String qrcode;

    @NotEmpty(message = "苏康码颜色不能为空")
    @ApiModelProperty(value = "苏康码颜色", required = false)
    private String qrcodeColor;

    @NotEmpty(message = "创建者不能为空")
    @ApiModelProperty(value = "创建者", required = false)
    private String createBy;

    @NotEmpty(message = "更新者不能为空")
    @ApiModelProperty(value = "更新者", required = false)
    private String updateBy;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "图片", required = false)
    private String picUrl;

}
