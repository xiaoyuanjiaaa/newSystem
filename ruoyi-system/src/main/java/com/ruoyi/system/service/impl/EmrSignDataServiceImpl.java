package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.EmrSignData;
import com.ruoyi.system.mapper.EmrSignDataMapper;
import com.ruoyi.system.service.IEmrSignDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmrSignDataServiceImpl extends ServiceImpl<EmrSignDataMapper, EmrSignData> implements IEmrSignDataService {

    @Autowired
    EmrSignDataMapper mapper;

    @Override
    public List<EmrSignData> getList() {
        QueryWrapper<EmrSignData> queryWrapper = new QueryWrapper<>();

        return mapper.selectList(queryWrapper);
    }
}
