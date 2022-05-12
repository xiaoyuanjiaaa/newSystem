package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.dto.AppRouteReportDTO;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.AppRouteReportUpdateDTO;
import com.ruoyi.system.entity.AppRouteReport;
import com.ruoyi.system.vo.AppRouteReportVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:04
 */
public interface AppRouteReportMapper extends BaseMapper<AppRouteReport> {

    /**
     * 分页查询行程填报数据
     * @param page
     * @param queryDTO
     * @return
     */
    IPage<AppRouteReportVO> getPageInfo(Page<AppRouteReportVO> page, @Param("queryDTO") AppRouteReportQueryDTO queryDTO);

    Integer updateInfo(@Param("updateDTO") AppRouteReportUpdateDTO updateDTO);

    @Select("select person_id,max(route_time) as route_time from app_route_report where status = 1 and route_time < #{dto.nextDay} group by person_id")
    List<AppRouteReport> listCountPerson(@Param("dto") AppRouteReportDTO reportDTO);
}
