package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruoyi.common.core.domain.entity.*;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.DutyStatusEnums;
import com.ruoyi.common.enums.RemindObjectEnums;
import com.ruoyi.common.enums.RemindTypeEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.*;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppHealthReportBatchSaveDTO;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.AppHealthReportDTO;
import com.ruoyi.system.dto.AppHealthReportSaveDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.vo.CountCompleteNumber;
import com.ruoyi.system.entity.vo.GroupCompleteNumber;
import com.ruoyi.system.entity.vo.HReport;
import com.ruoyi.system.mapper.AppHealthReportMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author dll
 * @Date 2021-08-14 02:10
 */
@Service
@Slf4j
@Transactional
public class AppHealthReportServiceImpl extends ServiceImpl<AppHealthReportMapper, AppHealthReport> implements IAppHealthReportService {
    @Value("${smsZh.sms.uid}")
    private String uId;

    @Value("${smsZh.sms.userPwd}")
    private String userPwd;

    @Value("${smsZh.sms.ext}")
    private String ext;

    private String message="";
    @Resource
    private AppHealthReportMapper appHealthReportMapper;

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Autowired
    private ISysDeptService deptService;

    @Resource
    private SysDeptMapper deptMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private AppPersonService personService;
    @Autowired
    private IAppPersonDestinationService personDestinationService;
    @Autowired
    private ITopicDataService topicDataService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IAppNotReportedService appNotReportedService;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private WorkPlaceService workPlaceService;

    @Autowired
    private IAppSmsConfigService smsConfigService;

    @Autowired
    private ISysConfigService sysConfigService;

