package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.AppEchelonDTO;
import com.ruoyi.system.dto.AppEpidemicUserDTO;
import com.ruoyi.system.dto.RosterDTO;
import com.ruoyi.system.dto.UserRosterDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.AppEpidemicUserMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 19:50
 */
@Service
public class IAppEpidemicUserServiceImpl extends ServiceImpl<AppEpidemicUserMapper, AppEpidemicUser> implements IAppEpidemicUserService {

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IAppRosterService rosterService;

    @Autowired
    private IAppDutyService dutyService;

    @Autowired
    private IAppRosterUserService rosterUserService;


    @Override
    public IPage listPage(AppEpidemicUserDTO epidemicUserDTO) {
        QueryWrapper<AppEpidemicUser> queryWrapper = new QueryWrapper<>();
        if (epidemicUserDTO.getUserName() != null){
            queryWrapper.like("user_name",epidemicUserDTO.getUserName()); // 用户姓名
        }
        if (epidemicUserDTO.getNickName() != null){
            queryWrapper.like("nick_name",epidemicUserDTO.getNickName()); // 用户昵称
        }
        if (epidemicUserDTO.getPhone() != null){
            queryWrapper.like("phone",epidemicUserDTO.getPhone()); // 用户手机
        }
        if (epidemicUserDTO.getWorkStatus() != null){
            queryWrapper.eq("work_status",epidemicUserDTO.getWorkStatus()); // 用户工作状态
        }
        if (epidemicUserDTO.getWorkAddress() != null){
            queryWrapper.like("work_address",epidemicUserDTO.getWorkAddress()); // 用户工作地址
        }
        if (epidemicUserDTO.getEchelonId() != null){
            queryWrapper.like("echelon_id",epidemicUserDTO.getEchelonId()); // 梯队id
        }
        if (epidemicUserDTO.getDeptId() != null){
            queryWrapper.like("dept_id",epidemicUserDTO.getDeptId()); // 部门id
        }
        if (epidemicUserDTO.getInTime() != null){
            queryWrapper.like("int_time",epidemicUserDTO.getInTime()); // 进入时间
        }
        if (epidemicUserDTO.getSuggestOutTime() != null){
            queryWrapper.like("suggest_out_time",epidemicUserDTO.getSuggestOutTime()); // 建议出去时间
        }
        if (epidemicUserDTO.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("create_time");
        }else {
            queryWrapper.orderByAsc("create_time");
        }
        IPage<AppEpidemicUser> page = new Page<>();
        page.setCurrent(epidemicUserDTO.getPageNum());
        page.setSize(epidemicUserDTO.getPageSize());
        IPage <AppEpidemicUser> rsult = page(page,queryWrapper);
        if (!CollectionUtils.isEmpty(rsult.getRecords())){
            for (AppEpidemicUser record : rsult.getRecords()) {
                SysDept dept = deptService.selectDeptById(record.getDeptId());
                if (dept != null){
                    record.setDeptName(dept.getDeptName());
                }
                List<AppRosterUser> appRosterUserList =
                        rosterUserService.list(new QueryWrapper<AppRosterUser>().eq("user_id", record.getUserId()));
                for (AppRosterUser appRosterUser : appRosterUserList) {
                    AppRoster roster = rosterService.getById(appRosterUser.getRosterId());
                    appRosterUser.setAppRoster(roster);
                    AppDuty duty = dutyService.getById(appRosterUser.getDutyId());
                    appRosterUser.setAppDuty(duty);
                }
                record.setAppRosterUserList(appRosterUserList);
            }
        }
        if (!CollectionUtils.isEmpty(rsult.getRecords())){
            for (AppEpidemicUser record : rsult.getRecords()) {
                SysDept dept = deptService.selectDeptById(record.getDeptId());
                if (dept != null){
                    record.setDeptName(dept.getDeptName());
                }
                List<AppRosterUser> appRosterUserList =
                        rosterUserService.list(new QueryWrapper<AppRosterUser>().eq("user_id", record.getUserId()));
                for (AppRosterUser appRosterUser : appRosterUserList) {
                    AppRoster roster = rosterService.getById(appRosterUser.getRosterId());
                    appRosterUser.setAppRoster(roster);
                    AppDuty duty = dutyService.getById(appRosterUser.getDutyId());
                    appRosterUser.setAppDuty(duty);
                }
                record.setAppRosterUserList(appRosterUserList);
            }
        }
        return rsult;
    }

    @Override
    public int delete(Long id) {
        int i = baseMapper.deleteByIdYes(id);
        return i;
    }

    @Override
    public boolean addEpidemicRoster(UserRosterDTO userRosterDTO) {
        if (userRosterDTO.getUserId() == null) throw new CustomException("请选择正确的人员");
        AppEpidemicUser user = getOne(new QueryWrapper<AppEpidemicUser>().eq("user_id", userRosterDTO.getUserId()));
        if (user.getWorkStatus() != 1) throw new CustomException("只有负压病房的人才可以排班");
        if (userRosterDTO == null){
            throw new CustomException("请选择正确的人员");
        }
        if (CollectionUtils.isEmpty(userRosterDTO.getRosterDTOS())){
            throw new CustomException("请指定排班信息");
        }
        AppEpidemicUser epidemicUser = getOne(new QueryWrapper<AppEpidemicUser>().eq("user_id",userRosterDTO.getUserId()));
        List<RosterDTO> rosterDTOS = userRosterDTO.getRosterDTOS();
        List<AppRosterUser> appRosterUserList = new ArrayList<>();
        for (RosterDTO rosterDTO : rosterDTOS) {
            AppRoster roster = rosterService.getById(rosterDTO.getRosterId());
            AppDuty duty = dutyService.getById(rosterDTO.getDutyId());
            AppRosterUser appRosterUser = AppRosterUser.builder()
                    .userId(userRosterDTO.getUserId())
                    .rosterId(rosterDTO.getRosterId())
                    .rosterName(roster.getRosterName())
                    .timeStartOne(roster.getTimeStartOne())
                    .timeStartTwo(roster.getTimeStartTwo())
                    .timeEndTwo(roster.getTimeEndTwo())
                    .timeEndOne(roster.getTimeEndOne())
                    .nikeName(epidemicUser.getNickName())
                    .rosterTime(rosterDTO.getRosterTime())
                    .dutyId(rosterDTO.getDutyId())
                    .dutyName(duty.getDutyName())
                    .build();
            appRosterUserList.add(appRosterUser);
        }
        boolean b = rosterUserService.saveBatch(appRosterUserList);
        return b;
    }

    @Override
    public AppEpidemicUser getEpidemicUser(Long id) {
        AppEpidemicUser epidemicUser = getById(id);
        if (epidemicUser == null){
            throw new CustomException("未查到指定人员信息");
        }
        List<AppRosterUser> appRosterUserList =
                rosterUserService.list(new QueryWrapper<AppRosterUser>().eq("user_id", epidemicUser.getUserId()));
        for (AppRosterUser appRosterUser : appRosterUserList) {
            AppRoster roster = rosterService.getById(appRosterUser.getRosterId());
            appRosterUser.setAppRoster(roster);
            AppDuty duty = dutyService.getById(appRosterUser.getDutyId());
            appRosterUser.setAppDuty(duty);
        }
        if (CollectionUtils.isEmpty(appRosterUserList)){
            epidemicUser.setRest("休息");
        }
        epidemicUser.setAppRosterUserList(appRosterUserList);
        return epidemicUser;
    }
}
