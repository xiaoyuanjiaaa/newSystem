package com.ruoyi.system.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "职工扫码记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeScanLogVo {

    /** 姓名 */
    @TableField("name" )
    private String name;

    /** 身份证 */
    @TableField("id_num" )
    private String idNum;

    /** 手机号 */
    @TableField("phone" )
    private String phone;

    /** 大门 */
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

    @TableField("create_time" )
    private Date createTime;
}
