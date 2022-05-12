package com.ruoyi.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("AppPersonDestinationVO对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonDestinationVO {
    @ApiModelProperty(value = "值班表主键ID")
    private Long udId;

    @ApiModelProperty(value = "值班时间")
    private Date udDate;

    @ApiModelProperty(value = "值班地点ID")
    private Long destinationId;

    @ApiModelProperty(value = "人员基础信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "值班地点名称")
    private String destinationName;


}
