package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppDestinationQueryDTO;
import com.ruoyi.system.entity.AppDestination;
import com.ruoyi.system.mapper.AppDestinationMapper;
import com.ruoyi.system.service.IAppDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:10
 */
@Service
public class AppDestinationServiceImpl extends ServiceImpl<AppDestinationMapper, AppDestination> implements IAppDestinationService {

   @Autowired
   private AppDestinationMapper appDestinationMapper;

    @Override
    public PageInfo<AppDestination> pageAppDestination(AppDestinationQueryDTO queryDTO) {

        QueryWrapper<AppDestination> queryWrapper = new QueryWrapper<>();
        if (queryDTO.getType() != null){
            queryWrapper.eq("type",queryDTO.getType());
        }
        if (queryDTO.getStatus() != null){
            queryWrapper.eq("status",queryDTO.getStatus());
        }
        PageHelper.startPage(queryDTO.getPageNum(),queryDTO.getPageSize(),queryDTO.getOrderBy());
        List<AppDestination> appDestinations = list(queryWrapper);
        return new PageInfo<>(appDestinations);
    }

    @Override
    public void addAppDestination(AppDestination appDestination) {
        baseMapper.insert(appDestination);
    }

    @Override
    public List<AppDestination> destinationList(Integer type) {
        QueryWrapper<AppDestination> queryWrapper = new QueryWrapper<>();
        if (type != null){
            queryWrapper.eq("type",type);
        }
        List<AppDestination> appDestinations = list(queryWrapper);
        return appDestinations;
    }
}
