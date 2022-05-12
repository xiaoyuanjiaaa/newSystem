package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEpidemicUser;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.excel.AppRosterUserExcel;
import com.ruoyi.system.mapper.AppRosterUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.UserRosterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:14
 */
@Service
public class IAppRosterUserServiceImpl extends ServiceImpl<AppRosterUserMapper, AppRosterUser> implements IAppRosterUserService {

    @Autowired
    private IAppEpidemicUserService appEpidemicUserService;
    @Autowired
    private IAppDutyService appDutyService;
    @Autowired
    private IAppRosterService appRosterService;
    @Autowired
    private ISysDeptService sysDeptService;


    @Override
    public PageInfo<UserRosterVO> pageList(AppRosterQueryDTO appRosterQueryDTO) {
        QueryWrapper<AppRosterUser> queryWrapper=new QueryWrapper<>();
        if(appRosterQueryDTO.getNickName()!=null){
            queryWrapper.like("nike_name",appRosterQueryDTO.getNickName());
        }
        //先根据时间查询当前排班时间是不是有人值班
        if(appRosterQueryDTO.getRosterTime()!=null){
            queryWrapper.eq("roster_time",appRosterQueryDTO.getRosterTime());
        }
        if(appRosterQueryDTO.getRosterId()!=null){
            queryWrapper.eq("roster_id",appRosterQueryDTO.getRosterId());
        }
//        queryWrapper.eq("work_status",3);
//        queryWrapper.groupBy("roster_time");
        queryWrapper.eq("is_deleted",0);
        PageHelper.startPage(appRosterQueryDTO.getPageNum(),appRosterQueryDTO.getPageSize(),appRosterQueryDTO.getOrderBy());
        List<UserRosterVO> userRosterVOS = baseMapper.selectListWrapper(queryWrapper);//值班人员
        if(userRosterVOS.size()>0){ //
            //值班人员id
            List<Long> ids=new ArrayList<>();
            for (UserRosterVO userRosterVO : userRosterVOS) {
//                userRosterVO.setStatusName("值班中");
//                AppDuty appDuty = appDutyService.getById(userRosterVO.getDutyId());
                userRosterVO.setDutyName(userRosterVO.getDutyName());
//                AppRoster appRoster = appRosterService.getById(userRosterVO.getRosterId());
                userRosterVO.setRosterName(userRosterVO.getRosterName());

                ids.add(userRosterVO.getUserId());//获取值班防疫人员id
            }
//            QueryWrapper<AppEpidemicUser> queryWrapper1=new QueryWrapper<>();
//            queryWrapper1.notIn("user_id",ids);
//            List<AppEpidemicUser> list = appEpidemicUserService.list(queryWrapper1);
//            if(list.size()>0){
//                for (AppEpidemicUser appEpidemicUser : list) {
//                    userRosterVO.setStatusName("休息中");
//                    userRosterVO.setNickName(appEpidemicUser.getNickName());
//                }
//            }
        }
        return new PageInfo<>(userRosterVOS);
    }

    @Override
    public List<AppRosterUserExcel> exportList(AppRosterQueryDTO appRosterQueryDTO) {
        QueryWrapper<AppRosterUser> queryWrapper=new QueryWrapper<>();
        //先根据时间查询当前排班时间是不是有人值班
        if(appRosterQueryDTO.getRosterTime()!=null){
            queryWrapper.eq("roster_time",appRosterQueryDTO.getRosterTime());
        }
        if(appRosterQueryDTO.getRosterId()!=null){
            queryWrapper.eq("roster_id",appRosterQueryDTO.getRosterId());
        }
        PageHelper.startPage(appRosterQueryDTO.getPageNum(),appRosterQueryDTO.getPageSize(),appRosterQueryDTO.getOrderBy());
        List<UserRosterVO> userRosterVOS = baseMapper.selectListWrapper(queryWrapper);//值班人员
        List<AppRosterUserExcel> list=new ArrayList<>();
        if(userRosterVOS.size()>0){ //
            //值班人员id
            for (UserRosterVO userRosterVO : userRosterVOS) {
                AppDuty appDuty = appDutyService.getById(userRosterVO.getDutyId());
                userRosterVO.setDutyName(appDuty.getDutyName());
                AppRoster appRoster = appRosterService.getById(userRosterVO.getRosterId());
                userRosterVO.setRosterName(appRoster.getRosterName());
               QueryWrapper<AppEpidemicUser> queryWrapper1=new QueryWrapper<>();
               queryWrapper1.eq("user_id",userRosterVO.getUserId());
                AppEpidemicUser one = appEpidemicUserService.getOne(queryWrapper1);
                SysDept sysDept = sysDeptService.selectDeptById(one.getDeptId());
                userRosterVO.setDeptId(one.getDeptId());
                userRosterVO.setDeptName(sysDept.getDeptName());

                //把数据塞进导出表
                AppRosterUserExcel appRosterUserExcel=new AppRosterUserExcel();
                appRosterUserExcel.setNikeName(userRosterVO.getNikeName());
                appRosterUserExcel.setPhoneNumber(one.getPhone());
                appRosterUserExcel.setDeptName(sysDept.getDeptName());
                appRosterUserExcel.setRosterTime(userRosterVO.getRosterTime());
                appRosterUserExcel.setRosterName(userRosterVO.getRosterName());
                appRosterUserExcel.setDutyName(userRosterVO.getDutyName());
                appRosterUserExcel.setEchelonName(one.getEchelonName());
                list.add(appRosterUserExcel);
            }
    }

        return list;
    }
}

