package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.CheckLeaderStatus;
import com.ruoyi.common.enums.DeptExamineStatus;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.BaseException;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.bo.DeptExamineListBO;
import com.ruoyi.system.dto.DeptExamineDTO;
import com.ruoyi.system.dto.DeptExamineListDTO;
import com.ruoyi.system.entity.DeptChangeExamine;
import com.ruoyi.system.entity.DoorPic;
import com.ruoyi.system.mapper.DeptChangeExamineMapper;
import com.ruoyi.system.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.vo.DeptExamineListVO;
import com.ruoyi.system.vo.DeptListVO;
import com.ruoyi.system.vo.DeptStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 片区更换审核表 服务实现类
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-07
 */
@Service
@Slf4j
public class DeptChangeExamineServiceImpl extends ServiceImpl<DeptChangeExamineMapper, DeptChangeExamine> implements IDeptChangeExamineService {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IAppHealthReportService appHealthReportService;
    @Autowired
    private IDoorPicService doorPicService;
    @Override
    public ResultVO<List<DeptListVO>> deptList() {
        List<DeptListVO> result=this.baseMapper.deptList();
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, result);
    }

    @Override
    public ResultVO<Boolean> udpateDept(Integer deptChangeId) {
        Long userId = SecurityUtils.getUserId();
        Long deptId=SecurityUtils.getLoginUser().getUser().getDeptId();
        //获取变更后的部门 通过部门id获取部门名称
        SysDept sysDeptchange=sysDeptService.getOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getDeptId,deptChangeId));
        if (ObjectUtil.isNull(sysDeptchange)) {
            throw new CustomException(FailEnums.DEPT_NOT_EXIST);
        }
        //通过用户id获取审核记录
        DeptChangeExamine getOne=this.baseMapper.selectOne(new LambdaQueryWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId, userId));
        if(ObjectUtil.isNull(getOne)){
            //如果审核记录为空  就添加一条新的审核记录
            DeptChangeExamine DeptChangeExamine = new DeptChangeExamine().setId(IdWorker.getId()).setDeptChangeId(deptChangeId)
                    .setUserId(userId).setCreateTime(LocalDateTime.now()).setFlag(DeptExamineStatus.EXAMINE.getCode());
            return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,this.save(DeptChangeExamine));
        }
        //如果片区处于审核中 就不能进行申请变更
        if(ObjectUtil.isNotNull(getOne.getFlag())) {
            if (DeptExamineStatus.EXAMINE.getCode() == getOne.getFlag()) {
                throw new CustomException(FailEnums.REFUSE_UPDATE);
            }
        }
        //更改审核状态
        Boolean result=this.update(new LambdaUpdateWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId, userId).
                set(DeptChangeExamine::getDeptChangeId,deptChangeId)
                .set(DeptChangeExamine::getCreateTime,LocalDateTime.now()).set(DeptChangeExamine::getFlag,DeptExamineStatus.EXAMINE.getCode()));
        return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,result);
    }

    @Override
    public TableDataInfo deptExamineList(DeptExamineListDTO dto) {
        DeptExamineListBO deptExamineListBO = new DeptExamineListBO();
        BeanUtils.copyProperties(dto,deptExamineListBO);
        boolean deptAdmin = appHealthReportService.isDeptAdmin(SecurityUtils.getLoginUser().getUser());
        //当前用户包含的角色
        List<SysRole> roles= SecurityUtils.getLoginUser().getUser().getRoles();
        //如果是系统管理员或者超级管理员 可以审核所有
        List<SysRole> newRoles=roles.stream().filter(sysRole -> sysRole.getRoleId()==115).collect(Collectors.toList());
        if(newRoles.size()>0 || SecurityUtils.getLoginUser().getUser().isAdmin()){
            Page<DeptExamineListVO> result=this.baseMapper.deptExamineList(deptExamineListBO, new Page<>(dto.getPageNum(), dto.getPageSize()));
            Map<Long,String> deptMap=sysDeptService.selectDeptList(new SysDept()).stream().collect(Collectors.toMap(SysDept::getDeptId,SysDept::getDeptName));
            for(DeptExamineListVO  deptExamineListVO:result.getRecords()){
                deptExamineListVO.setDeptChange(deptMap.get(deptExamineListVO.getDeptChangeId().longValue()));
            }
            return new TableDataInfo(result.getRecords(),(int)result.getTotal());
        }
        //只能看到当前负责人的片区审核列表：
        //1通过当前用户名称对比片区负责人名称 如果为空则返回为空
        if(!deptAdmin){
            return new TableDataInfo();
        }
        //2过滤除负责的片区审核列表
        deptExamineListBO.setDeptId(SecurityUtils.getDeptId().intValue());
        Page<DeptExamineListVO> result=this.baseMapper.deptExamineList(deptExamineListBO, new Page<>(dto.getPageNum(), dto.getPageSize()));
        Map<Long,String> deptMap=sysDeptService.selectDeptList(new SysDept()).stream().collect(Collectors.toMap(SysDept::getDeptId,SysDept::getDeptName));
        for(DeptExamineListVO  deptExamineListVO:result.getRecords()){
            deptExamineListVO.setDeptChange(deptMap.get(deptExamineListVO.getDeptChangeId().longValue()));
        }
        return new TableDataInfo(result.getRecords(),(int)result.getTotal());
    }

    @Override
    public AjaxResult deptExamine(DeptExamineDTO dto) {
        if(dto.getType()!=1&&dto.getType()!=2){
            return null;
        }
        DeptChangeExamine deptChangeExamine=this.getOne(new LambdaQueryWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId, dto.getUserId()));
        if(ObjectUtil.isNull(deptChangeExamine)){
            throw new CustomException(FailEnums.DEPT_NOT_EXIST);
        }
        if(dto.getType()==1){
            sysUserService.update(new LambdaUpdateWrapper<SysUser>().eq(SysUser::getUserId, dto.getUserId()).set(SysUser::getDeptId, deptChangeExamine.getDeptChangeId()));
            this.update(new LambdaUpdateWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId,dto.getUserId())
                    .set(DeptChangeExamine::getFlag,DeptExamineStatus.EXAMINE_PASS.getCode()).set(DeptChangeExamine::getExamineTime,LocalDateTime.now()));
            return AjaxResult.success();
        }else if(dto.getType()==2){
            this.update(new LambdaUpdateWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId,dto.getUserId())
                    .set(DeptChangeExamine::getFlag,DeptExamineStatus.EXAMINE_REFUSE.getCode()).set(DeptChangeExamine::getExamineTime,LocalDateTime.now()));
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @Override
    public ResultVO<DeptStatusVO> deptStatus() {
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser=sysUserService.selectUserById(userId);
        //获取当前用户审核记录
        DeptChangeExamine deptChangeExamine=this.getOne(new LambdaQueryWrapper<DeptChangeExamine>().eq(DeptChangeExamine::getUserId,userId));
        DeptStatusVO deptStatusVO=new DeptStatusVO();
        DoorPic doorPic=doorPicService.getOne(new LambdaQueryWrapper<DoorPic>().eq(DoorPic::getUserId, userId));
        if(ObjectUtil.isNotNull(doorPic)){
            deptStatusVO.setQrcodeUrl(doorPic.getQrcodeUrl());
        }
        boolean deptAdmin = appHealthReportService.isDeptAdmin(SecurityUtils.getLoginUser().getUser());
        if(deptAdmin){
            deptStatusVO.setLeaderStatus(CheckLeaderStatus.LEADER_REFUSE.getCode());
        }else {
            deptStatusVO.setLeaderStatus(CheckLeaderStatus.LEADER_ALLOW.getCode());
        }
        SysDept currentDept = sysDeptService.selectDeptById(sysUser.getDeptId());
        //封装片区审核状态以及二维码图片
        if(ObjectUtil.isNotNull(deptChangeExamine)) {
            deptStatusVO.setStatus(deptChangeExamine.getFlag());
        }
        deptStatusVO.setDeptId(currentDept.getDeptId().intValue());
        deptStatusVO.setDeptName(currentDept.getDeptName());
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, deptStatusVO);
    }

    @Override
    public AjaxResult savePic(String url) {
        List<DoorPic> list=doorPicService.getBaseMapper().selectList(new LambdaQueryWrapper<DoorPic>().eq(DoorPic::getUserId, SecurityUtils.getUserId()));
        if(list.size()>0){
            doorPicService.update(new LambdaUpdateWrapper<DoorPic>().eq(DoorPic::getUserId, SecurityUtils.getUserId()).set(DoorPic::getQrcodeUrl, url)
                    .set(DoorPic::getUpdateTime, LocalDateTime.now()));
            return AjaxResult.success();
        }
        DoorPic doorPic=new DoorPic();
        doorPic.setUserId(SecurityUtils.getUserId());
        doorPic.setQrcodeUrl(url);
        doorPic.setCreateTime(LocalDateTime.now());
        doorPicService.save(doorPic);
        return AjaxResult.success();
    }
}
