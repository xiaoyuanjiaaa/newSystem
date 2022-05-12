package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.NucleicAcidThreshold;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NucleicAcidThresholdMapper extends BaseMapper<NucleicAcidThreshold> {

    List<NucleicAcid> getListByBoolean(@Param("type") String type);


    List<NucleicAcid> getListByNumber(@Param("type") String type);



}
