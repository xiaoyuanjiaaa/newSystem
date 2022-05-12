package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("防疫梯队管理")
@TableName("app_echelon")
public class AppEchelon extends BaseEntity  {

    private static final long serialVersionUID = 1L;

    /** 梯队id */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    /** 梯队名称 */
    private String echelonName;

    /** 配置人数 */
    private Integer echelonNum;

    /** 梯队类型 **/
    private String echelonType;

    /** 删除状态 */
    private Integer isDeleted;


    /** 梯队下关联的人员 */
    @TableField(exist = false)
    private List<SysUser> sysUsers;

    /** 梯队关联的人员 **/
    @TableField(exist = false)
    private List<AppEpidemicUser> appEpidemicUsers;

}
