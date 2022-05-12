package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("片区审核列表入参对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptExamineListDTO extends PageDomain {
    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;
}
