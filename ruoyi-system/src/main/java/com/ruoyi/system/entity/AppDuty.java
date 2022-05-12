package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("职责")
@TableName("app_duty")
public class AppDuty  extends BaseEntity  {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ID_WORKER   )
    private Long id;

    /** 职责名称 */
    @ApiModelProperty(value = "职责名称 ",required = true)
    private String dutyName;

    /** 删除状态 */
    private Integer isDeleted;
}
