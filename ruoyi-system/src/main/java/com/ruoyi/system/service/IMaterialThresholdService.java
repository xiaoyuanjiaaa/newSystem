package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.MaterialThreshold;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMaterialThresholdService extends IService<MaterialThreshold> {

    List<MaterialThreshold> list(MaterialThreshold materialThreshold);

    MaterialThreshold getOneById(Long id);

    int add(MaterialThreshold materialThreshold);

    int updateThreshold(MaterialThreshold materialThreshold);

    int del(Long id);

    List<MaterialThreshold> getThreshold(MaterialThreshold materialThreshold);

    MaterialThreshold getAllThreshold();

    int updateAllThreshold(MaterialThreshold materialThreshold);

    MaterialThreshold getOne(Long id);

    Long getOneThresholdNumber(MaterialThreshold materialThreshold);


    int insertNoOut(MaterialThreshold materialThreshold);

    MaterialThreshold getOneNoOutput(Long id);

}
