package com.ruoyi.web.controller.app;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.*;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.exception.GlobalExceptionHandler;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.AppHealthReportBatchSaveDTO;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.AppHealthReportDTO;
import com.ruoyi.system.dto.AppHealthReportSaveDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.WorkPlaceFrequency;
import com.ruoyi.system.entity.vo.CountCompleteNumber;
import com.ruoyi.system.entity.vo.GroupCompleteNumber;
import com.ruoyi.system.excel.AppHealthReportExcel;
import com.ruoyi.system.excel.NotReportUserExcel;
import com.ruoyi.system.excel.SysUserExcel;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AppHealthReportQueryVO;
import com.ruoyi.system.vo.AppHealthReportVO;
import com.ruoyi.system.vo.PersonTemperatureVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description
 * @Author dll
 * @Date 2021-08-14 02:44
 */
@Api(tags = {"????????????"})
@RestController
@RequestMapping("/appHealthReport")
public class AppHealthReportController {

    @Autowired
    private IAppHealthReportService appHealthReportService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private SystemService systemService;

    @Value("${spring.profiles.active}")
    private String env;


    @ApiOperation("????????????????????????")
    @GetMapping("/pageAppHealthReport")
    public ResultVO<PageInfo<AppHealthReportQueryVO>> pageAppHealthReport(AppHealthReportDTO reportDTO) {
        return new ResultVO<PageInfo<AppHealthReportQueryVO>>(SuccessEnums.QUERY_SUCCESS, appHealthReportService.pageAppHealthReport(reportDTO));
    }

    @ApiOperation("????????????????????????")
    @PostMapping("/saveInfo")
    public ResultVO<String> saveInfo(HttpServletRequest request, @RequestBody AppHealthReportSaveDTO saveDTO) {
        String result = appHealthReportService.addAppHealthReport(saveDTO);
        return new ResultVO<String>(SuccessEnums.SAVE_SUCCESS, result);
        //?????????????????????????????????
        /*String res = "";
//        if(env.equals("prod")){
        try{
            systemService.getSkmInfoByIdNum(request,saveDTO.getIdNum(), saveDTO.getReportName());
        }catch (Exception e){
            //????????????????????????????????????????????????????????????????????????try-catch
            res = e.getMessage();
        }
//        }
        if (StringUtils.isNotEmpty(res)) {
            return new ResultVO<String>(201,res, result);
        }else{
            return new ResultVO<String>(SuccessEnums.SAVE_SUCCESS, result);
        }*/
    }


    @ApiOperation("??????????????????????????????")
    @PostMapping("/batchSaveInfo")
    public ResultVO<String> batchSaveInfo(@RequestBody AppHealthReportBatchSaveDTO saveDTO) {
        String result = appHealthReportService.batchSaveInfo(saveDTO);
        return new ResultVO<String>(SuccessEnums.SAVE_SUCCESS, result);
    }

    @ApiOperation("????????????????????????????????????")
    @GetMapping("/pageSysUser")
    public ResultVO<PageInfo<SysUser>> pageSysUser(AppHealthReportCountDTO countDTO) {
        return new ResultVO<PageInfo<SysUser>>(SuccessEnums.QUERY_SUCCESS, appHealthReportService.pageSysUser(countDTO));
    }

