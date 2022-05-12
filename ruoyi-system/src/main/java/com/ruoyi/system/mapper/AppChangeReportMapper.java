package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ruoyi.system.entity.AppChangeReport;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.vo.AppChangeReportVO;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
@Mapper
public interface AppChangeReportMapper extends BaseMapper<AppChangeReport> {

    /**
     * 查询【请填写功能名称】列表
     *
     * @param appChangeReport 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppChangeReport> selectAppChangeReportList(AppChangeReport appChangeReport);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppChangeReportById(Long id);

    List<AppChangeReportVO> selectListWrapper(@Param("ew") QueryWrapper<AppChangeReport> queryWrapper);

    /**
     * 新增【请填写功能名称】
     *
     * @param appChangeReport 【请填写功能名称】
     * @return 结果
     */
    public int insertAppChangeReport(AppChangeReport appChangeReport);

    List<AppChangeReport> selectChangeList(AppChangeReport appChangeReport);

    List<AppChangeReport> selectTodayMinusList(String name);

    List<AppChangeReport> selectTodayAddList(String name);
}
