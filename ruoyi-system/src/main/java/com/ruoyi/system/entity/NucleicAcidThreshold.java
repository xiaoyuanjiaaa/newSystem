package com.ruoyi.system.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nucleic_acid_threshold")
public class NucleicAcidThreshold {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 阈值名称
     */
    private String thresholdName;

    /**
     * 阈值
     */
    private String thresholdValue;

    /**
     * 评估标准
     */
    private String evaluateStandard;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 删除状态
     */
    private int isDeleted;


}
