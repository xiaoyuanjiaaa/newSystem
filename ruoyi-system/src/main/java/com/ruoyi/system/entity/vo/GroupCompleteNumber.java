package com.ruoyi.system.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("统计各个部门完成数量")
public class GroupCompleteNumber {

    @ApiModelProperty(value = "部门ID")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty("总人数")
    private Integer countUser;
    @ApiModelProperty("人员已填报")
    private Integer userComplete;


}
