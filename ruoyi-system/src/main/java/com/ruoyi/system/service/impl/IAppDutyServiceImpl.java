package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.AppDutyDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.mapper.AppDutyMapper;
import com.ruoyi.system.service.IAppDutyService;
import com.ruoyi.system.service.IAppRosterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:26
 */
@Service
public class IAppDutyServiceImpl extends ServiceImpl<AppDutyMapper, AppDuty> implements IAppDutyService {

    @Autowired
    private IAppRosterUserService appRosterUserService;

    @Override
    public IPage<AppDuty> listPage(AppDutyDTO appDutyDTO) {
        QueryWrapper<AppDuty> queryWrapper = new QueryWrapper<>();
        if (appDutyDTO.getDutyName() != null){
            queryWrapper.like("duty_name",appDutyDTO.getDutyName());
        }
        if (appDutyDTO.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("create_time");
        }else {
            queryWrapper.orderByAsc("create_time");
        }
        IPage<AppDuty> page = new Page<>();
        page.setCurrent(appDutyDTO.getPageNum());
        page.setSize(appDutyDTO.getPageSize());
        IPage <AppDuty> rsult = page(page,queryWrapper);
        return rsult;
    }

    @Override
    public Boolean updateRosterUser(AppDuty appDuty) {
        List<AppRosterUser> dutyId = appRosterUserService.
                list(new QueryWrapper<AppRosterUser>().eq("duty_id", appDuty.getId()));
        if (!CollectionUtils.isEmpty(dutyId)){
            for (AppRosterUser appRosterUser : dutyId) {
                appRosterUser.setDutyName(appDuty.getDutyName());
            }
            boolean b = appRosterUserService.saveOrUpdateBatch(dutyId);
            if (!b){
                throw new CustomException("修改人员排班信息失败");
            }
        }
        return true;
    }


}
