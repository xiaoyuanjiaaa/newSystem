package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.entity.CheckData;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:15
 */

public interface ICheckDataService extends IService<CheckData> {

    void jsonTransToDB(AppPersonWx appPersonWx);
}
