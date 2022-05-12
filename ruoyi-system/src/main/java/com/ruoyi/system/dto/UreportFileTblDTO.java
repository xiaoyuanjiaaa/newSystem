package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UreportFileTblDTO extends PageDomain {

    @ApiModelProperty("工作区域")
    private String name;
}
