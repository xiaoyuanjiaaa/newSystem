package com.ruoyi.system.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("查询AppPersonWx对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PublicApiQueryDTO {

    @ApiModelProperty(value = "身份证", required = false)
    private String idNum;

    @ApiModelProperty(value = "姓名", required = false)
    private String personName;

}
