package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.MaterialClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MaterialClassificationMapper extends BaseMapper<MaterialClassification> {

    List<MaterialClassification> selectListByWrapper(MaterialClassification materialClassification);

    int updateMaterial(MaterialClassification materialClassification);

    MaterialClassification getOneByCode(@Param("kindCode") String kindCode);

    int delByCode(@Param("kindCode")String kindCode);

    List<MaterialClassification> getDown();
}
