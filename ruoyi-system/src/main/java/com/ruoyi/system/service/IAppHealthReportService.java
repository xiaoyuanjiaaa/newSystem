package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.*;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.vo.CountCompleteNumber;
import com.ruoyi.system.entity.vo.GroupCompleteNumber;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import com.ruoyi.system.vo.AppHealthReportVO;
import com.ruoyi.system.vo.PersonTemperatureVO;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IAppHealthReportService extends IService<AppHealthReport> {


    /**
     * @param reportDTO
     * @return
     */
    PageInfo<AppHealthReportQueryVO> pageAppHealthReport(AppHealthReportDTO reportDTO);

    PageInfo<SysUser> pageSysUser(AppHealthReportCountDTO pageDomain);

    List<SysUser> pageSysUserExport(AppHealthReportCountDTO pageDomain);

    Integer addBatch(AppHealthReportCountDTO pageDomain);

    /**
     * 添加每日填报信息
     * @param saveDTO
     * @return
     */
    String addAppHealthReport(AppHealthReportSaveDTO saveDTO);

    /**
     * 测试接口
     * @param countDTO
     * @return
     */
    CountCompleteNumber countDeptComplete(AppHealthReportCountDTO countDTO);

    PageInfo<GroupCompleteNumber> groupDeptComplete(AppHealthReportCountDTO countDTO);

    /**
     * 测试接口
     * @param countDTO
     * @return
     */
    public PageInfo<GroupCompleteNumber> groupDeptCompleteTwo(AppHealthReportCountDTO countDTO);

    public PageInfo<GroupCompleteNumber> groupDeptCompleteThree(AppHealthReportCountDTO countDTO);

    PersonTemperatureVO listPersonTemperature(Long personId);

    /**
     * 获取最新的健康填报信息
     * @param personId
     * @return
     */
    AppHealthReportVO getHealthReportByPersonId(Long personId);

    /**
     * 查询人员当天的每日健康填报
     * @param personId
     * @return
     */
    AppHealthReportVO getInfoByPersonId(Long personId);

    /**
     *
     * @param saveDTO
     * @return
     */
    String batchSaveInfo(AppHealthReportBatchSaveDTO saveDTO);

    AppHealthReport getInfoByPersonIdTwo(Long personId);

    /**
     * 测试接口
     * @param countDTO
     * @return
     */
    public CountCompleteNumber countDeptComplete2(AppHealthReportCountDTO countDTO);

    public CountCompleteNumber countDeptComplete3(AppHealthReportCountDTO countDTO);

    //判断是不是片区管理员
    boolean isDeptAdmin(SysUser sysUser);

    List<AppHealthReport> selectTodayList(Long personId);

    int updateByPersonId(AppHealthReport appHealthReport);

    /**
     * 大屏每日填报异常人员
     * @return
     */
    List<AppHealthReportViewDTO> getViewList();
}
