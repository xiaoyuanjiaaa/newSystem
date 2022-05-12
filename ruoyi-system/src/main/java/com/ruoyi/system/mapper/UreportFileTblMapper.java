package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.UreportFileTbl;
import org.apache.ibatis.annotations.Mapper;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-10
 */
@Mapper
public interface UreportFileTblMapper extends BaseMapper<UreportFileTbl>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public UreportFileTbl selectUreportFileTblById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<UreportFileTbl> selectUreportFileTblList(UreportFileTbl ureportFileTbl);

    /**
     * 新增【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     */
    public int insertUreportFileTbl(UreportFileTbl ureportFileTbl);

    /**
     * 修改【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     */
    public int updateUreportFileTbl(UreportFileTbl ureportFileTbl);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteUreportFileTblById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUreportFileTblByIds(Long[] ids);
}
