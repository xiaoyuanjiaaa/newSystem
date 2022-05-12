package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.NegativePressureWard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NegativePressureWardMapper extends BaseMapper<NegativePressureWard> {

    List<NegativePressureWard> getList(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

}
