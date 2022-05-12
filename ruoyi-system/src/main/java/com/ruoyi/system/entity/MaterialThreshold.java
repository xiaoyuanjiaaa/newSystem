package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("material_threshold")
public class MaterialThreshold {

    private Long id;

    @ApiModelProperty(value = "物资id")
    private Long suppliersId;

    @ApiModelProperty(value = "阈值名称")
    private String thresholdName;

    @ApiModelProperty(value = "安全储备量")
    private Long safetyReserve;


    @ApiModelProperty(value = "自动计算储备量")
    @TableField(exist = false)
    private Long automaticReserve;


    @ApiModelProperty(value = "评估标准")
    private Integer evaluateStandard;

    @ApiModelProperty(value = "储备天数")
    private Long reserveDays;

    @ApiModelProperty(value = "物资名称")
    private String courtyardName;

    @ApiModelProperty(value = "评估天数")
    private Long evaluateDays;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态")
    private Integer isDeleted;

    /** 阈值状态 */
    @ApiModelProperty(value = "阈值状态")
    private Integer isAll;

}
