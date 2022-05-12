package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class HospitalPersonnelDTO {

    /**
     * 医护人员
     */
    private Integer medicalStaff;

    /**
     * 住院患者
     */
    private Integer Inpatient;

    /**
     * 家属
     */
    private Integer familyMembers;

    /**
     * 门诊患者
     */
    private Integer outpatient;

    /**
     * 保安
     */
    private Integer securityStaff;

}
