package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_change_report")
@ApiModel(value = "人员增减情况")
public class AppChangeReport extends BaseEntity {

    private static final long serialVersionUID = 5892550896716915535L;

    /** 主键 */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type= IdType.ID_WORKER)
    private Long id;

    /** 所在片区 */
    @Excel(name = "所在片区")
    private String deptName;

    /** 工号 */
    @Excel(name = "工号")
    private String jobNumber;

    /** 填报人 */
    @Excel(name = "姓名")
    private String changeName;

    /** 填报人手机号 */
    @Excel(name = "手机号")
    private String changePhone;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNum;

    /** 工作区域 */
//    @Excel(name = "工作区域")
    private String reportCompany;

    /** 填报人体温 */
//    @Excel(name = "填报人体温")
    private String reportTemperature;

    /** 填报时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "变化时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date changeTime;

    /** $column.columnComment */
//    @Excel(name = "变化时间")
    private Long personId;


    /** 增加减少状态(0，全部；1，增加；2，减少) */
//    @Excel(name = "增加减少状态(0，全部；1，增加；2，减少)")
    private Integer changeType;

    /** $column.columnComment */
    @Excel(name = "人员状态")
    private String changeTypeName;

    /** $column.columnComment */
//    @Excel(name = "变化时间")
    private Long deptId;
}
