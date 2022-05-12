package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppEchelonDTO;
import com.ruoyi.system.dto.AppEpidemicUserDTO;
import com.ruoyi.system.dto.UserRosterDTO;
import com.ruoyi.system.entity.AppEpidemicUser;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 19:49
 */
public interface IAppEpidemicUserService extends IService<AppEpidemicUser> {

    /**
     * 分页查询梯队列表
     * @param epidemicUserDTO
     */
    IPage listPage(AppEpidemicUserDTO epidemicUserDTO);

    int delete(Long id);

    /**
     * 为人员排班
     * @param userRosterDTO
     * @return
     */
    boolean addEpidemicRoster(UserRosterDTO userRosterDTO);

    /**
     * 获取防疫人员信息
     * @param id
     * @return
     */
    AppEpidemicUser getEpidemicUser(Long id);
}
