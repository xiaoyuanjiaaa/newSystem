package com.ruoyi.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @Author: jianguo
 * @Date: 2021/9/16 16:44
 */
@ApiModel(value = "部门完成数量")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HReport {

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门id")
    private Long deptId;

    @ApiModelProperty(value = "部门已经填报数量")
    private Integer HNumber;

    @ApiModelProperty(value = "部门未填报数量")
    private Integer NNumber;

}
