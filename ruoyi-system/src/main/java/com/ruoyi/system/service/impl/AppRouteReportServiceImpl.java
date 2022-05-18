package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.AppRouteReportDTO;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.AppRouteReportSaveDTO;
import com.ruoyi.system.dto.AppRouteReportUpdateDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppRouteReport;
import com.ruoyi.system.mapper.AppRouteReportMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.IAppRouteReportService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppRouteReportLeftVO;
import com.ruoyi.system.vo.AppRouteReportVO;
import com.ruoyi.system.vo.AppRouteReportsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:11
 */
@Service
public class AppRouteReportServiceImpl extends ServiceImpl<AppRouteReportMapper, AppRouteReport> implements IAppRouteReportService {

   @Autowired
   private AppRouteReportMapper appRouteReportMapper;
   @Autowired
   private ISysUserService userService;
   @Autowired
   private SysUserMapper sysUserMapper;
   /**
    *
    * @param saveDTO
    * @return
    */
   @Override
   public AppRouteReportVO addIAppRouteReport(AppRouteReportSaveDTO saveDTO) {
      return Optional.ofNullable(saveDTO).map(dto -> {
         Long routeId = IdWorker.getId();
         AppRouteReport info = new AppRouteReport();
         BeanUtils.copyProperties(saveDTO, info);
         info.setCreateTime(new Date()).setRouteId(routeId).setRouteTime(DateUtils.parseDate(saveDTO.getRouteTime()));
         appRouteReportMapper.insert(info);
         AppRouteReportVO resultDto = new AppRouteReportVO();
         BeanUtils.copyProperties(info, resultDto);
         return resultDto;
      }).orElse(new AppRouteReportVO());
   }

   /**
    * 修改或者审核行程状态
    * @param updateDTO
    * @return
    */
   @Override
   public Integer updateAppRouteReport(AppRouteReportUpdateDTO updateDTO) {
      return appRouteReportMapper.updateInfo(updateDTO);
   }

   /**
    * 分页查询行程信息
    * @param queryDTO
    * @return
    */
   @Override
   public IPage<AppRouteReportVO> getPageInfo(Page<AppRouteReportVO> page, AppRouteReportQueryDTO queryDTO) {
      return appRouteReportMapper.getPageInfo(page, queryDTO);
   }

    @Override
    public AppRouteReportLeftVO listCountPerson(AppRouteReportDTO reportDTO) {
       List<AppRouteReport> routeReports = appRouteReportMapper.listCountPerson(reportDTO);
       QueryWrapper<AppRouteReport> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("1","2");
       if (CollectionUtils.isNotEmpty(routeReports)){
          for (AppRouteReport routeReport : routeReports) {
             QueryWrapper<AppHealthReport> queryWrapper1 = new QueryWrapper<>();
             queryWrapper.or().or(qw->qw.eq("route_time",routeReport.getRouteTime()).eq("person_id",routeReport.getPersonId()));
          }
       }
       //查询出所有人当前所在地点，没有的人都在二院
       List<AppRouteReport> reports = list(queryWrapper);
       Map<String,List<AppRouteReport>> map = reports.stream().collect(Collectors.groupingBy(AppRouteReport::getRouteAddr));
       List<AppRouteReportsVO> appRouteReportVOS = new ArrayList<>();
       for (String key:map.keySet()){
          AppRouteReportsVO appRouteReportsVO = new AppRouteReportsVO();
          appRouteReportsVO.setPlace(key);
          appRouteReportsVO.setUserCount(map.get(key).size());
          appRouteReportVOS.add(appRouteReportsVO);
       }
       AppRouteReportLeftVO leftVO = new AppRouteReportLeftVO();
       leftVO.setAppRouteReportsVOS(appRouteReportVOS);

       QueryWrapper<SysUser> queryWrapper1 = new QueryWrapper<>();
       queryWrapper1.lt("u.create_time",reportDTO.getNextDay());
       List<SysUser> users = sysUserMapper.selectByQueryWrapper(queryWrapper1);
       if (CollectionUtils.isNotEmpty(users)){
          leftVO.setUserCount(users.size());
       }
       leftVO.setUserCounted(reports.size());
       return leftVO;

    }
}
