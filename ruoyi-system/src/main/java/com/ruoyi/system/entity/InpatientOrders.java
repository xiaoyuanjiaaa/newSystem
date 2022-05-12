package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("inpatient_orders")
public class InpatientOrders {

    private static final long serialVersionUID = 6240856850139448385L;

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
     * 开立科室代码
     */
    private String listDpcd;

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
     * 项目属性，1院内项目2院外项目
     */
    private String setItmattr;

    /**
     * 是否包含附材
     */
    private String setSubtbl;

    /**
     * 医嘱类别代码
     */
    private String typeCode;

    /**
     * 医嘱类别名称
     */
    private String typeName;

    /**
     * 医嘱是否分解 1 长期2临时
     */
    private String decmpsState;

    /**
     * 是否计费
     */
    private String chargeState;

    /**
     * 药方是否配药
     */
    private String needDrug;

    /**
     * 是否打印医嘱单
     */
    private String prmMorlist;

    /**
     * 是否需要确认
     */
    private String needConfirm;

    /**
     * 项目类别
     */
    private String itemType;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类别代码
     */
    private String classCode;

    /**
     * 项目类别名称
     */
    private String className;

    /**
     * 取药药房
     */
    private String pharmacyCode;

    /**
     * 执行科室代码
     */
    private String execDpcd;

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
     * 包装数量
     */
    private String packQty;

    /**
     * 规格
     */
    private String specs;

    /**
     * 剂型代码
     */
    private String doseModelCode;

    /**
     * 药品类别
     */
    private String  drugType;

    /**
     * 药品性质
     */
    private String drugQuality;

    /**
     * 价格
     */
    private String itemPrice;

    /**
     * 组合序号
     */
    private String combNo;

    /**
     * 主药标记
     */
    private String mainDrug;

    /**
     * 医嘱状态，0开立，1审核，2执行，3作废，4重整
     */
    private String moStat;

    /**
     * 用法代码
     */
    private String usageCode;

    /**
     * 用法名称
     */
    private String useName;

    /**
     * 用法英文缩写
     */
    private String englishAb;

    /**
     * 频次代码
     */
    private String frequencyCode;

    /**
     * 频次名称
     */
    private String frequencyName;

    /**
     * 每次剂量
     */
    private String doseOnce;

    /**
     * 1扣护士站常备药/2扣药房
     */
    private String stockMin;

    /**
     * 项目总量
     */
    private String qtyTot;

    /**
     * 付数
     */
    private String useDays;

    /**
     * 开始时间
     */
    private String dateBgn;

    /**
     * 结束时间
     */
    private String dateEnd;

    /**
     * 录入人员代码
     */
    private String recUsercd;

    /**
     * 录入人员姓名
      */
    private String recUsernm;

    /**
     * 确认表及，0未确认/1已确认
     */
    private String confirmFlag;

    /**
     * 确认时间
     */
    private String confirmDate;

    /**
     * 确认人员代码
     */
    private String confirmUsercd;

    /**
     * Dc标记0未dc/2已dc
     */
    private String dcFlag;

    /**
     * Dc时间
     */
    private String dcDate;

    /**
     * DC原因代码
     */
    private String dcCode;

    /**
     * DC原因名称
     */
    private String dcName;

    /**
     * DC医师代码
     */
    private String dcDoccd;

    /**
     * DC医师姓名
     */
    private String dcDocnm;

    /**
     * DC人员代码
     */
    private String dcUsercd;

    /**
     * DC人员名称
     */
    private String dcUsernm;

    /**
     * 执行标记0未执行/1已执行
     */
    private String executeFlag;

    /**
     * 执行时间
     */
    private String executeDate;

    /**
     * 执行人员代码
     */
    private String executeUsercd;

    /**
     * 整档标记0无1有
     */
    private String decoFlag;

    /**
     * 本次分解时间
     */
    private String dateCurmodc;

    /**
     * 下次分解时间
     */
    private String dateNxtmodc;

    /**
     * 医嘱说明
     */
    private String moNote1;

    /**
     * 备注
     */
    private String moNote2;

    /**
     * 1不惜要皮试/2需要皮试，未做/3皮试阳/4皮试阴
     */
    private String hypotest;

    /**
     * 检查部位体检
     */
    private String itemNote;

    /**
     * 申请单号
     */
    private String applyNo;

    /**
     * 加急标记，0普通/1加急
     */
    private String emcFlag;

    /**
     * 医嘱提取标记，0待提取/1已提取/2DC提取
     */
    private String getFlag;

    /**
     * 是否附材'1'是
     */
    private String subtblFlag;

    /**
     * 排列序号，安排列序号有大到小顺序显示医嘱
     */
    private String sortId;

    /**
     * DC审核时间
     */
    private String dcConfirmDate;

    /**
     * DC审核人
     */
    private String dcConfirmOper;

    /**
     *  DC审核标记，0未审核/1已审核
     */
    private String dcConfirmFlag;

    /**
     * 样本类型，名称
     */
    private String labCode;

    /**
     * 是否需要患者同意0不需要，1需要
     */
    private String permission;

    /**
     * 组套编码
     */
    private String packageCode;

    /**
     * 组套名称
     */
    private String packageName;

    /**
     * 扩展备注[执行时间]
     */
    private String mark1;

    /**
     * 扩展备注1
     */
    private String mark2;

    /**
     * 扩展备注2
     */
    private String mark3;

    /**
     * 执行时间点[特殊频次]
     */
    private String execTimes;

    /**
     * 执行剂量[特殊频次]
     */
    private String execDose;

    /**
     * 上级审核医生编码
     */
    private String upperDoct;

    /**
     * 抗生素申请单号
     */
    private String antibioticNo;

    /**
     * 白蛋白支付方式，1医保/2自费
     */
    private String albuminflag;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date updatedTime;

    /**
     * 删除状态
     */
    private int isDeleted;

}
