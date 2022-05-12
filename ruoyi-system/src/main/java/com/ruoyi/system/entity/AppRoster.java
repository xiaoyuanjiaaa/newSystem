package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 9:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("防疫人员排班管理")
@TableName("app_roster")
public class AppRoster extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 班次名称 */
    private String rosterName;


    /** 删除状态 */
    private Integer isDeleted;

    /** 班次时间-开始-1 */
    private String timeStartOne;

    /** 班次时间-开始-1 */
    private String timeEndOne;

    /** 班次时间-开始-2 */
    private String timeStartTwo;

    /** 班次时间-开始-2 */
    private String timeEndTwo;
    
    /** 数据隔离*/
    private Integer type;
  
}
