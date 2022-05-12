package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("nucleic_acid")
public class NucleicAcid {
    private static final long serialVersionUID = 7747715407616458588L;

    /** 主键id */
    private Long id;

    /**
     * 检验时间
     */
    private String testDate;

    /**
     * 检验单ID
     */
    private String inspectionOrderId;

    /**
     * 分组
     */
    private String grouping;

    /**
     * 检验时间
     */
    private String testTime;

    /**
     * 标本编码
     */
    private String specimenNo;

    /**
     * 申请单ID
     */
    private String applicationFormId;

    /**
     * 病人类别
     */
    private String patientCategory;

    /**
     * 病人ID
     */
    private String patientId;

    /**
     * 病人唯一号
     */
    private String patientUniqueId;

    /**
     * 病人姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 病人科别
     */
    private String patientDepartment;

    /**
     * 临床诊断代号
     */
    private String clinicalDiagnosticCode;

    /**
     * 临床诊断
     */
    private String clinicalDiagnosis;

    /**
     * 标本种类
     */
    private String specimenType;

    /**
     * 检验目的
     */
    private String inspectionPurpose;

    /**
     * 检验目的名称
     */
    private String inspectionPurposeName;

    /**
     * 样本收费
     */
    private String sampleCharge;

    /**
     *  样本收费数
     */
    private String specimenItemsNumber;

    /**
     * 工作量
     */
    private String workload;

    /**
     * 采样时间
     */
    private String samplingTime;

    /**
     * 采样人员
     */
    private String samplingPersonnel;

    /**
     * 开单时间
     */
    private String billingTime;

    /**
     * 开单人员
     */
    private String billingPersonnel;

    /**
     * 送检单位
     */
    private String inspectionUnit;

    /**
     *收样时间
     */
    private String sampleReceivingTime;

    /**
     * 收样人员
     */
    private String sampleReceivingPersonnel;
    /**
     * 录入人员
     */
    private String entryPersonnel;

    /**
     * 录入时间
     */
    private String entryTime;

    /**
     * 检验人员
     */
    private String inspector;

    /**
     * 审核人员
     */
    private String reviewer;

    /**
     * 审核时间
     */
    private String auditTime;

    /**
     * 打印人员
     */
    private String printer;

    /**
     * 预报告时间
     */
    private String forecastTime;

    /**
     * 次数
     */
    private String frequency;

    /**
     * 收费状态
     */
    private String chargingStatus;

    /**
     * 检验状态
     */
    private String inspectionStatus;

    /**
     * 结果
     */
    private String result;


    /**
     * 核酸/抗体
     */
    private String type;


    /**
     * 短信内容
     */
    @TableField(exist = false)
    private String shortMessage;


}
