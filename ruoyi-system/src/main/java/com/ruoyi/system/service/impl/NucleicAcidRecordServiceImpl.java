package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.NucleicAcidRecord;
import com.ruoyi.system.mapper.NucleicAcidRecordMapper;
import com.ruoyi.system.service.INucleicAcidRecordService;
import org.springframework.beans.factory.annotation.Autowired;

public class NucleicAcidRecordServiceImpl extends ServiceImpl<NucleicAcidRecordMapper, NucleicAcidRecord> implements INucleicAcidRecordService {

    @Autowired
    private NucleicAcidRecordMapper mapper;


}
