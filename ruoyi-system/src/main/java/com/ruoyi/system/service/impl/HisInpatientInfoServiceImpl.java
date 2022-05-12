package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.HospitalPersonnelDTO;
import com.ruoyi.system.entity.HisInpatientInfo;
import com.ruoyi.system.mapper.HisInpatientInfoMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.IHisInpatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HisInpatientInfoServiceImpl extends ServiceImpl<HisInpatientInfoMapper, HisInpatientInfo> implements IHisInpatientInfoService {
    @Autowired
    HisInpatientInfoMapper mapper;

    @Autowired
    SysUserMapper userMapper;

    @Override
    public List<HisInpatientInfo> getList() {
        QueryWrapper<HisInpatientInfo> queryWrapper = new QueryWrapper<>();
        return mapper.selectList(queryWrapper);
    }

    @Override
    public HospitalPersonnelDTO getListNum() {
        HospitalPersonnelDTO personnelDTO = new HospitalPersonnelDTO();

        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        List<SysUser> userList = userMapper.selectList(userQueryWrapper);

        personnelDTO.setMedicalStaff(userList.size());

        QueryWrapper<HisInpatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("leave_hospital","");
        List<HisInpatientInfo> inpatientInfoList = mapper.selectList(queryWrapper);
        personnelDTO.setInpatient(inpatientInfoList.size());

        return personnelDTO;
    }
}
