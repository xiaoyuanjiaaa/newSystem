package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.NucleicAcidThreshold;
import com.ruoyi.system.mapper.NucleicAcidThresholdMapper;
import com.ruoyi.system.service.INucleicAcidThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NucleicAcidThresholdServiceImpl extends ServiceImpl<NucleicAcidThresholdMapper, NucleicAcidThreshold> implements INucleicAcidThresholdService {

    @Autowired
    private NucleicAcidThresholdMapper mapper;

    @Override
    public int insert(NucleicAcidThreshold nucleicAcidThreshold) {
        return mapper.insert(nucleicAcidThreshold);
    }

    @Override
    public NucleicAcidThreshold getOne(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int update(NucleicAcidThreshold nucleicAcidThreshold) {
        return mapper.updateById(nucleicAcidThreshold);
    }

    @Override
    public List<NucleicAcid> getListByThreshold(String type) {
        return mapper.getListByBoolean(type);
    }

    @Override
    public List<NucleicAcid> getListByNumber(String type) {
        return mapper.getListByNumber(type);
    }


}
