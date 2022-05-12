package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.NucleicAcidThreshold;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INucleicAcidThresholdService extends IService<NucleicAcidThreshold> {

    int insert(NucleicAcidThreshold nucleicAcidThreshold);

    NucleicAcidThreshold getOne(Long id);

    int update(NucleicAcidThreshold nucleicAcidThreshold);

    List<NucleicAcid> getListByThreshold(String type);

    List<NucleicAcid> getListByNumber(String type);



}