    String str = "[{\\\"detailId\\\":\\\"6\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 08:54:01\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 13:27:38\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"A.???\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"B.???\\\\\\\",\\\\\\\"value\\\\\\\":1}]\\\",\\\"sort\\\":\\\"0\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":0},{\\\"detailId\\\":\\\"7\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"???28??????????????????????????????????????????????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 08:54:28\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 13:27:38\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"A.???\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"B.???\\\\\\\",\\\\\\\"value\\\\\\\":1}]\\\",\\\"sort\\\":\\\"1\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":0},{\\\"detailId\\\":\\\"8\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"???28???????????????????????????????????????????????????????????????????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 08:54:58\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 09:42:40\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"A.???\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"B.???\\\\\\\",\\\\\\\"value\\\\\\\":1}]\\\",\\\"sort\\\":\\\"2\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":1},{\\\"detailId\\\":\\\"9\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"?????????????????????????????????????????????????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 08:55:21\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 13:27:38\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"A.???\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"B.???\\\\\\\",\\\\\\\"value\\\\\\\":1}]\\\",\\\"sort\\\":\\\"3\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":1},{\\\"detailId\\\":\\\"10\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 09:04:09\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 13:27:38\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"A.1????????????????????????\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"B.2???????????????\\\\\\\",\\\\\\\"value\\\\\\\":1},{\\\\\\\"label\\\\\\\":\\\\\\\"C.3??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":2},{\\\\\\\"label\\\\\\\":\\\\\\\"D.4??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":3},{\\\\\\\"label\\\\\\\":\\\\\\\"E.5??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":4},{\\\\\\\"label\\\\\\\":\\\\\\\"F.6??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":5},{\\\\\\\"label\\\\\\\":\\\\\\\"G.7??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":6},{\\\\\\\"label\\\\\\\":\\\\\\\"H.8??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":7},{\\\\\\\"label\\\\\\\":\\\\\\\"I.9??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":8},{\\\\\\\"label\\\\\\\":\\\\\\\"J.10??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":9},{\\\\\\\"label\\\\\\\":\\\\\\\"K.11???????????????\\\\\\\",\\\\\\\"value\\\\\\\":10},{\\\\\\\"label\\\\\\\":\\\\\\\"L.12??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":11},{\\\\\\\"label\\\\\\\":\\\\\\\"M.13??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":12},{\\\\\\\"label\\\\\\\":\\\\\\\"N.14???????????????\\\\\\\",\\\\\\\"value\\\\\\\":13},{\\\\\\\"label\\\\\\\":\\\\\\\"O.15??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":14},{\\\\\\\"label\\\\\\\":\\\\\\\"P.16??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":15},{\\\\\\\"label\\\\\\\":\\\\\\\"Q.17??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":16},{\\\\\\\"label\\\\\\\":\\\\\\\"R.18??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":17},{\\\\\\\"label\\\\\\\":\\\\\\\"S.19??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":18},{\\\\\\\"label\\\\\\\":\\\\\\\"T.20??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":19},{\\\\\\\"label\\\\\\\":\\\\\\\"U.21??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":20},{\\\\\\\"label\\\\\\\":\\\\\\\"V.22??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":21},{\\\\\\\"label\\\\\\\":\\\\\\\"W.23??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":22},{\\\\\\\"label\\\\\\\":\\\\\\\"X.24??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":23},{\\\\\\\"label\\\\\\\":\\\\\\\"Y.25??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":24},{\\\\\\\"label\\\\\\\":\\\\\\\"Z.26??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":25},{\\\\\\\"label\\\\\\\":\\\\\\\"9.27???????????????\\\\\\\",\\\\\\\"value\\\\\\\":26},{\\\\\\\"label\\\\\\\":\\\\\\\"9.28??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":27},{\\\\\\\"label\\\\\\\":\\\\\\\"9.29??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":28},{\\\\\\\"label\\\\\\\":\\\\\\\"9.30??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":29},{\\\\\\\"label\\\\\\\":\\\\\\\"9.31??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":30},{\\\\\\\"label\\\\\\\":\\\\\\\"9.32??????????????????\\\\\\\",\\\\\\\"value\\\\\\\":31},{\\\\\\\"label\\\\\\\":\\\\\\\"9.33???????????????\\\\\\\",\\\\\\\"value\\\\\\\":32}]\\\",\\\"sort\\\":\\\"4\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":1},{\\\"detailId\\\":\\\"11\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"???????????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"radio\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 17:44:44\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 17:44:44\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"???\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"??????\\\\\\\",\\\\\\\"value\\\\\\\":1}]\\\",\\\"sort\\\":\\\"5\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":1},{\\\"detailId\\\":\\\"12\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"?????????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"input\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 17:44:56\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 17:44:56\\\",\\\"remark\\\":null,\\\"selectOptions\\\":null,\\\"sort\\\":\\\"6\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":\\\"1111\\\"},{\\\"detailId\\\":\\\"13\\\",\\\"templateId\\\":\\\"4\\\",\\\"columnName\\\":null,\\\"chineseName\\\":\\\"??????\\\",\\\"isCached\\\":null,\\\"columnType\\\":\\\"checkbox\\\",\\\"createBy\\\":\\\"\\\",\\\"createTime\\\":\\\"2021-09-08 17:45:34\\\",\\\"updateBy\\\":\\\"\\\",\\\"updateTime\\\":\\\"2021-09-08 17:45:34\\\",\\\"remark\\\":null,\\\"selectOptions\\\":\\\"[{\\\\\\\"label\\\\\\\":\\\\\\\"??????\\\\\\\",\\\\\\\"value\\\\\\\":0},{\\\\\\\"label\\\\\\\":\\\\\\\"?????????\\\\\\\",\\\\\\\"value\\\\\\\":1},{\\\\\\\"label\\\\\\\":\\\\\\\"?????????\\\\\\\",\\\\\\\"value\\\\\\\":2},{\\\\\\\"label\\\\\\\":\\\\\\\"?????????\\\\\\\",\\\\\\\"value\\\\\\\":3}]\\\",\\\"sort\\\":\\\"7\\\",\\\"isEnabled\\\":\\\"0\\\",\\\"value\\\":[1,2]}]\"";


    @Override
    public PageInfo<AppHealthReportQueryVO> pageAppHealthReport(AppHealthReportDTO reportDTO) {
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
//        if (StringUtil.isNotEmpty(reportDTO.getJobNum())) {
//            SysUser user = userService.selectUserByJonNumber(reportDTO.getJobNum());
//            if (user == null) {
//                queryWrapper.eq("1", "2");
//            } else {
//                queryWrapper.eq("app_health_report.person_id", user.getPersonId());
//            }
//        }
        if ( StringUtils.isNotEmpty (reportDTO.getJobNum ()) ){
            queryWrapper.like ("su.job_number",reportDTO.getJobNum ());
        }
        if (StringUtil.isNotEmpty(reportDTO.getName())) {
            queryWrapper.like("report_name", reportDTO.getName());
        }
        if (StringUtil.isNotEmpty(reportDTO.getMinTemperature())) {
            queryWrapper.ge("report_temperature", Integer.parseInt (reportDTO.getMinTemperature()));
        }
        if (StringUtil.isNotEmpty(reportDTO.getMaxTemperature())) {
            queryWrapper.le("report_temperature", Integer.parseInt (reportDTO.getMaxTemperature()));
        }
        if (reportDTO.getStartTime() != null) {
            queryWrapper.ge("report_time", reportDTO.getStartTime());
        }
        if (reportDTO.getEndTime() != null) {
            queryWrapper.lt("report_time", reportDTO.getEndTime());
        }
        if (StringUtil.isNotEmpty(reportDTO.getWorkPlace())) {
            // ????????????????????????????????????,?????????????????????????????????
            queryWrapper.eq("app_health_report.work_place", reportDTO.getWorkPlace());
        }
        if (reportDTO.getPersonId() != null) {
            queryWrapper.eq("app_health_report.person_id", reportDTO.getPersonId());
        }
        if (reportDTO.getDeptId() != null) {
            SysUser sysUser = new SysUser();
            sysUser.setDeptId(reportDTO.getDeptId());
            List<SysUser> sysUsers = userService.selectUserList(sysUser);
            if (CollectionUtils.isNotEmpty(sysUsers)) {
//                queryWrapper.eq("1", "2");
                queryWrapper.in("app_health_report.person_id", sysUsers.stream().map(SysUser::getPersonId).collect(Collectors.toList()));
            } else {
//                queryWrapper.in("app_health_report.person_id", sysUsers.stream().map(SysUser::getPersonId).collect(Collectors.toList()));
                return new PageInfo<>();
            }
        }
        if (reportDTO.getOnDuty() != null) {
            queryWrapper.eq("duty_status", reportDTO.getOnDuty());
        }
        PageHelper.startPage(reportDTO.getPageNum(), reportDTO.getPageSize(), reportDTO.getOrderBy());
        List<AppHealthReportQueryVO> appHealthReports = baseMapper.selectListByWrapper(queryWrapper);
        for (AppHealthReportQueryVO appHealthReport : appHealthReports) {
            if (appHealthReport.getWorkPlace() != null){
                SysDictData sysDictData = dictDataService.getByValue(appHealthReport.getWorkPlace());
                appHealthReport.setWorkPlace(sysDictData.getDictLabel());
            }
            if(appHealthReport.getDutyStatus()!=null) {
                if (DutyStatusEnums.ON_DUTY.getCode() == appHealthReport.getDutyStatus()) {
                    appHealthReport.setIsDuty(DutyStatusEnums.ON_DUTY.getMsg());
                } else if (DutyStatusEnums.OUT_DUTY.getCode() == appHealthReport.getDutyStatus()) {
                    appHealthReport.setIsDuty(DutyStatusEnums.OUT_DUTY.getMsg());
                }
            }
        }
        return new PageInfo<>(appHealthReports);
    }

    @Override
    public PageInfo<SysUser> pageSysUser(AppHealthReportCountDTO pageDomain) {
        QueryWrapper<AppNotReported> queryWrapper = new QueryWrapper<>();
        pageDomain.getCurrentDay();
        queryWrapper.ge("statistics_time", pageDomain.getCurrentDay());
        queryWrapper.lt("statistics_time", pageDomain.getNextDay());
        List<AppNotReported> appNotReporteds = appNotReportedService.list(queryWrapper);
        List<Long> personIds = appNotReporteds.stream().map(AppNotReported::getPersonId).collect(Collectors.toList());

        QueryWrapper<SysUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("u.del_flag", "0");
        queryWrapper1.eq("u.status", "0");
        if (!CollectionUtils.isEmpty(personIds)) {
            queryWrapper1.in("u.person_id", personIds);
        }
        if (pageDomain.getDeptId() != null) {
            queryWrapper1.eq("u.dept_id", pageDomain.getDeptId());
        }
        PageHelper.startPage(pageDomain.getPageNum(), pageDomain.getPageSize(), pageDomain.getOrderBy());
        List<SysUser> users = new ArrayList<>();
        if (!CollectionUtils.isEmpty(personIds)) {
            users = userMapper.selectByQueryWrapper(queryWrapper1);
            for (SysUser user : users) {
                if(user !=null && user.getDept() != null && StringUtil.isNotEmpty(user.getDept().getDeptName())){
                    user.setDeptName(user.getDept().getDeptName());
                }
            }
        }
        for (SysUser user : users) {
            if (user.getWorkPlace() != null && !user.getWorkPlace().equals("fever_clinic")){
                WorkPlaceFrequency workPlaceFrequency = workPlaceService.
                        getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", user.getWorkPlace()));
                if (workPlaceFrequency != null){
                    System.out.println("workPlaceFrequency.getFrequencyName() = " + workPlaceFrequency.getFrequencyName());
                    user.setFillInJudge(workPlaceFrequency.getFrequencyName());
                    System.out.println("user.getFillInJudge() = " + user.getFillInJudge());
                }
            }
        }
        return new PageInfo<SysUser>(users);
    }

    @Override
    public List<SysUser> pageSysUserExport(AppHealthReportCountDTO pageDomain) {
        QueryWrapper<AppNotReported> queryWrapper = new QueryWrapper<>();
        pageDomain.getCurrentDay();
        queryWrapper.ge("statistics_time", pageDomain.getCurrentDay());
        queryWrapper.lt("statistics_time", pageDomain.getNextDay());
        queryWrapper.ne("user_id", 1);
        List<AppNotReported> appNotReporteds = appNotReportedService.list(queryWrapper);
        List<Long> personIds = appNotReporteds.stream().map(AppNotReported::getPersonId).collect(Collectors.toList());

        QueryWrapper<SysUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("u.del_flag", "0");
        queryWrapper1.eq("u.status", "0");
        if (!CollectionUtils.isEmpty(personIds)) {
            queryWrapper1.in("u.person_id", personIds);
        }
        if (pageDomain.getDeptId() != null) {
            queryWrapper1.eq("u.dept_id", pageDomain.getDeptId());
        }
        List<SysUser> users = new ArrayList<>();
        if (!CollectionUtils.isEmpty(personIds)) {
            users = userMapper.selectByQueryWrapper(queryWrapper1);
        }
        return users;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param pageDomain
     * @return
     */
    @Override
    public Integer addBatch(AppHealthReportCountDTO pageDomain) {
        if (pageDomain.getDeptId() == null) {
            throw new CustomException("???????????????");
        }
        pageDomain.setPageSize(100000);
        List<SysUser> items = pageSysUser(pageDomain).getList();
        if (!CheckUtil.NullOrEmpty(items)) {
            items.stream().forEach(one -> {
                AppHealthReport info = new AppHealthReport();
                Long reportId = IdWorker.getId();
                info.setCreateTime(new Date()).setReportTime(new Date()).setReportId(reportId).setCreateBy("admin")
                        .setReportName(one.getUserName()).setReportPhone(one.getPhonenumber()).setPersonId(one.getPersonId())
                        .setSex(Integer.parseInt(one.getSex())).setReportJson(str);
                appHealthReportMapper.insert(info);
            });
        }
        return null;
    }

    /**
     * ????????????????????????
     *
     * @param saveDTO
     * @return
     */
    @Override
//    @Transactional
    public String addAppHealthReport(AppHealthReportSaveDTO saveDTO) {
        //???????????????????????????
        String firstStitchTime = saveDTO.getFirstStitchTime ();
        //???????????????????????????
        String twoStitchTime = saveDTO.getTwoStitchTime ();
        //???????????????????????????
        String threeStitchTime = saveDTO.getThreeStitchTime ();
        //????????????
        if ( StringUtils.isNotBlank (firstStitchTime) && StringUtils.isNotBlank (twoStitchTime) && StringUtils.isNotBlank (threeStitchTime) ){
            //???????????????,?????????????????????????????????,????????????????????????????????????????????????compare??????0
            int compare = twoStitchTime.compareTo (firstStitchTime);
            //???????????????,?????????????????????????????????,????????????????????????????????????????????????compare1??????0
            int compare1 = threeStitchTime.compareTo (twoStitchTime);
            if ( compare <= 0 || compare1 <= 0 ){
                //??????????????????
                throw  new CustomException("?????????????????????????????????????????????!!!");
            }
        }
        return Optional.ofNullable(saveDTO).map(dto -> {
                    if (saveDTO.getAppSource() != null && saveDTO.getAppSource() == 1) {
                        SysUser sysUser = userService.getOne(new QueryWrapper<SysUser>().eq("phonenumber", saveDTO.getReportPhone()));
                        if (sysUser != null) {
                            saveDTO.setPersonId(sysUser.getPersonId());
                        }
                    }

                    //???????????????????????????????????????user?????????????????????????????????
                    if (StringUtil.isEmpty(dto.getWorkPlace())) {
                        String phone = saveDTO.getReportPhone();
                        SysUser sysUser = userService.getOne(new QueryWrapper<SysUser>().eq("phonenumber", phone));
                        if (sysUser != null) dto.setWorkPlace(sysUser.getWorkPlace());
                    }
                    //?????????????????????????????????
                    if (StringUtil.isNotEmpty(dto.getWorkPlace())) {
                        WorkPlaceFrequency wf = workPlaceService.getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", dto.getWorkPlace()));
                        if (wf != null) saveDTO.setFrequency(wf.getFrequencyName());
                    }

                    //???????????????????????????????????????????????????
                    List<SysUser> users = userService.list(new QueryWrapper<SysUser>().eq("phonenumber", saveDTO.getReportPhone()));
                    if (ObjectUtil.isNotEmpty(users)) {
                        SysUser user = users.get(0);
                        //??????user???????????????or??????
                        if (user.getStatus().equals("1") || user.getDelFlag().equals("2")) {
                            throw new CustomException("?????????????????????????????????");
                        }
                        if (user.getDeptId() != null) {
                            if (user.getDeptId() != null) {
                                SysDept dept = deptService.selectDeptById(user.getDeptId());
                                if (dept != null) {
                                    saveDTO.setDeptName(dept.getDeptName());
                                }
                            }
                            saveDTO.setReportName(user.getNickName());
                            saveDTO.setIdNum(user.getIdNum());
                            saveDTO.setJobNumber(user.getJobNumber());
                            saveDTO.setIsPrivate(user.getIsPrivate());
                            saveDTO.setIsTemporary(user.getIsTemporary());
                            saveDTO.setPostLevel(user.getPostLevel());
                        }
                    }
                    AppHealthReportVO po = getInfoByPersonId(saveDTO.getPersonId());
                    AppHealthReport info = new AppHealthReport();
                     log.info("++++++++++++++++++++++++++++++++++" + dto.getReportJson());
                    //????????????  ?????????????????????????????????
                    if(ObjectUtil.isNotNull(dto.getReportJson())){
                    Gson gson = new Gson();
                    List<Option> list = gson.fromJson(dto.getReportJson(), new TypeToken<List<Option>>() {
                    }.getType());
                        log.info("*******************" + list);
                        for (Option option : list) {
                            if (option.getDetailId() == 21) {
                                log.info("=========" + option.getValue());
                                Object value = option.getValue();
                                if (value instanceof Double) {
                                    info.setDutyStatus(Integer.valueOf(((Double) value).intValue()));
                                } else if (value instanceof Integer) {
                                    info.setDutyStatus((Integer) value);
                                }

                            }
                        }
                        out:for (Option option : list) {
                            //????????? ????????????
                            Gson gsonSelection = new Gson();
                            String selections = option.getSelectOptions();
                            Object value = option.getValue();
                            log.info(";;;;;;;;;;;;;;;;;;" + selections);
                            if(ObjectUtil.isNotNull(selections)) {
                            List<Option.SelectOptions> selectOptionsList = gsonSelection.fromJson(selections, new TypeToken<List<Option.SelectOptions>>() {
                            }.getType());
                            for (Option.SelectOptions selectOptions : selectOptionsList) {
                                if(ObjectUtil.isNotNull(selectOptions.getExceptionStatus())){
                                    if(value instanceof Double){
                                        if (selectOptions.getValue() == Integer.valueOf(((Double) value).intValue()) && selectOptions.getExceptionStatus()) {
                                            this.sendException();
                                            break out;
                                        }
                                    }else if(value instanceof String){
                                        String[] values = value.toString().split(",");
                                        for(String str:values){
                                            if (selectOptions.getValue() == Integer.parseInt(str) && selectOptions.getExceptionStatus()) {
                                                this.sendException();
                                                break out;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                                }
                        }

                    BeanUtils.copyProperties(dto, info);
                    if (po != null && po.getReportId() != null && getTimeChange()) {
                        //???????????????????????????
                        info.setReportId(po.getReportId()).setUpdateTime(new Date()).setReportTime(new Date());
                        appHealthReportMapper.updateById(info);
                        SysUser sysUser = userMapper.selectByPersonId(saveDTO.getPersonId());
                        if (ObjectUtil.isNotEmpty(sysUser)) {
                            Long[] ids = new Long[]{sysUser.getUserId()};
                            appNotReportedService.deleteDatas(ids); // ?????????????????????
                        }
                    } else {
                        Long reportId = IdWorker.getId();
                        info.setCreateTime(new Date()).setReportTime(new Date()).setReportId(reportId);
                        int insert = appHealthReportMapper.insert(info);
                        if (insert == 1) {
                            SysUser sysUser = userMapper.selectByPersonId(saveDTO.getPersonId());
                            if (ObjectUtil.isNotEmpty(sysUser)) {
                                Long[] ids = new Long[]{sysUser.getUserId()};
                                appNotReportedService.deleteDatas(ids); // ?????????????????????
                            }
                        }
                    }
            topicDataService.resolve(saveDTO, info);
            //???????????????
            Integer colour = 0;
            if (saveDTO.getFlag()) {
                colour = 16768605;
            }
            //?????????????????????base64
            String qrcodeType = "2";
            String str = systemService.outQrCode(null,qrcodeType, saveDTO.getReportName(), saveDTO.getIdNum(), saveDTO.getReportPhone(), colour);
            return str;
        }).orElse(null);
    }

    private Boolean getTimeChange() {
        List<SysDictData> items = sysDictTypeService.selectDictDataByType("app_time");
        if (!CheckUtil.NullOrEmpty(items) && items.size() == 2) {
            List<SysDictData> dictDataItems = items.stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList());
            System.out.println("------" + dictDataItems);
            try {
                Integer nowHs = Integer.parseInt(DateUtils.getDateHm());
                Integer start = Integer.parseInt(dictDataItems.get(0).getDictValue().replace(":", ""));
                Integer end = Integer.parseInt(dictDataItems.get(1).getDictValue().replace(":", ""));
                if (nowHs >= start && nowHs <= end) {
                    return true;
                }
            } catch (Exception e) {
                throw new CustomException("????????????????????????????????????");
            }
        } else {
            throw new CustomException("???????????????????????????????????????");
        }
        return false;
    }

    @Override
    public CountCompleteNumber countDeptComplete2(AppHealthReportCountDTO countDTO) {
        if (countDTO.getCurrentDay() == null) {
            countDTO.setCurrentDay(LocalDate.now());
        }
        // ??????????????????????????????
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("report_time", countDTO.getCurrentDay());
        queryWrapper.lt("report_time", countDTO.getNextDay());

        //????????????????????????????????????
        QueryWrapper<AppNotReported> wrapper = new QueryWrapper<>();
        wrapper.ge("statistics_time", countDTO.getCurrentDay());
        wrapper.lt("statistics_time", countDTO.getNextDay());

        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser(); // ?????????????????????????????????
        SysDept dept = deptService.selectDeptById(user.getDeptId());

//        if (!"3".equals(user.getStatus()) && user != null && dept != null) {
//            queryWrapper.eq("dept_name",dept.getDeptName());
//            wrapper.eq("dept_id",user.getDeptId());
//        }

        // ????????????????????????
        List<AppHealthReport> healthReports = list(queryWrapper);
        int count = count(queryWrapper); // ?????????


        // ?????????????????????
        List<AppNotReported> notReporteds = appNotReportedService.list(wrapper);
        int count1 = appNotReportedService.count(wrapper); // ?????????
        int countUser = count1 + count; //????????????

        // ????????????????????????
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.ne("level", 1); // ????????????
        List<SysDept> list = deptService.list(sysDeptQueryWrapper);

        List<Long> deptId = notReporteds.stream().filter(appNotReported -> appNotReported.getDeptId() != null)
                .map(AppNotReported::getDeptId).collect(Collectors.toList());
        List<Long> deptIdTwo = deptId.stream().filter(aLong -> aLong != 100).distinct().collect(Collectors.toList()); // ?????????????????????id
//        for (Long aLong : deptIdTwo) {
//            if (aLong == 100){
//                deptIdTwo.remove(aLong);
//            }
//        }
        System.out.println("?????????????????????id = " + deptIdTwo);
        if (deptIdTwo.size() == 0) { // ??????????????????
            CountCompleteNumber countCompleteNumber = CountCompleteNumber.builder()
                    .userCount(countUser)
                    .userComplete(count)
                    .userUnComplete(0)
                    .deptUnComplete(0)
                    .deptCount(list.size())
                    .deptComplete(list.size())
                    .build();
            return countCompleteNumber;
        }

        sysDeptQueryWrapper.notIn("dept_id", deptIdTwo); // ????????????????????????
        List<SysDept> list1 = deptService.list(sysDeptQueryWrapper);

        CountCompleteNumber countCompleteNumber = CountCompleteNumber.builder()
                .userCount(countUser)
                .userComplete(count)
                .userUnComplete(count1)
                .deptUnComplete(deptIdTwo.size())
                .deptCount(list.size())
                .deptComplete(list1.size())
                .build();
        return countCompleteNumber;
    }

    @Override
    public CountCompleteNumber countDeptComplete3(AppHealthReportCountDTO countDTO) {
        if (countDTO.getCurrentDay() == null) {
            countDTO.setCurrentDay(LocalDate.now());
        }
        // ??????????????????????????????
        SysDept sysDept = deptService.selectDeptById(countDTO.getDeptId());
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("report_time", countDTO.getCurrentDay());
        queryWrapper.lt("report_time", countDTO.getNextDay());
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            queryWrapper.eq("dept_name",sysDept.getDeptName());
        }
        // ????????????????????????
//        List<AppHealthReport> healthReports = list(queryWrapper);
        int count = count(queryWrapper); // ?????????
        //??????????????????
        queryWrapper.eq("duty_status", DutyStatusEnums.ON_DUTY.getCode());
        int onDutyCount = count(queryWrapper);
        log.info("????????????onDudyCount==========================="+onDutyCount);

        //????????????????????????
        //????????????????????????????????????
        QueryWrapper<AppNotReported> wrapper = new QueryWrapper<>();
        wrapper.ge("statistics_time", countDTO.getCurrentDay());
        wrapper.lt("statistics_time", countDTO.getNextDay());
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            wrapper.eq("dept_id",countDTO.getDeptId());
        }
        wrapper.ne("user_id", 1);
        List<AppNotReported> notReporteds = appNotReportedService.list(wrapper);
        int count1 = appNotReportedService.count(wrapper); // ?????????
        // ?????????????????????
        int countUser = count1 + count; //????????????
        //?????????????????????
        int outDutyCount = countUser-onDutyCount;
        // ????????????????????????
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.ne("level", "1"); // ????????????
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            sysDeptQueryWrapper.eq("dept_id",countDTO.getDeptId());
        }
        List<SysDept> list = deptService.list(sysDeptQueryWrapper);

        List<Long> deptId = notReporteds.stream().filter(appNotReported -> appNotReported.getDeptId() != null)
                .map(AppNotReported::getDeptId).collect(Collectors.toList());
        List<Long> deptIdTwo = deptId.stream().filter(aLong -> aLong != 100).distinct().collect(Collectors.toList()); // ?????????????????????id
//        for (Long aLong : deptIdTwo) {
//            if (aLong == 100){
//                deptIdTwo.remove(aLong);
//            }
//        }
        System.out.println("?????????????????????id = " + deptIdTwo);
        if (deptIdTwo.size() == 0) { // ??????????????????
            CountCompleteNumber countCompleteNumber = CountCompleteNumber.builder()
                    .userCount(countUser)
                    .userComplete(count)
                    .userUnComplete(0)
                    .deptUnComplete(0)
                    .deptCount(list.size())
                    .deptComplete(list.size())
                    .onDutyCount(onDutyCount)
                    .outDutyCount(outDutyCount)
                    .build();
            return countCompleteNumber;
        }

        sysDeptQueryWrapper.notIn("dept_id", deptIdTwo); // ????????????????????????
        List<SysDept> list1 = deptService.list(sysDeptQueryWrapper);

        CountCompleteNumber countCompleteNumber = CountCompleteNumber.builder()
                .userCount(countUser)
                .userComplete(count)
                .userUnComplete(count1)
                .deptUnComplete(deptIdTwo.size())
                .deptCount(list.size())
                .deptComplete(list1.size())
                .onDutyCount(onDutyCount)
                .outDutyCount(outDutyCount)
                .build();
        return countCompleteNumber;

    }

    @Override
    public boolean isDeptAdmin(SysUser sysUser) {
        boolean flag=false;
        List<SysRole> sysRoleList = sysRoleMapper.selectRoleByUserId(sysUser.getUserId());
        if(sysRoleList.size()>0){
            for (SysRole sysRole : sysRoleList) {
                if("deptadmin".equals(sysRole.getRoleKey())){
                    flag=true;
                }
            }
        }
        return flag;
    }

    @Override
    public List<AppHealthReport> selectTodayList(Long personId) {
        return baseMapper.selectTodayList(personId);
    }


    @Override
    public int updateByPersonId(AppHealthReport appHealthReport) {
        int i = baseMapper.updateByPersonId(appHealthReport);
        return i;
    }

    @Override
    public List<AppHealthReportViewDTO> getViewList() {
        return appHealthReportMapper.getViewList();
    }


    @Override
    public CountCompleteNumber countDeptComplete(AppHealthReportCountDTO countDTO) {
        System.out.println(countDTO.getCurrentDay());
        System.out.println(countDTO.getNextDay());
        // ??????????????????????????????
        CountCompleteNumber countCompleteNumber = new CountCompleteNumber();
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("report_time", countDTO.getCurrentDay());
        queryWrapper.lt("report_time", countDTO.getNextDay());
        List<AppHealthReport> healthReports = list(queryWrapper);

        List<Long> personIds = healthReports.stream().map(AppHealthReport::getPersonId).collect(Collectors.toList());

        /*???????????????*/
        QueryWrapper<SysUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("del_flag", 0);
        queryWrapper1.eq("status", 0);
        queryWrapper.lt("create_time", countDTO.getNextDay());
        if (countDTO.getDeptId() != null) {
            queryWrapper1.eq("dept_id", countDTO.getDeptId());
        }
        Integer countUser = userService.count(queryWrapper1); // ????????????id???????????????
        List<Long> userPersonIds = userService.list(queryWrapper1).stream().map(SysUser::getPersonId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(personIds)) {
            queryWrapper1.notIn("person_id", personIds);
        }
//        if(countDTO.getDeptId() != null){
//            queryWrapper1.eq("dept_id",countDTO.getDeptId());
//        }
        Integer userUnComplete = userService.count(queryWrapper1); // ????????????id??????????????????
        countCompleteNumber.setUserCount(countUser);
        countCompleteNumber.setUserUnComplete(userUnComplete);
        if (!countDTO.getCurrentDay().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
            countCompleteNumber.setUserComplete(countUser - userUnComplete);
        }
        queryWrapper1.select("user_id", "user_name", "nick_name", "dept_id", "phonenumber", "job_number");
        List<SysUser> sysUsers = userService.list(queryWrapper1);


        /*????????????*/
        QueryWrapper<SysDept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("del_flag", 0);
        deptQueryWrapper.eq("status", 0);
        if (countDTO.getDeptId() != null) {
            deptQueryWrapper.eq("dept_id", countDTO.getDeptId());
        }
        List<SysDept> sysDepts = deptMapper.listDepts(deptQueryWrapper);
        Integer deptComplete = 0;
        for (SysDept sysDept : sysDepts) {
            QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("u.dept_id", sysDept.getDeptId());
            userQueryWrapper.lt("u.create_time", countDTO.getNextDay());
            userQueryWrapper.eq("u.status", 0);
            userQueryWrapper.eq("u.del_flag", 0);
            List<SysUser> deptUsers = userMapper.selectByQueryWrapper(userQueryWrapper);
            if (CollectionUtils.isNotEmpty(deptUsers)) {
                List<Long> personIds2 = deptUsers.stream().map(SysUser::getPersonId).collect(Collectors.toList());
                if (personIds.containsAll(personIds2)) {
                    deptComplete++;
                }
            }
        }
        Integer deptCount = sysDepts.size();
        countCompleteNumber.setDeptCount(deptCount);
        countCompleteNumber.setDeptComplete(deptComplete);
        countCompleteNumber.setDeptUnComplete(deptCount - deptComplete);

        return countCompleteNumber;
    }

    @Override
    public PageInfo<GroupCompleteNumber> groupDeptCompleteTwo(AppHealthReportCountDTO countDTO) {
        if (countDTO.getCurrentDay() == null) {
            countDTO.setCurrentDay(LocalDate.now());
        }
        List<GroupCompleteNumber> completeNumbers = new ArrayList<>();
        // ???????????????????????????????????????
        List<HReport> hReports = deptService.getYesData(countDTO);
        System.out.println(hReports);

        SysDept sysDept = deptService.selectDeptById(countDTO.getDeptId());
        // ?????????????????????
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("report_time", countDTO.getCurrentDay());
        queryWrapper.lt("report_time", countDTO.getNextDay());
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            queryWrapper.eq("dept_name",sysDept.getDeptName());
        }
        queryWrapper.select(AppHealthReport.class, info -> !info.getColumn().equals("vaccination_url"));
        List<AppHealthReport> healthReports = list(queryWrapper);

        // ??????????????????
        QueryWrapper<AppNotReported> wrapper = new QueryWrapper<>();
        wrapper.ge("statistics_time", countDTO.getCurrentDay());
        wrapper.lt("statistics_time", countDTO.getNextDay());
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            wrapper.eq("dept_id",countDTO.getDeptId());
        }
        List<AppNotReported> notReporteds = appNotReportedService.list(wrapper);

        // ????????????????????????
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.ne("level", "1"); // ????????????
        if(countDTO.getDeptId()!=null && !"".equals(countDTO.getDeptId())){
            sysDeptQueryWrapper.eq("dept_id",countDTO.getDeptId());
        }
        List<SysDept> list = deptService.list(sysDeptQueryWrapper);
        List<SysDept> list1 = deptService.selectNoLevel(); // ????????????????????????
        List<HReport> hReportList = new ArrayList<>(); // ????????????????????????????????????

        for (SysDept dept : list) {
            HReport hReport = HReport.builder()
                    .deptName(dept.getDeptName())
                    .deptId(dept.getDeptId())
                    .build();
            hReportList.add(hReport);
        }


//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        SysUser user = loginUser.getUser(); // ?????????????????????????????????
//
//        if (!"3".equals(user.getStatus())) {
//            for (SysDept dept : list) {
//                if (dept.getDeptId().equals(user.getDeptId())) {
//                    HReport hReport = HReport.builder()
//                            .deptName(dept.getDeptName())
//                            .deptId(dept.getDeptId())
//                            .build();
//                    hReportList.add(hReport);
//                    break;
//                }
//            }
//        } else {
//            for (SysDept dept : list) {
//                HReport hReport = HReport.builder()
//                        .deptName(dept.getDeptName())
//                        .deptId(dept.getDeptId())
//                        .build();
//                hReportList.add(hReport);
//            }
//        }



        // ????????????????????????
        for (HReport hReport : hReportList) {
            int yes = 0;
            for (AppHealthReport healthReport : healthReports) {
                if (hReport.getDeptName().equals(healthReport.getDeptName())) {
                    yes++;
                }
            }
            hReport.setHNumber(yes);
        }
        // ????????????????????????
        for (HReport hReport : hReportList) {
            int no = 0;
            for (AppNotReported notReported : notReporteds) {
                if (hReport.getDeptId().equals(notReported.getDeptId())) {
                    no++;
                }
            }
            hReport.setNNumber(no);
        }
        // ????????????
        for (HReport hReport : hReportList) {
            GroupCompleteNumber completeNumber = new GroupCompleteNumber().builder()
                    .deptName(hReport.getDeptName())
                    .deptId(hReport.getDeptId())
                    .userComplete(hReport.getHNumber() == null ? 0 : hReport.getHNumber())
                    .countUser(hReport.getNNumber() + (hReport.getHNumber() == null ? 0 : hReport.getHNumber()))
                    .build();
            completeNumbers.add(completeNumber);
        }
        return new PageInfo<GroupCompleteNumber>(completeNumbers);


//        // ??????????????????????????????
//        if (CollectionUtils.isEmpty(hReports)){
//            // ?????????
//            QueryWrapper<AppNotReported> noWrapper = new QueryWrapper<>();
//            noWrapper.ge("statistics_time", countDTO.getCurrentDay());
//            noWrapper.lt("statistics_time", countDTO.getNextDay());
//            List<AppNotReported> list1 = appNotReportedService.list(noWrapper); // ???????????????????????????
//
//            for (SysDept dept : list) {
//                HReport hReport = HReport.builder()
//                        .deptName(dept.getDeptName())
//                        .deptId(dept.getDeptId())
//                        .build();
//                int c = 0;
//                for (AppNotReported appNotReported : list1) { // ????????????????????????????????????????????????
//                    if (dept.getDeptId().equals(appNotReported.getDeptId())){ // ?????????????????????
//                        c++;
//                    }
//                }
//                hReport.setNNumber(c);
//                hReports.add(hReport);
//            }
//            for (HReport hReport : hReports) { // ???????????????????????????????????????
//                GroupCompleteNumber completeNumber = new GroupCompleteNumber().builder()
//                        .deptName(hReport.getDeptName())
//                        .deptId(hReport.getDeptId())
//                        .userComplete(0)
//                        .countUser(hReport.getHNumber())
//                        .countUser(hReport.getNNumber() + 0)
//                        .build();
//                completeNumbers.add(completeNumber);
//            }
//            return new PageInfo<GroupCompleteNumber>(completeNumbers);
//        }
//        // ?????????????????????????????????
//        QueryWrapper<AppNotReported> wrapper = new QueryWrapper<>();
//        wrapper.ge("statistics_time", countDTO.getCurrentDay());
//        wrapper.lt("statistics_time", countDTO.getNextDay());
//        List<AppNotReported> notReporteds = appNotReportedService.list(wrapper); // ??????????????????
//
//        for (AppNotReported notReported : notReporteds) {
//            SysDept dept = deptService.selectDeptById(notReported.getDeptId());
//            notReported.setDeptName(dept.getDeptName());
//        }
//        System.out.println(notReporteds);
//
//        List<HReport> hReportList = new ArrayList<>();
//        for (SysDept dept : list) {
//            HReport hReport = HReport.builder()
//                    .deptName(dept.getDeptName())
//                    .deptId(dept.getDeptId())
//                    .build();
//            hReportList.add(hReport);
//        }
//        for (HReport hReport : hReportList) {
//            for (HReport report : hReports) {
//                if (hReport.getDeptName().equals(report.getDeptName())){
//                    BeanUtils.copyProperties(report,hReport);
//                }
//                continue;
//            }
//        }
//        for (HReport hReport : hReportList) {
//            int i = 0;
//            for (AppNotReported notReported : notReporteds) {
//                if (hReport.getDeptName().equals(notReported.getDeptName())){
//                    i++;
//                }
//            }
//            hReport.setNNumber(i);
//        }
//        for (HReport hReport : hReportList) {
//            GroupCompleteNumber completeNumber = new GroupCompleteNumber().builder()
//                    .deptName(hReport.getDeptName())
//                    .deptId(hReport.getDeptId())
//                    .userComplete(hReport.getHNumber() == null ? 0 : hReport.getHNumber())
//                    .countUser(hReport.getHNumber())
//                    .countUser(hReport.getNNumber() + (hReport.getHNumber() == null ? 0 : hReport.getHNumber()))
//                    .build();
//            completeNumbers.add(completeNumber);
//        }
//        return new PageInfo<GroupCompleteNumber>(completeNumbers);
    }

    @Override
    public PageInfo<GroupCompleteNumber> groupDeptCompleteThree(AppHealthReportCountDTO countDTO) {

        return null;
    }

    @Override
    public PageInfo<GroupCompleteNumber> groupDeptComplete(AppHealthReportCountDTO countDTO) {
        PageHelper.startPage(countDTO.getPageNum(), countDTO.getPageSize());
        List<GroupCompleteNumber> completeNumbers = deptService.groupDeptComplete(countDTO);
        return new PageInfo<GroupCompleteNumber>(completeNumbers);
    }

    @Override
    public PersonTemperatureVO listPersonTemperature(Long personId) {
        AppPerson appPerson = personService.getById(personId);
//        if (appPerson == null) {
//            throw new CustomException("??????????????????????????????");
//        }
        List<SysUser> sysUsers1 = userService.selectUserByPersonId(personId);
        SysUser sysUser1 = sysUsers1.get(0);
        PersonTemperatureVO temperatureVO = new PersonTemperatureVO();
//        temperatureVO.setName(appPerson.getPersonName());
        temperatureVO.setName(sysUser1.getNickName());
        //??????
        List<AppHealthReportQueryVO> appHealthReports = listTodayReport(personId);
        if (CollectionUtils.isNotEmpty(appHealthReports)) {
            temperatureVO.setTemperature(appHealthReports.get(0).getReportTemperature());
        }
        //?????????????????????
        List<AppPersonDestinationVO> personDestinationVOS = personDestinationService.listPersonDestinationVO(personId);
        if (CollectionUtils.isNotEmpty(personDestinationVOS)) {
            temperatureVO.setDestinationName(personDestinationVOS.get(0).getDestinationName());
        }
        //????????????
        List<SysUser> sysUsers = userService.selectUserByPersonId(personId);
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            SysUser sysUser = sysUsers.get(0);
            WorkPlaceFrequency workPlaceFrequency = workPlaceService.getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", sysUser.getWorkPlace()));
            if (workPlaceFrequency != null){
                temperatureVO.setDestinationName(workPlaceFrequency.getWorkPlaceName());
            }
            temperatureVO.setJobNumber(sysUser.getJobNumber());
            if (sysUser.getDept() != null) {
                temperatureVO.setDeptName(sysUser.getDept().getDeptName());
            }
            temperatureVO.setDeptId(sysUser.getDeptId());
        }
        //??????14???????????????
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_health_report.person_id", personId);
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDate ftyDays = now.minusDays(13);
        queryWrapper.lt("report_time", tomorrow);
        queryWrapper.ge("report_time", ftyDays);
        queryWrapper.select("CONVERT ( report_time, date ) as report_time", "max(report_temperature) as report_temperature");
        queryWrapper.groupBy("CONVERT ( report_time, date )");
        List<AppHealthReport> appHealthReports1 = list(queryWrapper);
        List<PersonTemperature> personTemperatures = new ArrayList<>();
        for (AppHealthReport appHealthReport : appHealthReports1) {
            PersonTemperature personTemperature = new PersonTemperature();
            personTemperature.setDate(appHealthReport.getReportTime());
            personTemperature.setTemperature(appHealthReport.getReportTemperature());
            personTemperatures.add(personTemperature);
        }
        personTemperatures = personTemperatures.stream().sorted(Comparator.comparing(PersonTemperature::getDate)).collect(Collectors.toList());
        temperatureVO.setTemperatures(personTemperatures);
        return temperatureVO;
    }

    private List<AppHealthReportQueryVO> listTodayReport(Long personId) {
        QueryWrapper<AppHealthReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_health_report.person_id", personId);
        LocalDate now = LocalDate.now();
        queryWrapper.ge("app_health_report.create_time", now);
        queryWrapper.lt("app_health_report.create_time", now.plusDays(1));
        List<AppHealthReportQueryVO> appHealthReports = appHealthReportMapper.selectListByWrapper(queryWrapper);
        return appHealthReports;
    }

    /**
     * ??????personid???????????????????????????
     *
     * @param personId
     * @return
     */
    @Override
    public AppHealthReportVO getHealthReportByPersonId(Long personId) {
        List<AppHealthReport> items = appHealthReportMapper.selectList(
                Wrappers.<AppHealthReport>query().lambda()
                        .eq(AppHealthReport::getPersonId, personId).orderByDesc(AppHealthReport::getReportId));
        AppHealthReportVO result = new AppHealthReportVO();
        if (!CheckUtil.NullOrEmpty(items)) {
            BeanUtils.copyProperties(items.get(0), result);
        }
        String remark = "??????????????????";
        if(StringUtils.isNotEmpty(result.getFrequency())){
            remark = "???????????????????????????".concat(result.getFrequency());
        }
        result.setFrequencyRemark(remark);
        //?????????????????????????????????????????????,???????????????????????????????????????????????????
        AppHealthReport report = this.getOne(new QueryWrapper<AppHealthReport>().eq("person_id",personId).isNotNull("vaccination").orderByDesc("report_time").last("limit 1"));
        if(report != null){
            result.setVaccination(report.getVaccination());
            result.setNotVacRemark(report.getNotVacRemark());
            result.setFirstStitchTime(report.getFirstStitchTime());
            result.setTwoStitchTime(report.getTwoStitchTime());
            result.setThreeStitchTime(report.getThreeStitchTime());
            result.setVaccinationUrl(report.getVaccinationUrl());
        }
        return result;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param personId
     * @return
     */
    @Override
    public AppHealthReportVO getInfoByPersonId(Long personId) {
        List<AppHealthReport> items = appHealthReportMapper.selectList(
                Wrappers.<AppHealthReport>query().lambda()
                        .eq(AppHealthReport::getPersonId, personId)
                        .between(AppHealthReport::getReportTime, DateUtils.getStartTime(), DateUtils.getEndTime())
                        .orderByDesc(AppHealthReport::getReportId));
        AppHealthReportVO result = new AppHealthReportVO();
        if (!CheckUtil.NullOrEmpty(items)) {
            BeanUtils.copyProperties(items.get(0), result);
        }
        return result;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param saveDTO
     * @return
     */
    @Override
    @Transactional
    public String batchSaveInfo(AppHealthReportBatchSaveDTO saveDTO) {
        if (!CheckUtil.NullOrEmpty(saveDTO.getPersonId())) {
            List<Long> userIds = new ArrayList<>();
            Long[] userIdsTwo = new Long[saveDTO.getPersonId().size()];
            List<Long> personId = saveDTO.getPersonId();
            for (Long one : personId) {
                AppHealthReportVO po = getInfoByPersonId(one);
                if(po != null && po.getReportId() != null){
                    continue;
                }
//                AppPerson po = personService.getAppPerson(one);
                SysDept dept = new SysDept();
                SysUser user = new SysUser();
                List<SysUser> users = userService.list(new QueryWrapper<SysUser>().eq("person_id", one));
                if (ObjectUtil.isNotEmpty(users)) {
                    user = users.get(0);
                    userIds.add(user.getUserId());
                    if (user.getDeptId() != null) {
                        dept = deptService.selectDeptById(user.getDeptId());
                    }
                    if (user != null) {
                        //??????user???????????????or??????
                        if (!user.getStatus().equals("1") && !user.getDelFlag().equals("2")) {
                            AppHealthReport info = new AppHealthReport();
                            Long reportId = IdWorker.getId();
                            info.setCreateTime(new Date()).setReportTime(new Date()).setReportId(reportId).setCreateBy("admin")
                                    .setReportName(user.getUserName()).setReportPhone(user.getPhonenumber()).setPersonId(one)
                                    .setReportJson(saveDTO.getReportContent())
                                    .setReportName(user.getNickName())
                                    .setIdNum(user.getIdNum())
                                    .setJobNumber(user.getJobNumber())
                                    .setIsPrivate(user.getIsPrivate())
                                    .setIsTemporary(user.getIsTemporary())
                                    .setPostLevel(user.getPostLevel())
                                    .setRemark(saveDTO.getRemark())
                                    .setAppSource(saveDTO.getAppSource())
                                    .setDeptName(dept.getDeptName());
                            if (user.getSex() != null) {
                                info.setSex(Integer.parseInt(user.getSex()));
                            }
                            //?????????????????????????????????
                            if(StringUtil.isNotEmpty(user.getWorkPlace())){
                                WorkPlaceFrequency wf = workPlaceService.getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place",user.getWorkPlace()));
                                if(wf!=null)info.setFrequency(wf.getFrequencyName());
                            }
                            //??????????????????
                            AppHealthReport report = this.getOne(new QueryWrapper<AppHealthReport>().eq("person_id",one).isNotNull("vaccination").orderByDesc("report_time").last("limit 1"));
                            if(report != null){
                                info.setVaccination(report.getVaccination());
                                info.setNotVacRemark(report.getNotVacRemark());
                                info.setFirstStitchTime(report.getFirstStitchTime());
                                info.setTwoStitchTime(report.getTwoStitchTime());
                                info.setThreeStitchTime(report.getThreeStitchTime());
                                info.setFirstStitchRemark(report.getFirstStitchRemark());
                                info.setTwoStitchRemark(report.getTwoStitchRemark());
                                info.setThreeStitchRemark(report.getThreeStitchRemark());
                                info.setVaccinationUrl(report.getVaccinationUrl());
                            }

                            int insert = appHealthReportMapper.insert(info);
                            //?????????????????????????????????
                            topicDataService.jsonTransToDB(info);
                            if (insert == 1){
                                userIds.toArray(userIdsTwo);
                                appNotReportedService.deleteDatas(userIdsTwo);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public AppHealthReport getInfoByPersonIdTwo(Long personId) {
        AppHealthReport infoByPersonIdTwo = baseMapper.getInfoByPersonIdTwo(personId);
        return infoByPersonIdTwo;
    }

    public void sendException(){
        QueryWrapper<AppSmsConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_enabled", 0);
        queryWrapper.eq("type", RemindTypeEnums.EXCEPTION_REMIND.getCode());
        List<AppSmsConfig> smsConfigList = smsConfigService.list(queryWrapper);
        List<Integer> list=smsConfigList.stream().map(AppSmsConfig::getReminder).collect(Collectors.toList());
        Boolean is=false;
        if(list.size()>0){
            is=list.contains(RemindObjectEnums.YOURSELF.getCode());
        }
        if (ObjectUtil.isNotEmpty(smsConfigList)) {
            String mobile = "";
            for (AppSmsConfig appSmsConfig : smsConfigList) {
                switch (appSmsConfig.getReminder()) {
                    case 1:
                        mobile = SecurityUtils.getLoginUser().getUser().getPhonenumber();
                        message = "???????????????????????????????????????";
                        break;
                    case 2:
                        if(!is){
                            SysDept sysDept = deptService.getOne(new LambdaQueryWrapper<SysDept>().eq(SysDept::getDeptId, SecurityUtils.getDeptId()));
                            message = "??????????????????"+SecurityUtils.getLoginUser().getUser().getNickName()+"??????????????????";
                            mobile = sysDept.getPhone();
                            break;
                        }
                        continue;
                    case 3:
                        message = "??????????????????" + SecurityUtils.getLoginUser().getUser().getNickName() + "??????????????????";
                        mobile = smsConfigService.getAppointPhone(appSmsConfig.getAppointUser(),is);
                        break;
                }
                //????????????
                SmsOracleConfigDTO dto = new SmsOracleConfigDTO();
                dto.setCreateDate(new Timestamp(DateUtils.getCurrentTimeStamp()));
                dto.setMobile(mobile);
                dto.setMessage(message);
                log.info("?????????????????????++++++++++++++"+dto.toString());
                sysConfigService.insertSms(dto);
                //??????????????????
                smsConfigService.sendSmsLog(dto.getMessage(),dto.getMobile());
            }
        }
    }

}
