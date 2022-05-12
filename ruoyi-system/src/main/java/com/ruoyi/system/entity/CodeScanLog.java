package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("code_scan_log")
@ApiModel(value = "职工扫码记录")
public class CodeScanLog extends BaseEntity {

    private static final long serialVersionUID = 5892550896716915535L;

    /** 主键 */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("id" )
    private Long id;

    /** 姓名 */
    @TableField("name" )
    private String name;

    /** 身份证 */
    @TableField("id_num" )
    private String idNum;

    /** 手机号 */
    @TableField("phone" )
    private String phone;

    @TableField("door" )
    private String door;

    /** 所在片区id */
    @TableField("dept_id" )
    private Long deptId;

    /** 所在片区 */
    @TableField("dept_name" )
    private String deptName;

    /** 苏康码 */
    @TableField("skm_qrcode" )
    private String skmQrcode;

    /** 苏康码异常备注 */
    @TableField("skm_remark" )
    private String skmRemark;

    /** 每日填报异常备注 */
    @TableField("report_remark" )
    private String reportRemark;
}
