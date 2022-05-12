package com.ruoyi.system.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("查询AppPersonWx对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxYuJianDTO extends PageDomain {

    @ApiModelProperty("开始时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty("结束时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty(value = "人员personId", required = false)
    private String personId;

    @ApiModelProperty(value = "身份证", required = false)
    private String idNum;

    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;

    @ApiModelProperty(value = "姓名", required = false)
    private String personName;

    @ApiModelProperty(value = "目的地", required = false)
    private String destination;

    @ApiModelProperty(value = "本人手机号", required = false)
    private String phonenumber;

    @ApiModelProperty(value = "是否代填 1:是,2:否", required = false)
    private Integer isAssistant;
    @ApiModelProperty(value = "患者名称")
    private String patientName;
    @ApiModelProperty(value = "患者身份证号")
    private String patientNum;
    @ApiModelProperty(value = "与患者关系")
    private String patentRelation;

    private String visitName;
    private String visitPhonenumber;
    private String visitStartTime;
    private String visitTime;
    private String visitRemark;
    private Integer enabled;
    private String xcxOpenId;
    private Integer isVisit;
    private Boolean flag;

    private String updateUserPhone;
    private String updateUserName;
    private Date updateTime;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long updateUserId;


}
