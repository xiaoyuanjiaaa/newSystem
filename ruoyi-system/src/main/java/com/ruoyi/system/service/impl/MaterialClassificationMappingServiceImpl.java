package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.MaterialClassificationMapping;
import com.ruoyi.system.mapper.MaterialClassificationMappingMapper;
import com.ruoyi.system.service.IMaterialClassificationMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialClassificationMappingServiceImpl extends ServiceImpl<MaterialClassificationMappingMapper, MaterialClassificationMapping> implements IMaterialClassificationMappingService {

    @Autowired
    MaterialClassificationMappingMapper mapper;

    /**
     * 选择勾选添加
     * @param kindCode
     * @return
     */
    @Override
    public int addCheck(String kindCode) {
        QueryWrapper<MaterialClassificationMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kind_code",kindCode);
        MaterialClassificationMapping materialClassificationMapping = mapper.selectOne(queryWrapper);
        if(materialClassificationMapping!=null){
            return mapper.delAll(kindCode);
        }
        if(materialClassificationMapping==null && ObjectUtil.isNotEmpty(kindCode)){
            return mapper.addCheck(kindCode);
        }
        return 1;
    }

}
