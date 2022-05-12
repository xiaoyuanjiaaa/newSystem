package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppHealthReportViewDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:04
 */
public interface AppHealthReportMapper extends BaseMapper<AppHealthReport> {


    List<AppHealthReportQueryVO> selectListByWrapper(@Param("ew") QueryWrapper<AppHealthReport> queryWrapper);

    int deleteByPersonIds(List<Long> personIds);

    AppHealthReport getInfoByPersonIdTwo(Long personId);

    int selectForWeek(Long personId);

    int selectForMonth(Long personId);

    List<AppHealthReport> selectTodayList(Long personId);

    int updateByPersonId(AppHealthReport appHealthReport);

    /**
     * 大屏展示每日填报异常人员
     * @return
     */
    List<AppHealthReportViewDTO> getViewList();
}
