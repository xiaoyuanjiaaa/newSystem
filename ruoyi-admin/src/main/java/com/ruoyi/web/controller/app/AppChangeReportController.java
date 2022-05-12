package com.ruoyi.web.controller.app;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.AppChangeReportDTO;
import com.ruoyi.system.dto.AppHealthReportBatchSaveDTO;
import com.ruoyi.system.dto.AppHealthReportDTO;
import com.ruoyi.system.dto.UreportFileTblDTO;
import com.ruoyi.system.entity.AppChangeReport;
import com.ruoyi.system.entity.UreportFileTbl;
import com.ruoyi.system.mapper.AppChangeReportMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AppChangeReportVO;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = {"每日填报"})
@RestController
@RequestMapping("/appChangeReport")
public class AppChangeReportController extends BaseController {

    @Autowired
    private IAppChangeReportService appChangeReportService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;
    @Autowired
    private AppChangeReportMapper appChangeReportMapper;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IAppHealthReportService appHealthReportService;

//    @ApiOperation("批量添加每日健康填报")
//    @GetMapping("/add")
//    public void batchSaveInfo() {
//        appChangeReportService.insertList();
//    }

    /**
     * 查询【请填写功能名称】列表
     */
//    @PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/pagelist")
    public TableDataInfo list(AppChangeReport appChangeReport)
    {
        startPage();
        List<AppChangeReport> list = appChangeReportService.selectChangeList(appChangeReport);
        return getDataTable(list);
    }

    /**
     * 查询【请填写功能名称】列表
     */
//    @PreAuthorize("@ss.hasPermi('system:tbl:list')")
//    @ApiOperation("每日填报数据查询")
    @GetMapping("/list")
    public ResultVO<PageInfo<AppChangeReportVO>> pageAppChangeReport(AppChangeReportDTO appChangeReportDTO){

        Long deptId = appChangeReportDTO.getDeptId();
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if(sysDept==null){
                        return new ResultVO<>(FailEnums.ROLE_QUERY_FAIL,"部门管理员请先绑定片区");
                    }else if(sysDept!=null) {
                        appChangeReportDTO.setDeptId(sysDept.getDeptId());
                    }
                }else {
                    appChangeReportDTO.setDeptId(null);
                }
            }
        }
        //如果传了deptId，强制赋值
        if(deptId!=null){
            appChangeReportDTO.setDeptId(deptId);
        }
        return new ResultVO<PageInfo<AppChangeReportVO>>(SuccessEnums.QUERY_SUCCESS,appChangeReportService.pageList(appChangeReportDTO));
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "增减人员汇总", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('appChangeReport:export')")
    @GetMapping("/export")
    public AjaxResult export(AppChangeReport appChangeReport)
    {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    appChangeReport.setDeptId(sysDept.getDeptId());
                }else {
                    appChangeReport.setDeptId(null);
                }
            }
        }
        List<AppChangeReport> list2 = appChangeReportService.selectChangeList(appChangeReport);
        for (AppChangeReport changeReport : list2) {
            if(changeReport.getChangeType()==1){
                changeReport.setChangeTypeName("增加人员");
            }else if(changeReport.getChangeType()==2){
                changeReport.setChangeTypeName("减少人员");
            }
        }
        ExcelUtil<AppChangeReport> util = new ExcelUtil<AppChangeReport>(AppChangeReport.class);
        return util.exportExcel(list2, "变化人数明细");
    }
    @Log(title = "增减人员汇总", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('appChangeReport:export')")
    @GetMapping("/exportList")
    public AjaxResult exportList(AppChangeReport appChangeReport)
    {
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    appChangeReport.setDeptId(sysDept.getDeptId());
                }else {
                    appChangeReport.setDeptId(null);
                }
            }
        }
        List<AppChangeReport> list2 = appChangeReportService.selectChangeList(appChangeReport);
        for (AppChangeReport changeReport : list2) {
            if(changeReport.getChangeType()==1){
                changeReport.setChangeTypeName("增加人员");
            }else if(changeReport.getChangeType()==2){
                changeReport.setChangeTypeName("减少人员");
            }
        }
        ExcelUtil<AppChangeReport> util = new ExcelUtil<AppChangeReport>(AppChangeReport.class);
        return AjaxResult.success("变化人数明细", JSON.toJSONString(list2));
    }

    //判断当前时间是不是规定时间
    @GetMapping("/judgedTime")
    public Map<String, Object> judged(){

        String start=null;
        String end=null;
        int intStart=0;
        int intEnd=0;
        int intNow=0;
        SysDictData sysDictData=new SysDictData();
        sysDictData.setDictType("app_time");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        for (int i = 0; i < sysDictDataList.size(); i++) {
             start = sysDictDataList.get(0).getDictValue();//开始时间
             end = sysDictDataList.get(sysDictDataList.size()-1).getDictValue();//结束时间
        }
        //判断
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        String now = df.format(new Date());// new Date()为获取当前系统时间
        String strStart = start.substring(0,2); //05
        String strEnd = end.substring(0,2); //11
        String strNow = now.substring(0,2); //当前时间
        try {
             intStart = Integer.parseInt(strStart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            intEnd = Integer.parseInt(strEnd);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            intNow = Integer.parseInt(strNow);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<>();
        if(intStart<=intNow && intNow<=intEnd){
            map.put("code",200);
            map.put("flag",true);
            return map;
        }
        map.put("code",500);
        map.put("flag",false);
        return map;
    }
}
