package com.ruoyi.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("目的地查询实体")
public class AppDestinationQueryDTO extends PageDomain {

    @ApiModelProperty("类型 1.目的地 ，2.出入口，3.检验点")
    private Integer type;

    @ApiModelProperty("类型 0.启用")
    private Integer status;

}
