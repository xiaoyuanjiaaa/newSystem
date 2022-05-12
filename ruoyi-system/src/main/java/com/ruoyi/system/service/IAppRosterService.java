package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.vo.UserRosterVO;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 9:32
 */
public interface IAppRosterService extends IService<AppRoster> {

    /**
     * 分页查询排班信息
     * @param appRosterQueryDTO
     * @return
     */
    IPage listPage(AppRosterQueryDTO appRosterQueryDTO);

    /**
     * 新增排班信息
     * @param appRoster
     * @return
     */
    Boolean saveOrUpdateRoster(AppRoster appRoster);


    /**
     * 删除已经排班的人员信息
     * @param idOne
     * @return
     */
    boolean removeRosterUserByIds(List<String> idOne);


}
