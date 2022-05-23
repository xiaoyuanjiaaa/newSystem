package com.ruoyi.system.vo;

import com.ruoyi.system.entity.AppPersonWxVisit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppPersonWxVisitVO extends AppPersonWxVisit {
    @ApiModelProperty(value = "用户名")
    private String personName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "身份证号")
    private String idNum;

}
