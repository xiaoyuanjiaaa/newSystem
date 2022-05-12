package com.ruoyi.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ldp
 */
@Data
public class BaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true)
    private String id;

}
