package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("片区列表返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptListVO {
    @ApiModelProperty(value = "片区id")
    private Long deptId;
    @ApiModelProperty(value = "片区名称")
    private String deptName;
}
