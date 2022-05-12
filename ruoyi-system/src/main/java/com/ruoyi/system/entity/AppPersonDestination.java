package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (AppPersonDestination)实体类
 *
 * @author makejava
 * @since 2021-08-13 15:10:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("app_person_destination")
public class AppPersonDestination implements Serializable {
    /**
     * 值班表主键ID
     */
    @TableId(value = "ud_id", type = IdType.INPUT)
    private Long udId;

    /**
     * 值班时间
     */
    private Date udDate;

    /**
     * 值班地点ID
     */
    private Long destinationId;

    /**
     * 人员基础信息ID
     */
    private Long personId;

    /**
     * 用户ID
     */
    private Long userId;

}
