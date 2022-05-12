package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.AppAsset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【防疫资产】Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
@Mapper
public interface AppAssetMapper extends BaseMapper<AppAsset>
{
    /**
     * 查询【防疫资产】
     * 
     * @param id 【防疫资产】主键
     * @return 【防疫资产】
     */
    public AppAsset selectAppAssetById(Long id);

    /**
     * 查询【防疫资产】列表
     * 
     * @param appAsset 【防疫资产】
     * @return 【防疫资产】集合
     */
    public List<AppAsset> selectAppAssetList(AppAsset appAsset);

    /**
     * 新增【防疫资产】
     * 
     * @param appAsset 【防疫资产】
     * @return 结果
     */
    public int insertAppAsset(AppAsset appAsset);

    /**
     * 修改【防疫资产】
     * 
     * @param appAsset 【防疫资产】
     * @return 结果
     */
    public int updateAppAsset(AppAsset appAsset);

    /**
     * 删除【防疫资产】
     * 
     * @param id 【防疫资产】主键
     * @return 结果
     */
    public int deleteAppAssetById(Long id);

    /**
     * 批量删除【防疫资产】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppAssetByIds(Long[] ids);
}
