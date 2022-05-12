package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nucleic_acid_record")
public class NucleicAcidRecord {

    private static final long serialVersionUID = 4190909746910833251L;
    /** 主键id */
    private Long id;

    /**
     * 病人id
     */
    private String patientId;

    /**
     * 短信发送状态
     * 1、未发送
     * 2、已发送
     */
    private int newsStatus;
}
