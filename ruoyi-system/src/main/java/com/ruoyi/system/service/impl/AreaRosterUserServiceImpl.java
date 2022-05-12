package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.AreaRosterUserDTO;
import com.ruoyi.system.dto.AreaRosterUserQueryDTO;
import com.ruoyi.system.dto.RosterUserDTO;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.entity.AreaRosterUser;
import com.ruoyi.system.entity.WorkPlaceFrequency;
import com.ruoyi.system.excel.AreaRosterUserExcel;
import com.ruoyi.system.mapper.AreaRosterUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AreaRosterUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaRosterUserServiceImpl extends ServiceImpl<AreaRosterUserMapper,AreaRosterUser> implements IAreaRosterUserService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IAppRosterService rosterService;
    @Autowired
    private WorkPlaceService workPlaceService;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AreaRosterUser selectAreaRosterUserById(Long id)
    {
        return baseMapper.selectAreaRosterUserById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AreaRosterUser> selectAreaRosterUserList(AreaRosterUser areaRosterUser)
    {
        return baseMapper.selectAreaRosterUserList(areaRosterUser);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAreaRosterUser(AreaRosterUser areaRosterUser)
    {
        areaRosterUser.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertAreaRosterUser(areaRosterUser);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAreaRosterUser(AreaRosterUser areaRosterUser)
    {
        areaRosterUser.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateAreaRosterUser(areaRosterUser);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAreaRosterUserByIds(Long[] ids)
    {
        return baseMapper.deleteAreaRosterUserByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAreaRosterUserById(Long id)
    {
        return baseMapper.deleteAreaRosterUserById(id);
    }

    @Override
    public int addAreaRosterUser(AreaRosterUserDTO areaRosterUserDTO) {

        if (areaRosterUserDTO == null){
            throw new CustomException("请选择正确的人员");
        }
        if (CollectionUtils.isEmpty(areaRosterUserDTO.getRosterUserDTOS())){
            throw new CustomException("请指定排班信息");
        }

        SysUser sysUser = sysUserService.selectUserById(areaRosterUserDTO.getUserId());
        SysDept sysDept = sysDeptService.selectDeptById(sysUser.getDeptId());
        String deptName = sysDept.getDeptName();
        List<RosterUserDTO> rosterUserDTOS = areaRosterUserDTO.getRosterUserDTOS();
        int b=0;
        for (RosterUserDTO rosterUserDTO : rosterUserDTOS) {
            AppRoster appRoster = rosterService.getById(rosterUserDTO.getRosterId());
            if(rosterUserDTO.getWorkplaceId()==null || "".equals(rosterUserDTO.getWorkplaceId())){
                throw new CustomException("工作区域不能为空");
            }
            WorkPlaceFrequency workPlaceFrequency = workPlaceService.getById(rosterUserDTO.getWorkplaceId());
            AreaRosterUser areaRosterUser=AreaRosterUser.builder()
                    .userId(areaRosterUserDTO.getUserId())
                    .rosterId(rosterUserDTO.getRosterId())
                    .rosterName(appRoster.getRosterName())
                    .rosterTime(rosterUserDTO.getRosterTime())
                    .deptId(sysUser.getDeptId())
                    .deptName(deptName)
                    .nickName(sysUser.getNickName())
                    .phone(sysUser.getPhonenumber())
                    .workPlaceId(rosterUserDTO.getWorkplaceId())
                    .workPlaceName(workPlaceFrequency.getWorkPlaceName())
                    .build();
            b= baseMapper.insert(areaRosterUser);
        }
        return b;
    }

    @Override
    public PageInfo<AreaRosterUserVO> pageList(AreaRosterUserQueryDTO areaRosterUserQueryDTO) {
        QueryWrapper<AreaRosterUser> queryWrapper=new QueryWrapper<>();
        if(areaRosterUserQueryDTO.getNickName()!=null){
            queryWrapper.like("nick_name",areaRosterUserQueryDTO.getNickName());
        }
        if(areaRosterUserQueryDTO.getRosterId()!=null){
            queryWrapper.eq("roster_id",areaRosterUserQueryDTO.getRosterId());
        }
        if(areaRosterUserQueryDTO.getDeptId()!=null){
            queryWrapper.eq("dept_id",areaRosterUserQueryDTO.getDeptId());
        }
        if(areaRosterUserQueryDTO.getRosterTime()!=null){
            queryWrapper.eq("roster_time",areaRosterUserQueryDTO.getRosterTime());
        }
        if(areaRosterUserQueryDTO.getWorkPlaceId()!=null){
            queryWrapper.eq("work_place_id",areaRosterUserQueryDTO.getWorkPlaceId());
        }
        PageHelper.startPage(areaRosterUserQueryDTO.getPageNum(),areaRosterUserQueryDTO.getPageSize(),areaRosterUserQueryDTO.getOrderBy());
        List<AreaRosterUserVO> areaRosterUserVOS = baseMapper.selectListWrapper(queryWrapper);

        return new PageInfo<>(areaRosterUserVOS);
    }

    @Override
    public List<AreaRosterUserExcel> exportList(AreaRosterUserQueryDTO areaRosterUserQueryDTO) {
        QueryWrapper<AreaRosterUser> queryWrapper=new QueryWrapper<>();
        if(areaRosterUserQueryDTO.getNickName()!=null){
            queryWrapper.like("nick_name",areaRosterUserQueryDTO.getNickName());
        }
        if(areaRosterUserQueryDTO.getRosterId()!=null){
            queryWrapper.eq("roster_id",areaRosterUserQueryDTO.getRosterId());
        }
        if(areaRosterUserQueryDTO.getDeptId()!=null){
            queryWrapper.eq("dept_id",areaRosterUserQueryDTO.getDeptId());
        }
        if(areaRosterUserQueryDTO.getRosterTime()!=null){
            queryWrapper.eq("roster_time",areaRosterUserQueryDTO.getRosterTime());
        }
        if(areaRosterUserQueryDTO.getWorkPlaceId()!=null){
            queryWrapper.eq("work_place_id",areaRosterUserQueryDTO.getWorkPlaceId());
        }
//        PageHelper.startPage(areaRosterUserQueryDTO.getPageNum(),areaRosterUserQueryDTO.getPageSize(),areaRosterUserQueryDTO.getOrderBy());
        List<AreaRosterUserVO> areaRosterUserVOS = baseMapper.selectListWrapper(queryWrapper);
        List<AreaRosterUserExcel> list=new ArrayList<>();
        if(areaRosterUserVOS.size()>0){
            for (AreaRosterUserVO areaRosterUserVO : areaRosterUserVOS) {
                AreaRosterUserExcel areaRosterUserExcel=new AreaRosterUserExcel();
                areaRosterUserExcel.setNickName(areaRosterUserVO.getNickName());
                areaRosterUserExcel.setPhone(areaRosterUserVO.getPhone());
                areaRosterUserExcel.setDeptName(areaRosterUserVO.getDeptName());
                areaRosterUserExcel.setRosterName(areaRosterUserVO.getRosterName());
                areaRosterUserExcel.setRosterTime(areaRosterUserVO.getRosterTime());
                areaRosterUserExcel.setWorkPlaceName(areaRosterUserVO.getWorkPlaceName());
                list.add(areaRosterUserExcel);
            }
        }
        return list;
    }
}

