package com.ruoyi.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@ApiModel("新增AppVisitRecord对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitRecordSaveDTO {

    @NotEmpty(message = "被访问人名不能为空")
    @ApiModelProperty(value = "被访问人名", required = true)
    private String visitedPerson;

    @NotEmpty(message = "访问人名不能为空")
    @ApiModelProperty(value = "访问人名", required = true)
    private String visitor;

    @NotEmpty(message = "访问地点不能为空")
    @ApiModelProperty(value = "访问地点", required = true)
    private String destinationName;

    @NotEmpty(message = "访问点ID不能为空")
    @ApiModelProperty(value = "访问点ID", required = true)
    private Long destinationId;

    @NotEmpty(message = "访客计划ID不能为空")
    @ApiModelProperty(value = "访客计划ID", required = false)
    private Long planId;

    @ApiModelProperty(value = "被确认到访的人员id", required = false)
    private Long personId;

    @NotEmpty(message = "创建者不能为空")
    @ApiModelProperty(value = "创建者", required = true)
    private String createBy;

    @NotEmpty(message = "更新者不能为空")
    @ApiModelProperty(value = "更新者", required = false)
    private String updateBy;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

}
