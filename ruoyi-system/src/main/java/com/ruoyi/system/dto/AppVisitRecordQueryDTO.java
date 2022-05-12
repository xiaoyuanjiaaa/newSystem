package com.ruoyi.system.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("查询AppVisitRecord对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitRecordQueryDTO {
    @ApiModelProperty(value = "访客记录ID", required = false)
    private Long recordId;

    @ApiModelProperty(value = "访客记录时间", required = false)
    private Object recordTime;

    @ApiModelProperty(value = "被访问人名", required = false)
    private String visitedPerson;

    @ApiModelProperty(value = "访问人名", required = false)
    private String visitor;

    @ApiModelProperty(value = "访问地点", required = false)
    private String destinationName;

    @ApiModelProperty(value = "访问点ID", required = false)
    private Long destinationId;

    @ApiModelProperty(value = "访客计划ID", required = false)
    private Long planId;

    @ApiModelProperty(value = "创建者", required = false)
    private String createBy;

    @ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;

    @ApiModelProperty(value = "更新者", required = false)
    private String updateBy;

    @ApiModelProperty(value = "更新时间", required = false)
    private Date updateTime;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

}
