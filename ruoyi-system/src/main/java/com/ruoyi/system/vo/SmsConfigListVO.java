package com.ruoyi.system.vo;

import com.ruoyi.system.entity.AppSmsConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SmsConfigListVO extends AppSmsConfig {
    @ApiModelProperty(value = "提醒类型")
    private String remindType;
    @ApiModelProperty(value = "提醒对象")
    private String remindObject;
}
