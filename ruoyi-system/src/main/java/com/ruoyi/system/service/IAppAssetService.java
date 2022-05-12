package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.AppAsset;

import java.util.List;

/**
 * 【防疫物资管理】Service接口
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
public interface IAppAssetService extends IService<AppAsset>
{
    /**
     * 查询【防疫物资查询】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppAsset selectAppAssetById(Long id);

    /**
     * 查询【防疫物资】列表
     * 
     * @param appAsset 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppAsset> selectAppAssetList(AppAsset appAsset);

    /**
     * 新增【防疫物资】
     * 
     * @param appAsset 【请填写功能名称】
     * @return 结果
     */
    public int insertAppAsset(AppAsset appAsset);

    /**
     * 修改【防疫物资】
     * 
     * @param appAsset 【防疫物资】
     * @return 结果
     */
    public int updateAppAsset(AppAsset appAsset);

    /**
     * 批量删除【防疫物资】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteAppAssetByIds(Long[] ids);

    /**
     * 删除【防疫物资】信息
     * 
     * @param id 【防疫物资】主键
     * @return 结果
     */
    public int deleteAppAssetById(Long id);
}
