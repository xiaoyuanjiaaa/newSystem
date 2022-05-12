package com.ruoyi.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Entity基类
 *
 * @author ldp
 */
@Data
public class BasePageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码", required = false)
    private Integer index = 1;

    @ApiModelProperty(value = "行数", required = false)
    private Integer pageSize = 10;
}
