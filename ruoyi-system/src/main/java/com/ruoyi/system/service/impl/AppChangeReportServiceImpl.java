package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.AppChangeReportDTO;
import com.ruoyi.system.entity.AppChangeReport;
import com.ruoyi.system.entity.UreportFileTbl;
import com.ruoyi.system.mapper.AppChangeReportMapper;
import com.ruoyi.system.service.IAppChangeReportService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.vo.AppChangeReportVO;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppChangeReportServiceImpl extends ServiceImpl<AppChangeReportMapper, AppChangeReport> implements IAppChangeReportService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysDeptService sysDeptService;


//    @Override
//    public void insertList() {
//        List<SysUser> selectTodayAddList = userService.selectTodayAddList();//查询今日增加人员
//        List<SysUser> selectTodayMinList = userService.selectTodayMinList();//查询今日减少人员
//
//        if(selectTodayAddList.size()>0){
//            for (SysUser sysUser : selectTodayAddList) {
//                AppChangeReport appChangeReport=new AppChangeReport();
//                SysDept sysDept = sysDeptService.selectDeptById(sysUser.getDeptId());
//                appChangeReport.setId(IdWorker.getId());
//                appChangeReport.setChangeTime(sysUser.getCreateTime());//传新增时间
//                appChangeReport.setChangeType(1);
//                appChangeReport.setChangeName(sysUser.getNickName());
//                appChangeReport.setDeptName(sysDept.getDeptName());
//                appChangeReport.setChangePhone(sysUser.getPhonenumber());
//                appChangeReport.setJobNumber(sysUser.getJobNumber());
//                saveOrUpdate(appChangeReport);
//            }
//        }
//
//        if(selectTodayMinList.size()>0){
//            for (SysUser sysUser : selectTodayMinList) {
//                AppChangeReport appChangeReport=new AppChangeReport();
//                SysDept sysDept = sysDeptService.selectDeptById(sysUser.getDeptId());
//                appChangeReport.setId(IdWorker.getId());
//                appChangeReport.setChangeTime(sysUser.getUpdateTime());//传更新时间
//                appChangeReport.setChangeType(2);//减少
//                appChangeReport.setChangeName(sysUser.getNickName());
//                appChangeReport.setDeptName(sysDept.getDeptName());
//                appChangeReport.setChangePhone(sysUser.getPhonenumber());
//                appChangeReport.setJobNumber(sysUser.getJobNumber());
//                saveOrUpdate(appChangeReport);
//            }
//        }
//
//    }

    @Override
    public List<AppChangeReport> selectAppChangeReportList(AppChangeReport appChangeReport) {

        return baseMapper.selectAppChangeReportList(appChangeReport);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param appChangeReport 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppChangeReport(AppChangeReport appChangeReport)
    {
        appChangeReport.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertAppChangeReport(appChangeReport);
    }

    @Override
    public int addChange(SysUser user) {
        //查询今日启用
        List<AppChangeReport> appChangeReportList = baseMapper.selectTodayAddList(user.getNickName());
        if(appChangeReportList.size()>0){
            for (AppChangeReport appChangeReport : appChangeReportList) {
                baseMapper.deleteAppChangeReportById(appChangeReport.getId());
            }
        }
        int i=0;
        if(user!=null){ //增加
            AppChangeReport appChangeReport=new AppChangeReport();
            SysDept sysDept = sysDeptService.selectDeptById(user.getDeptId());
            appChangeReport.setChangeName(user.getNickName());
            appChangeReport.setDeptId(user.getDeptId() == null ? null : user.getDeptId());
            appChangeReport.setDeptName(sysDept == null ? null : sysDept.getDeptName());

            //时间往后推一天
            Date date= new   Date(); //取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果

            appChangeReport.setChangeTime(date);
            appChangeReport.setChangePhone(user.getPhonenumber());
            appChangeReport.setChangeType(1);
            appChangeReport.setIdNum(user.getIdNum());
            appChangeReport.setJobNumber(user.getJobNumber());
            i = baseMapper.insert(appChangeReport);
        }
        return i;
    }

    @Override
    public int updateChange(SysUser user) {
        //查询今日禁用
        List<AppChangeReport> appChangeReportList = baseMapper.selectTodayMinusList(user.getNickName());
        if(appChangeReportList.size()>0){
            for (AppChangeReport appChangeReport : appChangeReportList) {
               baseMapper.deleteAppChangeReportById(appChangeReport.getId());
            }
        }
        int i=0;
        if(user!=null){ //增加
            AppChangeReport appChangeReport=new AppChangeReport();
            SysDept sysDept = sysDeptService.selectDeptById(user.getDeptId());
            appChangeReport.setChangeName(user.getNickName());
            appChangeReport.setDeptId(user.getDeptId());
            appChangeReport.setDeptName(sysDept.getDeptName());
            appChangeReport.setChangeTime(new Date());
            appChangeReport.setChangePhone(user.getPhonenumber());
            appChangeReport.setChangeType(2);
            appChangeReport.setIdNum(user.getIdNum());
            appChangeReport.setJobNumber(user.getJobNumber());
            i = baseMapper.insert(appChangeReport);
        }
        return i;
    }

    @Override
    public List<AppChangeReport> selectChangeList(AppChangeReport appChangeReport) {
        return baseMapper.selectChangeList(appChangeReport);
    }

    @Override
    public PageInfo<AppChangeReportVO> pageList(AppChangeReportDTO appChangeReportDTO) {
        QueryWrapper<AppChangeReport> queryWrapper = new QueryWrapper<>();
        if(appChangeReportDTO.getDeptId()!=null && !"".equals(appChangeReportDTO.getDeptId())){
            queryWrapper.eq("dept_id",appChangeReportDTO.getDeptId());
        }
        if(appChangeReportDTO.getStartTime()!=null){
            queryWrapper.ge("change_time",appChangeReportDTO.getStartTime());
        }
        if(appChangeReportDTO.getEndTime()!=null){
            queryWrapper.le("change_time",appChangeReportDTO.getEndTime());
        }

        if(appChangeReportDTO.getStartTime()!=null && appChangeReportDTO.getEndTime()!=null){
            queryWrapper.between("change_time",appChangeReportDTO.getStartTime(),appChangeReportDTO.getEndTime());
        }
        if (appChangeReportDTO.getDeptId()!=null){
            queryWrapper.eq("dept_id",appChangeReportDTO.getDeptId());
        }
        if (appChangeReportDTO.getChangeType()!=null){
            queryWrapper.eq("change_type",appChangeReportDTO.getChangeType());
        }
        PageHelper.startPage(appChangeReportDTO.getPageNum(),appChangeReportDTO.getPageSize(),appChangeReportDTO.getOrderBy());
        List<AppChangeReportVO> appHealthReports = baseMapper.selectListWrapper(queryWrapper);
        return new PageInfo<>(appHealthReports);
    }

}
