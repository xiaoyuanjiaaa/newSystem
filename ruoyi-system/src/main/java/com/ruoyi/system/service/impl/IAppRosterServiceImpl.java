package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.mapper.AppRosterMapper;
import com.ruoyi.system.mapper.AppRosterUserMapper;
import com.ruoyi.system.service.IAppEpidemicUserService;
import com.ruoyi.system.service.IAppRosterService;
import com.ruoyi.system.service.IAppRosterUserService;
import com.ruoyi.system.vo.UserRosterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 9:32
 */
@Service
public class IAppRosterServiceImpl extends ServiceImpl<AppRosterMapper, AppRoster> implements IAppRosterService {

    @Autowired
    private IAppEpidemicUserService epidemicUserService;
    @Autowired
    private IAppRosterUserService rosterUserService;

    @Override
    public IPage listPage(AppRosterQueryDTO appRosterQueryDTO) {
        QueryWrapper<AppRoster> queryWrapper = new QueryWrapper<>();
        if (appRosterQueryDTO.getRosterTime() != null){
            queryWrapper.like("roster_name",appRosterQueryDTO.getRosterTime());
        }
        if (appRosterQueryDTO.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("create_time");
        }else {
            queryWrapper.orderByAsc("create_time");
        }
        queryWrapper.eq("create_by",appRosterQueryDTO.getCreateBy());
        queryWrapper.eq("type",appRosterQueryDTO.getType ());//添加数据隔离校验
        IPage<AppRoster> page = new Page<>();
        page.setCurrent(appRosterQueryDTO.getPageNum());
        page.setSize(appRosterQueryDTO.getPageSize());
        IPage <AppRoster> rsult = page(page,queryWrapper);
        return rsult;
    }

    @Override
    public Boolean saveOrUpdateRoster(AppRoster appRoster) {
        if (appRoster.getId() == null){
            appRoster.setId(IdWorker.getId());
        }
        return saveOrUpdate(appRoster);
    }

    @Override
    public boolean removeRosterUserByIds(List<String> idOne) {
        boolean b = rosterUserService.removeByIds(idOne);
        return b;
    }



}
