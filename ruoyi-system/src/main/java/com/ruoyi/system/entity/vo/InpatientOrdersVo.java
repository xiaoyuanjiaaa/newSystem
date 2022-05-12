package com.ruoyi.system.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("病程录")
public class InpatientOrdersVo {

    private Long id;

    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     * 住院病历号
     */
    private String patientNo;

    /**
     * 住院科室代码
     */
    private String deptCode;

    /**
     * 住院护理站代码
     */
    private String nurseCellCode;

    /**
     * 医嘱流水号
     */
    private String moOrder;

    /**
     * 医嘱医师代码
     */
    private String docCode;

    /**
     * 医嘱医师名称
     */
    private String docName;

    /**
     * 医嘱日期
     */
    private String moDate;

    /**
     * 是否婴儿医嘱
     */
    private String babyFlag;

    /**
     * 婴儿序号
     */
    private String happenNo;

    /**
     * 医嘱类别名称
     */
    private String typeName;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类别名称
     */
    private String className;

    /**
     * 执行科室名称
     */
    private String execDpnm;

    /**
     * 药品基本剂量
     */
    private String baseDose;

    /**
     * 剂量单位
     */
    private String doseUnit;

    /**
     * 最小单位
     */
    private String minUnit;

    /**
     * 计价单位
     */
    private String priceUnit;

    /**
     * 规格
     */
    private String specs;

    /**
     * 价格
     */
    private String itemPrice;

    /**
     * 用法名称
     */
    private String useName;

    /**
     * 频次代码
     */
    private String frequencyCode;

    /**
     * 频次名称
     */
    private String frequencyName;

    /**
     * 录入人员代码
     */
    private String recUsercd;

    /**
     * DC原因名称
     */
    private String dcName;

    /**
     * DC医师姓名
     */
    private String dcDocnm;

    /**
     * DC人员名称
     */
    private String dcUsernm;

    /**
     * 录入人员姓名
     */
    private String recUsernm;

    /**
     * 确认时间
     */
    private String confirmDate;
    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 意识
     */
    private String consciousness;

    /**
     * 体温
     */
    private String temperature;

    /**
     * 脉搏
     */
    private String pulse;

    /**
     * 呼吸
     */
    private String breathing;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 血氧饱和度
     */
    private String spo2;

    /**
     * 意识清晰度
     */
    private String avpuScore;

    /**
     * 病情及预后评估系统
     */
    private String mews;

    /**
     * 吸氧
     */
    private String oxygenInhalation;

    /**
     * 疼痛
     */
    private String pain;

    /**
     * 入量名称
     */
    private String inputName;

    /**
     * 入量量
     */
    private String inputQuantity;

    /**
     * 出量名称
     */
    private String outputName;

    /**
     * 出量量
     */
    private String outputQuantity;

    /**
     * 姓名
     */
    private String name;


}
