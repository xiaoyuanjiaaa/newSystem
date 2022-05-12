package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.mapper.MaterialInputMapper;
import com.ruoyi.system.service.IMaterialInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialInputServiceImpl implements IMaterialInputService {
    @Autowired
    public MaterialInputMapper mapper;


    @Override
    public List<MaterialInput> list(MaterialInput materialInput) {
        QueryWrapper<MaterialInput> queryWrapper = new QueryWrapper<>();
        List<MaterialInput> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public MaterialInput getOneById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int add(MaterialInput materialInput) {
        return mapper.insertDynamic(materialInput);
    }
}
