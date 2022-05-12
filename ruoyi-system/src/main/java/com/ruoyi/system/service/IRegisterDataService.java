package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.RegisterData;
import com.ruoyi.system.entity.SysRegister;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:15
 */

public interface IRegisterDataService extends IService<RegisterData> {

    void jsonTransToDB(SysRegister sysRegister);
}
