package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppAsset;
import com.ruoyi.system.mapper.AppAssetMapper;
import com.ruoyi.system.service.IAppAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
@Service
public class AppAssetServiceImpl extends ServiceImpl<AppAssetMapper,AppAsset>  implements IAppAssetService
{
    @Autowired
    private AppAssetMapper appAssetMapper;

    /**
     * 查询【防疫物资】
     * 
     * @param id 【防疫物资】主键
     * @return 【防疫物资】
     */
    @Override
    public AppAsset selectAppAssetById(Long id)
    {
        return appAssetMapper.selectAppAssetById(id);
    }

    /**
     * 查询【防疫物资】列表
     * 
     * @param appAsset 【防疫物资】
     * @return 【防疫物资】
     */
    @Override
    public List<AppAsset> selectAppAssetList(AppAsset appAsset)
    {
        return appAssetMapper.selectAppAssetList(appAsset);
    }

    /**
     * 新增【防疫物资】
     * 
     * @param appAsset 【防疫物资】
     * @return 结果
     */
    @Override
    public int insertAppAsset(AppAsset appAsset)
    {
        appAsset.setId(IdWorker.getId());
        System.out.println(appAsset);
        return appAssetMapper.insertAppAsset(appAsset);
    }

    /**
     * 修改【防疫物资】
     * 
     * @param appAsset 【防疫物资】
     * @return 结果
     */
    @Override
    public int updateAppAsset(AppAsset appAsset)
    {
        return appAssetMapper.updateAppAsset(appAsset);
    }

    /**
     * 批量删除【防疫物资】
     * 
     * @param ids 需要删除的【防疫物资】主键
     * @return 结果
     */
    @Override
    public int deleteAppAssetByIds(Long[] ids)
    {
        return appAssetMapper.deleteAppAssetByIds(ids);
    }

    /**
     * 删除【防疫物资】信息
     * 
     * @param id 【防疫物资】主键
     * @return 结果
     */
    @Override
    public int deleteAppAssetById(Long id)
    {
        return appAssetMapper.deleteAppAssetById(id);
    }
}
