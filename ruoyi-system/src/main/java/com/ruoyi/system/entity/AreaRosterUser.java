package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("片区人员排班关联表")
@TableName("area_roster_user")
public class AreaRosterUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type= IdType.ID_WORKER)
    private Long id;

    /** 值班人员id */
    @Excel(name = "值班人员id")
    private Long userId;

    /** 排班id */
    @Excel(name = "排班id")
    private Long rosterId;

    /** 排班名称 */
    @Excel(name = "排班名称")
    private String rosterName;

    /** 排班时间 */
    @Excel(name = "排班时间")
    private String rosterTime;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer isDeleted;

    /** 片区id */
    @Excel(name = "片区id")
    private Long deptId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 人员名称 */
    @Excel(name = "人员名称")
    private String nickName;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 手机号*/
    private String phone;

    private Long workPlaceId;

    private String workPlaceName;
}
