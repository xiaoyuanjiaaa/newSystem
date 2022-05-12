package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.NegativePressureWardDTO;
import com.ruoyi.system.entity.NegativePressureWard;

import java.util.List;

public interface INegativePressureWardService extends IService<NegativePressureWard> {

    NegativePressureWardDTO negativePressureWard();
}
