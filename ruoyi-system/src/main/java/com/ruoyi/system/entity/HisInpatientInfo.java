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
@TableName("his_inpatient_info")
public class HisInpatientInfo {

    private static final long serialVersionUID = 265181240295163529L;

    private Long id;

    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     * 住院号
     */
    private String hospitalizationNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 职业代码
     */
    private String occupationCode;

    /**
     * 工职单位
     */
    private String workUnit;

    /**
     * 户口或家庭住址
     */
    private String address;

    /**
     * 家庭电话
     */
    private String telephone;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 联系人地址
     */
    private String contactAddress;

    /**
     * 联系人关系
     */
    private String contactRelation;

    /**
     * 入院日期
     */
    private String admissionDate;

    /**
     * 科室代码
     */
    private String deptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 结算类别
     */
    private String settlementType;

    /**
     * 合同代码
     */
    private String contractCode;

    /**
     * 合同单位名称
     */
    private String contractUnitName;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 护理单元代码
     */
    private String nursingUnitCode;

    /**
     * 护理单元名称
     */
    private String nursingUnitName;

    /**
     * 医师代码(住院)
     */
    private String physicianHospitalCode;

    /**
     * 医师名称(住院)
     */
    private String physicianHospitalName;

    /**
     * 医师代码(主治)
     */
    private String physicianAttendingCode;

    /**
     * 医师名称(主治)
     */
    private String physicianAttendingName;

    /**
     * 医师代码(主任)
     */
    private String physicianDirectorCode;

    /**
     * 医师名称(主任)
     */
    private String physicianDirectorName;

    /**
     * 沪市代码(责任)
     */
    private String nurseDutyCode;

    /**
     * 护士名称(责任)
     */
    private String nurseDutyName;

    /**
     * 费用金额(已结)
     */
    private String settlementAmountEd;

    /**
     * 预交金额(已结)
     */
    private String advancePaymentPriceEd;

    /**
     * 结算日期(上次)
     */
    private String settlementDateEd;

    /**
     * 出院日期
     */
    private String leaveHospital;

    /**
     * 开具医师
     */
    private String issuingPhysician;

    /**
     * 上次固定费用时间
     */
    private String lastFixedFeeDate;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 操作日期
     */
    private String operatorDate;

    /**
     * 门诊诊断
     */
    private String outpatientDiagnosis;

    /**
     * 饮食
     */
    private String diet;

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
