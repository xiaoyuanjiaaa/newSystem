package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 19:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("梯队人员关联表")
@TableName("app_echelon_user")
public class AppEchelonUser implements Serializable {

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    private Long echelonId;

    private Long userId;
}
