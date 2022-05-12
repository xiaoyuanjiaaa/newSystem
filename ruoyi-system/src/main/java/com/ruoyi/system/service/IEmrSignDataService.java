package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.EmrSignData;

import java.util.List;

public interface IEmrSignDataService extends IService<EmrSignData> {

    List<EmrSignData> getList();

}
