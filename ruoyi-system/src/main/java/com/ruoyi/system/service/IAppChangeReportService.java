package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.AppChangeReportDTO;
import com.ruoyi.system.entity.AppChangeReport;
import com.ruoyi.system.vo.AppChangeReportVO;

import java.util.List;

public interface IAppChangeReportService extends IService<AppChangeReport> {

//    void insertList();

    /**
     * 查询【请填写功能名称】列表
     *
     * @param appChangeReport 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppChangeReport> selectAppChangeReportList(AppChangeReport appChangeReport);

    PageInfo<AppChangeReportVO> pageList(AppChangeReportDTO appChangeReportDTO);

    /**
     * 新增【请填写功能名称】
     *
     * @param appChangeReport 【请填写功能名称】
     * @return 结果
     */
    public int insertAppChangeReport(AppChangeReport appChangeReport);



    //用户表增加新增人员
    public int addChange(SysUser user);

    public int updateChange(SysUser user);

    List<AppChangeReport> selectChangeList(AppChangeReport appChangeReport);
}
