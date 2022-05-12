package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppRouteReportDTO;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.AppRouteReportSaveDTO;
import com.ruoyi.system.dto.AppRouteReportUpdateDTO;
import com.ruoyi.system.entity.AppRouteReport;
import com.ruoyi.system.service.impl.AppRouteReportServiceImpl;
import com.ruoyi.system.vo.AppRouteReportLeftVO;
import com.ruoyi.system.vo.AppRouteReportVO;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IAppRouteReportService extends IService<AppRouteReport> {

    /**
     * 行程填报添加
     * @param saveDTO
     * @return
     */
    AppRouteReportVO addIAppRouteReport(AppRouteReportSaveDTO saveDTO);

    /**
     * 修改行程填报信息
     * @param updateDTO
     * @return
     */
    Integer updateAppRouteReport(AppRouteReportUpdateDTO updateDTO);

    /**
     * 分页查询行程填报信息
     * @param queryDTO
     * @return
     */
    IPage<AppRouteReportVO> getPageInfo(Page<AppRouteReportVO> page, AppRouteReportQueryDTO queryDTO);

    AppRouteReportLeftVO listCountPerson(AppRouteReportDTO reportDTO);
}
