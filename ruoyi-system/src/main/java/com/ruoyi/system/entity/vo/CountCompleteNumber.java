package com.ruoyi.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "部门完成数量")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountCompleteNumber {

    @ApiModelProperty(value = "部门总数")
    private Integer deptCount;

    @ApiModelProperty(value = "部门完成数量")
    private Integer deptComplete;

    @ApiModelProperty(value = "部门未完成数量")
    private Integer deptUnComplete;

    @ApiModelProperty("总人数")
    private Integer userCount;

    @ApiModelProperty("已填报人数")
    private Integer userComplete;

    @ApiModelProperty("未填报人数")
    private Integer userUnComplete;

    @ApiModelProperty("在岗人数")
    private Integer onDutyCount;

    @ApiModelProperty("未在岗人数")
    private Integer outDutyCount;


}
