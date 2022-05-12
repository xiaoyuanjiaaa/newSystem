package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.MaterialClassification;
import com.ruoyi.system.entity.MaterialClassificationMapping;
import com.ruoyi.system.mapper.MaterialClassificationMappingMapper;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IMaterialClassificationMappingService extends IService<MaterialClassificationMapping> {
    /**
     * 选择勾选添加
     * @param kindCode
     * @return
     */
    int addCheck(String kindCode);


}
