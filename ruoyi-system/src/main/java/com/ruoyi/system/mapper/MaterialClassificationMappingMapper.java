package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.MaterialClassificationMapping;


public interface MaterialClassificationMappingMapper extends BaseMapper<MaterialClassificationMapping> {

    /**
     * 选择勾选
     * @return
     */
    int delAll(String kindCode);
    /**
     * 选择勾选
     * @return
     */
    int addCheck(String kindCode);

}
