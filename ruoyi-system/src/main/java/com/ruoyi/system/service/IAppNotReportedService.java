package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.AppNotReported;

/**
 * @Author: jianguo
 * @Date: 2021/9/15 11:07
 */
public interface IAppNotReportedService extends IService<AppNotReported> {

    /**
     * 新增一条数据
     */
    Boolean saveData(SysUser user);

    /**
     * 批量删除数据
     * @param userIds
     */
    Boolean deleteDatas(Long[] userIds);
}
