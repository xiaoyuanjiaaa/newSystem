package com.ruoyi.system.service;

import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.vo.MaterialOutputVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMaterialOutputService {

    List<MaterialOutput> list(MaterialOutput materialOutput);

    List<MaterialOutputVo> getOutputNumber(Long id,Long evaluateDays);

//    List<MaterialOutputVo> getMaxNumber(Long id,Long evaluateDays);
}
