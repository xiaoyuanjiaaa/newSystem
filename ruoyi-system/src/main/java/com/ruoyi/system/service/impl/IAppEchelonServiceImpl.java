package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.AppEchelonDTO;
import com.ruoyi.system.dto.AppEchelonUserDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.entity.AppEchelonUser;
import com.ruoyi.system.entity.AppEpidemicUser;
import com.ruoyi.system.mapper.AppEchelonMapper;
import com.ruoyi.system.service.IAppEchelonService;
import com.ruoyi.system.service.IAppEchelonUserService;
import com.ruoyi.system.service.IAppEpidemicUserService;
import com.ruoyi.system.service.ISysUserService;
import com.tencentcloudapi.cms.v20190321.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 20:48
 */
@Service
public class IAppEchelonServiceImpl extends ServiceImpl<AppEchelonMapper, AppEchelon> implements IAppEchelonService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private IAppEchelonUserService echelonUserService;
    @Autowired
    private IAppEpidemicUserService epidemicUserService;

    @Override
    public IPage<AppEchelon> listPage(AppEchelonDTO appEchelonDTO) {
        QueryWrapper<AppEchelon> queryWrapper = new QueryWrapper<>();
        if (appEchelonDTO.getEchelonName() != null) {
            queryWrapper.like("echelon_name", appEchelonDTO.getEchelonName());
        }
        if (appEchelonDTO.getIsAsc().equals("desc")) {
            queryWrapper.orderByDesc("create_time");
        } else {
            queryWrapper.orderByAsc("create_time");
        }
        IPage<AppEchelon> page = new Page<>();
        page.setCurrent(appEchelonDTO.getPageNum());
        page.setSize(appEchelonDTO.getPageSize());
        IPage<AppEchelon> rsult = page(page, queryWrapper);
        List<AppEchelon> records = rsult.getRecords();
        for (AppEchelon record : records) {
            List<AppEpidemicUser> echelonUsers = epidemicUserService.
                    list(new QueryWrapper<AppEpidemicUser>().eq("echelon_id", record.getId()));
                record.setAppEpidemicUsers(echelonUsers);
        }
        return rsult;
    }

    @Override
    public boolean removeByIdsAll(List<String> idOne) {
        return false;
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean saveUser(AppEchelonUserDTO appEchelonUserDTO) {
        if (appEchelonUserDTO.getEchelonId() == null){
            throw new CustomException("请选择梯队");
        }
        AppEchelon appEchelon = getById(appEchelonUserDTO.getEchelonId());
        if (appEchelon == null){
            throw new CustomException("请选择正确梯队");
        }
        List<Long> userIds = appEchelonUserDTO.getUserIds();
        if (CollectionUtils.isEmpty(userIds)) {
            throw new CustomException("请选择人员");
        }

        AppEchelon byId = getById(appEchelonUserDTO.getEchelonId());
        if (byId == null){
            throw new CustomException("无关联梯队");
        }
        List<SysUser> sysUsers = userService.selectUserByIds(userIds);
        System.out.println(sysUsers);
        // 在梯队中的人
        List<SysUser> sysUsersTwo = sysUsers.stream().
                filter(user -> user.getIsEchelon() != null).collect(Collectors.toList());
        // 不在梯队中的人
        List<SysUser> sysUsersThree = sysUsers.stream().
                filter(user -> user.getIsEchelon() == null).collect(Collectors.toList());

        List<AppEpidemicUser> appEpidemicUsers = new ArrayList<>(); //

        if (!CollectionUtils.isEmpty(sysUsersTwo)){
            for (SysUser sysUser : sysUsersTwo) {
                sysUser.setIsEchelon(appEchelonUserDTO.getEchelonId());
                AppEpidemicUser appEpidemicUser = AppEpidemicUser.builder()
                        .echelonId(appEchelonUserDTO.getEchelonId())
                        .userId(sysUser.getUserId())
                        .userName(sysUser.getUserName())
                        .workStatus(3)
                        .deptId(sysUser.getDeptId())
                        .nickName(sysUser.getNickName())
                        .deptId(sysUser.getDeptId())
                        .phone(sysUser.getPhonenumber())
                        .echelonName(byId.getEchelonName())
                        .userId(sysUser.getUserId())
                        .build();
                appEpidemicUsers.add(appEpidemicUser);
                userService.updateUser(sysUser); // 修改用户信息
            }
            List<Long> sysUsersIds = sysUsersTwo.stream().map(SysUser::getUserId).collect(Collectors.toList());
            boolean isDeleteSysUser = epidemicUserService.remove(new QueryWrapper<AppEpidemicUser>().in("user_id",sysUsersIds));// 删除已经在梯队中的人员与梯队关联数据

            if (!isDeleteSysUser) {
                throw new CustomException("未能重置人员与梯队的关联信息", 500);
            }
        }
        for (SysUser sysUser : sysUsersThree) {
            sysUser.setIsEchelon(appEchelonUserDTO.getEchelonId());
            AppEpidemicUser appEpidemicUser = AppEpidemicUser.builder()
                    .echelonId(appEchelonUserDTO.getEchelonId())
                    .userId(sysUser.getUserId())
                    .userName(sysUser.getUserName())
                    .workStatus(3)
                    .deptId(sysUser.getDeptId())
                    .nickName(sysUser.getNickName())
                    .echelonName(byId.getEchelonName())
                    .phone(sysUser.getPhonenumber())
                    .userId(sysUser.getUserId())
                    .build();
            appEpidemicUsers.add(appEpidemicUser);
            userService.updateUser(sysUser); // 修改用户信息
        }

        List<AppEpidemicUser> echelonUsers = epidemicUserService.list(new QueryWrapper<AppEpidemicUser>().eq("echelon_id", appEchelonUserDTO.getEchelonId()));
        if (appEchelon.getEchelonNum() < echelonUsers.size() + appEpidemicUsers.size()){ // 判断梯队人员存储量
            throw new CustomException("梯队人员可存数量不足", 500);
        }
        boolean isSaveEchelonUser = epidemicUserService.saveBatch(appEpidemicUsers);// 存储人员与梯队关联数据
        if (!isSaveEchelonUser) {
            throw new CustomException("未能成功存储人员与梯队关联信息", 500);
        }
        return true;
    }

    @Override
    public AppEchelon getByIdAndUser(Long id) {
        AppEchelon appEchelon = getById(id);
        List<AppEpidemicUser> echelonUsers = epidemicUserService.
                list(new QueryWrapper<AppEpidemicUser>().eq("echelon_id", id));
        appEchelon.setAppEpidemicUsers(echelonUsers);
        return appEchelon;
    }

    @Override
    public boolean deleteEchelonUser(Long id) {
        AppEpidemicUser byId = epidemicUserService.getById(id);
        SysUser byId1 = userService.selectUserById(byId.getUserId());
        int i = userService.updateUserSetEL(byId.getUserId());
        if (i != 1){
            return false;
        }
        if (byId.getWorkStatus() != 3){
            return false;
        }
        int delete = epidemicUserService.delete(id);
        if (delete != 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean judge(AppEchelon appEchelon) {
        List<AppEpidemicUser> epidemicUsers = epidemicUserService.
                list(new QueryWrapper<AppEpidemicUser>().eq("echelon_id", appEchelon.getId()));
        if (!CollectionUtils.isEmpty(epidemicUsers) && epidemicUsers.size() > appEchelon.getEchelonNum()){
                return false;
        }
        return true;
    }

    @Override
    public boolean saveOrUpdateAndUser(AppEchelon appEchelon) {
        boolean b = saveOrUpdate(appEchelon);
        if (b){
            List<AppEpidemicUser> epidemicUsers = epidemicUserService.
                    list(new QueryWrapper<AppEpidemicUser>().eq("echelon_id", appEchelon.getId()));
            if (!CollectionUtils.isEmpty(epidemicUsers)){
                for (AppEpidemicUser epidemicUser : epidemicUsers) {
                    epidemicUser.setEchelonName(appEchelon.getEchelonName());
                }
                boolean b1 = epidemicUserService.updateBatchById(epidemicUsers);
               if (!b1){
                   throw new CustomException("修改梯队人员信息失败");
               }
            }
            return b;
        }
        return b;
    }


}
