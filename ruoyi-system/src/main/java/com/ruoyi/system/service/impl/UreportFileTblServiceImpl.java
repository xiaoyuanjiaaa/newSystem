package com.ruoyi.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.UreportFileTblDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UreportFileTblMapper;
import com.ruoyi.system.entity.UreportFileTbl;
import com.ruoyi.system.service.IUreportFileTblService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-10
 */
@Service
public class UreportFileTblServiceImpl extends ServiceImpl<UreportFileTblMapper,UreportFileTbl> implements IUreportFileTblService
{

/*
    *//**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     *//*
    @Override
    public UreportFileTbl selectUreportFileTblById(Long id)
    {
        return baseMapper.selectUreportFileTblById(id);
    }

    *//**
     * 查询【请填写功能名称】列表
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 【请填写功能名称】
     *//*
    @Override
    public List<UreportFileTbl> selectUreportFileTblList(UreportFileTbl ureportFileTbl)
    {
        return baseMapper.selectUreportFileTblList(ureportFileTbl);
    }

    *//**
     * 新增【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     *//*
    @Override
    public int insertUreportFileTbl(UreportFileTbl ureportFileTbl)
    {
        ureportFileTbl.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertUreportFileTbl(ureportFileTbl);
    }

    *//**
     * 修改【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     *//*
    @Override
    public int updateUreportFileTbl(UreportFileTbl ureportFileTbl)
    {
        ureportFileTbl.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateUreportFileTbl(ureportFileTbl);
    }

    *//**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     *//*
    @Override
    public int deleteUreportFileTblByIds(Long[] ids)
    {
        return baseMapper.deleteUreportFileTblByIds(ids);
    }

    *//**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     *//*
    @Override
    public int deleteUreportFileTblById(Long id)
    {
        return baseMapper.deleteUreportFileTblById(id);
    }*/

    @Override
    public IPage<UreportFileTbl> pageReport(UreportFileTblDTO ureportFileTblDTO) {
        QueryWrapper<UreportFileTbl> queryWrapper = new QueryWrapper<>();

        if (ureportFileTblDTO.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("update_time_");
        }else {
            queryWrapper.orderByAsc("update_time_");
        }
        if (StringUtil.isNotEmpty(ureportFileTblDTO.getName())){
            queryWrapper.like("name_",ureportFileTblDTO.getName());
        }
        IPage<UreportFileTbl> page = new Page<>();
        page.setCurrent(ureportFileTblDTO.getPageNum());
        page.setSize(ureportFileTblDTO.getPageSize());
        IPage <UreportFileTbl> rsult = page(page,queryWrapper);
        return rsult;
    }
}
