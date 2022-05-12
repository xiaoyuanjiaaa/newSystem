package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppDestinationQueryDTO;
import com.ruoyi.system.entity.AppDestination;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IAppDestinationService extends IService<AppDestination> {


    PageInfo<AppDestination> pageAppDestination(AppDestinationQueryDTO queryDTO);

    void addAppDestination(AppDestination appDestination);
    //目的地查询不分页
    List<AppDestination> destinationList(Integer type);
}
