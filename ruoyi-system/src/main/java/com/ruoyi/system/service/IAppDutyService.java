package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppDutyDTO;
import com.ruoyi.system.entity.AppDuty;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:25
 */
public interface IAppDutyService extends IService<AppDuty> {


    /**
     * 列表分页查询
     * @param appDutyDTO
     */
    IPage<AppDuty> listPage(AppDutyDTO appDutyDTO);

    Boolean updateRosterUser(AppDuty appDuty);
}
