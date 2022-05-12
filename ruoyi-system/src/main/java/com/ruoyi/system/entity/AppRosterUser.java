package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.io.Serializable;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("防疫人员与排班关联表")
@TableName("app_roster_user")
public class AppRosterUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 防疫人员id */
    private Long userId;

    /** 防疫人员名称 **/
    private String nikeName;

    /** 排班id */
    private Long rosterId;

    /** 排班日期 */
    private String rosterTime;

    /** 职责id **/
    private Long dutyId;

    /** 班次名称 */
    private String rosterName;

    /** 班次时间-开始-1 */
    private String timeStartOne;

    /** 班次时间-开始-1 */
    private String timeEndOne;

    /** 班次时间-开始-2 */
    private String timeStartTwo;

    /** 班次时间-开始-2 */
    private String timeEndTwo;

    /** 职责名称 */
    @ApiModelProperty(value = "职责名称 ",required = true)
    private String dutyName;

    /** 删除状态 */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private AppRoster appRoster;

    @TableField(exist = false)
    private AppDuty appDuty;
}
