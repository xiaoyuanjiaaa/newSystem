package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jianguo
 * @Date: 2021/9/15 10:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("app_not_reported")
@ApiModel(value = "健康填报实体")
public class AppNotReported implements Serializable {

    private static final long serialVersionUID =  5892550896716915535L;

    /**
     * 健康填报ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @ApiModelProperty(value = "id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 部门id **/
    private Long deptId;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 人员ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long personId;

    /** 统计时间 */
    private Date statisticsTime;

    /** 用户主键id **/
    private Long userId;

    /** 用户姓名 **/
    private String nickName;

    /** 部门名称 **/
    @TableField(exist = false)
    private String deptName;
}
