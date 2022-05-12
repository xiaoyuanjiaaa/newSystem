package com.ruoyi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("片区状态返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptStatusVO {
    @ApiModelProperty(value = "片区id")
    private Integer deptId;
    @ApiModelProperty(value = "片区名称")
    private String deptName;
    @ApiModelProperty(value = "片区审核状态 1审核中 2通过 3拒绝")
    private Integer status;
    @ApiModelProperty(value = "个人门铃码图片地址")
    private String qrcodeUrl;
    @ApiModelProperty(value = "1可以修改片区 0不能修改片区")
    private Integer leaderStatus;
}