    @ApiOperation("?????????????????????????????????????????????")
    @GetMapping("/pageSysUserExport")
    public AjaxResult pageSysUserExport(AppHealthReportCountDTO countDTO) {
        if (ObjectUtil.isEmpty(countDTO.getCurrentDay())) {
            return AjaxResult.error(FailEnums.CHECK_PARAM.getCode(), FailEnums.CHECK_PARAM.getMsg());
        }
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    countDTO.setDeptId(sysDept.getDeptId());
                } else {
                    countDTO.setDeptId(null);
                }
            }
        }
        List<SysUser> users = appHealthReportService.pageSysUserExport(countDTO);
        for (SysUser user : users) {
            if (user.getWorkPlace() != null) {
                WorkPlaceFrequency workPlaceFrequency = workPlaceService.
                        getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", user.getWorkPlace()));
                if (workPlaceFrequency != null) {
                    user.setWorkPlace(workPlaceFrequency.getWorkPlaceName());
                }
            }
        }
        List<NotReportUserExcel> list = new ArrayList<>();
        users.stream().forEach(t -> {
            NotReportUserExcel excel = new NotReportUserExcel();
            excel.setNickName(t.getNickName());
            excel.setPhonenumber(t.getPhonenumber());
            excel.setIdNum(t.getIdNum());
            excel.setJobNumber(t.getJobNumber());
            excel.setDept(t.getDept());
            excel.setWorkPlace(t.getWorkPlace());
            list.add(excel);
        });
        if (CollectionUtils.isEmpty(users)) {
            return AjaxResult.error("??????????????????");
        } else {
            ExcelUtil<NotReportUserExcel> util = new ExcelUtil<NotReportUserExcel>(NotReportUserExcel.class);
            String date = DateUtils.dateTime();
            return util.exportExcel(list, date.concat("?????????????????????"));
        }
    }
    @ApiOperation("?????????????????????????????????????????????")
    @GetMapping("/pageSysUserExportList")
    public AjaxResult pageSysUserExportList(AppHealthReportCountDTO countDTO) {
        if (ObjectUtil.isEmpty(countDTO.getCurrentDay())) {
            return AjaxResult.error(FailEnums.CHECK_PARAM.getCode(), FailEnums.CHECK_PARAM.getMsg());
        }
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    countDTO.setDeptId(sysDept.getDeptId());
                } else {
                    countDTO.setDeptId(null);
                }
            }
        }
        List<SysUser> users = appHealthReportService.pageSysUserExport(countDTO);
        for (SysUser user : users) {
            if (user.getWorkPlace() != null) {
                WorkPlaceFrequency workPlaceFrequency = workPlaceService.
                        getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", user.getWorkPlace()));
                if (workPlaceFrequency != null) {
                    user.setWorkPlace(workPlaceFrequency.getWorkPlaceName());
                }
            }
        }
        List<NotReportUserExcel> list = new ArrayList<>();
        users.stream().forEach(t -> {
            NotReportUserExcel excel = new NotReportUserExcel();
            excel.setNickName(t.getNickName());
            excel.setPhonenumber(t.getPhonenumber());
            excel.setIdNum(t.getIdNum());
            excel.setJobNumber(t.getJobNumber());
            excel.setDept(t.getDept());
            excel.setWorkPlace(t.getWorkPlace());
            list.add(excel);
        });
        if (CollectionUtils.isEmpty(users)) {
            return AjaxResult.error("??????????????????");
        }else {
            return AjaxResult.success("????????????", JSON.toJSONString(list));

        }
    }


    @ApiOperation("????????????????????????????????????")
    @GetMapping("/addBatch")
    public ResultVO<Integer> addBatch(AppHealthReportCountDTO countDTO) {
        return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS, appHealthReportService.addBatch(countDTO));
    }


    @ApiOperation("????????????-????????????????????????")
    @GetMapping("/countDeptComplete")
    public ResultVO<CountCompleteNumber> countDeptComplete(AppHealthReportCountDTO countDTO) {
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if (sysDept == null) {
                        return new ResultVO<>(FailEnums.ROLE_QUERY_FAIL, "?????????????????????????????????");
                    } else if (sysDept != null) {
                        countDTO.setDeptId(sysDept.getDeptId());
                    }
                } else {
                    countDTO.setDeptId(null);
                }

            }
        }
        CountCompleteNumber countCompleteNumber = appHealthReportService.countDeptComplete3(countDTO);
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, countCompleteNumber);
    }

    @ApiOperation("????????????")
    @GetMapping("/countDeptCompleteTwo")
    public ResultVO<CountCompleteNumber> countDeptCompleteTwo(AppHealthReportCountDTO countDTO) {
        CountCompleteNumber countCompleteNumber = appHealthReportService.countDeptComplete2(countDTO);
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, countCompleteNumber);
    }

    @ApiOperation("????????????-??????????????????????????????")
    @GetMapping("/groupDeptComplete")
    public ResultVO<PageInfo<GroupCompleteNumber>> groupDeptComplete(AppHealthReportCountDTO countDTO) {
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if (sysDept == null) {
                        return new ResultVO<>(FailEnums.ROLE_QUERY_FAIL, "?????????????????????????????????");
                    } else if (sysDept != null) {
                        countDTO.setDeptId(sysDept.getDeptId());
                    }
                } else {
                    countDTO.setDeptId(null);
                }
            }
        }
        PageInfo<GroupCompleteNumber> groupCompleteNumber = appHealthReportService.groupDeptCompleteTwo(countDTO);
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, groupCompleteNumber);
    }

    @ApiOperation("????????????-????????????")
    @GetMapping("/groupDeptCompleteTwo")
    public ResultVO<PageInfo<GroupCompleteNumber>> groupDeptCompleteTwo(AppHealthReportCountDTO countDTO) {
        PageInfo<GroupCompleteNumber> groupCompleteNumber = appHealthReportService.groupDeptCompleteTwo(countDTO);
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, groupCompleteNumber);
    }


    @ApiOperation("????????????-????????????????????????????????????")
    @GetMapping("/listPersonTemperature")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "personId", value = "?????????personId", dataType = "Long")
    )
    public ResultVO<PersonTemperatureVO> listPersonTemperature(Long personId) {
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS, appHealthReportService.listPersonTemperature(personId));
    }

    @ApiOperation("??????personId?????????????????????????????????")
    @GetMapping("/getHealthReportByPersonId")
    @ApiImplicitParams(@ApiImplicitParam(name = "personId", value = "?????????personId", dataType = "Long"))
    public ResultVO<AppHealthReportVO> getHealthReportByPersonId(Long personId) {
        return new ResultVO<AppHealthReportVO>(SuccessEnums.QUERY_SUCCESS, appHealthReportService.getHealthReportByPersonId(personId));
    }

    @Log(title = "???????????????????????????", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppHealthReportCountDTO countDTO) {
        if (ObjectUtil.isEmpty(countDTO.getCurrentDay())) {
            return AjaxResult.error(FailEnums.CHECK_PARAM.getCode(), FailEnums.CHECK_PARAM.getMsg());
        }
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    countDTO.setDeptId(sysDept.getDeptId());
                } else {
                    countDTO.setDeptId(null);
                }
            }
        }
        countDTO.setPageSize(1000);
        PageInfo<GroupCompleteNumber> groupCompleteNumberPageInfo = appHealthReportService.groupDeptCompleteTwo(countDTO);
        List<GroupCompleteNumber> completeNumbers = groupCompleteNumberPageInfo.getList();
        List<AppHealthReportExcel> list = new ArrayList<>();
        completeNumbers.stream().forEach(t -> {
            Integer notFilled = t.getCountUser() - t.getUserComplete();
            BigDecimal rate = t.getCountUser() == 0 ? new BigDecimal(0) : new BigDecimal(t.getUserComplete().toString()).divide(new BigDecimal(t.getCountUser().toString()), 4, BigDecimal.ROUND_HALF_UP);
            String reportingRate = rate.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString().concat("%");
            AppHealthReportExcel excel = new AppHealthReportExcel();
            excel.setDeptName(t.getDeptName());
            excel.setTotalNum(t.getCountUser());
            excel.setFilled(t.getUserComplete());
            excel.setNotFilled(notFilled);
            excel.setReportingRate(reportingRate);
            list.add(excel);
        });
        ExcelUtil<AppHealthReportExcel> util = new ExcelUtil<AppHealthReportExcel>(AppHealthReportExcel.class);
        return util.exportExcel(list, "??????????????????");
    }
    @Log(title = "???????????????????????????", businessType = BusinessType.EXPORT)
    @GetMapping("/exportList")
    public AjaxResult exportList(AppHealthReportCountDTO countDTO) {
        if (ObjectUtil.isEmpty(countDTO.getCurrentDay())) {
            return AjaxResult.error(FailEnums.CHECK_PARAM.getCode(), FailEnums.CHECK_PARAM.getMsg());
        }
        // ?????????????????????
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNotNull(currentUser)) {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if (deptAdmin) {
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
//            Long deptId = currentUser.getDeptId();
                    countDTO.setDeptId(sysDept.getDeptId());
                } else {
                    countDTO.setDeptId(null);
                }
            }
        }
        countDTO.setPageSize(1000);
        PageInfo<GroupCompleteNumber> groupCompleteNumberPageInfo = appHealthReportService.groupDeptCompleteTwo(countDTO);
        List<GroupCompleteNumber> completeNumbers = groupCompleteNumberPageInfo.getList();
        List<AppHealthReportExcel> list = new ArrayList<>();
        completeNumbers.stream().forEach(t -> {
            Integer notFilled = t.getCountUser() - t.getUserComplete();
            BigDecimal rate = t.getCountUser() == 0 ? new BigDecimal(0) : new BigDecimal(t.getUserComplete().toString()).divide(new BigDecimal(t.getCountUser().toString()), 4, BigDecimal.ROUND_HALF_UP);
            String reportingRate = rate.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP).toString().concat("%");
            AppHealthReportExcel excel = new AppHealthReportExcel();
            excel.setDeptName(t.getDeptName());
            excel.setTotalNum(t.getCountUser());
            excel.setFilled(t.getUserComplete());
            excel.setNotFilled(notFilled);
            excel.setReportingRate(reportingRate);
            list.add(excel);
        });
        return AjaxResult.success("????????????", JSON.toJSONString(list));
    }


    @ApiOperation("??????????????????")
    @GetMapping("/getWorkPlace")
    public ResultVO<List<Map<String, Object>>> getWorkPlace() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("work_place");
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        List<Map<String, Object>> mapList = new ArrayList<>();
        list.stream().forEach(t -> {
            WorkPlaceFrequency wf = workPlaceService.getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", t.getDictValue()));
            Map<String, Object> map = new HashMap<>(16);
            map.put("dictLabel", t.getDictLabel());
            map.put("dictValue", t.getDictValue());
            String remark = "??????????????????";
            if (wf != null && StringUtils.isNotEmpty(wf.getFrequencyName())) {
                remark = "???????????????????????????".concat(wf.getFrequencyName());
            }
            map.put("frequencyRemark", remark);
            mapList.add(map);
        });
        return new ResultVO<List<Map<String, Object>>>(SuccessEnums.QUERY_SUCCESS, mapList);
    }

    @ApiOperation("????????????????????????????????????????????????")
    @GetMapping("/vaccineSituation")
    public ResultVO<AppHealthReportQueryVO> vaccineSituation(String reportPhone) {
        AppHealthReport report = appHealthReportService.getOne(new QueryWrapper<AppHealthReport>().eq("report_phone",reportPhone).eq("vaccination",1).orderByDesc("report_time").last("limit 1"));
        AppHealthReportQueryVO vo = new AppHealthReportQueryVO();
        if(ObjectUtil.isNotEmpty(report)){
            BeanUtils.copyProperties(report,vo);
        }
        return new ResultVO<AppHealthReportQueryVO>(SuccessEnums.QUERY_SUCCESS, vo);
    }

}
