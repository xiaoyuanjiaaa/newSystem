package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.HospitalPersonnelDTO;
import com.ruoyi.system.entity.HisInpatientInfo;

import java.util.List;

public interface IHisInpatientInfoService extends IService<HisInpatientInfo> {

    List<HisInpatientInfo> getList();

    /**
     * 院内人员数
     * @return
     */
    HospitalPersonnelDTO getListNum();
}
