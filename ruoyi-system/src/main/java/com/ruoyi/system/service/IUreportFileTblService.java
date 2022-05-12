package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.UreportFileTblDTO;
import com.ruoyi.system.entity.UreportFileTbl;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2021-09-10
 */
public interface IUreportFileTblService extends IService<UreportFileTbl>
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
//    public UreportFileTbl selectUreportFileTblById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
//    public List<UreportFileTbl> selectUreportFileTblList(UreportFileTbl ureportFileTbl);

    /**
     * 新增【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     */
//    public int insertUreportFileTbl(UreportFileTbl ureportFileTbl);

    /**
     * 修改【请填写功能名称】
     * 
     * @param ureportFileTbl 【请填写功能名称】
     * @return 结果
     */
//    public int updateUreportFileTbl(UreportFileTbl ureportFileTbl);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
//    public int deleteUreportFileTblByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
//    public int deleteUreportFileTblById(Long id);

    //分页
    IPage<UreportFileTbl> pageReport(UreportFileTblDTO ureportFileTblDTO);
}
