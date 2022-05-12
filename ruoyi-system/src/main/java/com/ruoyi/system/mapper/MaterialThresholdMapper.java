package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.MaterialThreshold;
import com.ruoyi.system.entity.vo.SuppliesThresholdVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MaterialThresholdMapper extends BaseMapper<MaterialThreshold> {

    int add(MaterialThreshold materialThreshold);

    int insertNoOut(MaterialThreshold materialThreshold);

    int updateNoOut(@Param("id")Long id,@Param("safetyReserve")Long safetyReserve);

    int updateThreshold(MaterialThreshold materialThreshold);

    int saveAll(@Param("materialThresholdList") List<MaterialThreshold> materialThresholdList);

    MaterialThreshold getOneBuSuppliesId(@Param("id") Long id);

    int del(Long id);

    MaterialThreshold getOneById(@Param("id") Long id);

    MaterialThreshold getAllThreshold();

    int updateAllThreshold(MaterialThreshold materialThreshold);

    MaterialThreshold getOne(@Param("id") Long id);

    int bactchInsert(List<MaterialThreshold> thresholdList);

    MaterialThreshold getOneNoOutput(@Param("suppliersId") Long suppliersId);

    List<SuppliesThresholdVo> getSuppliesThreshold();



}
