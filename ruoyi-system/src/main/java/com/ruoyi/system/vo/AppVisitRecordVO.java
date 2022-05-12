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

@ApiModel("AppVisitRecordVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitRecordVO {
    @ApiModelProperty(value = "访客记录ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recordId;

    @ApiModelProperty(value = "访客记录时间")
    private Date recordTime;

    @ApiModelProperty(value = "被访问人名")
    private String visitedPerson;

    @ApiModelProperty(value = "访问人名")
    private String visitor;

    @ApiModelProperty(value = "访问地点")
    private String destinationName;

    @ApiModelProperty(value = "访问点ID")
    private Long destinationId;

    @ApiModelProperty(value = "访客计划ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

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

}
