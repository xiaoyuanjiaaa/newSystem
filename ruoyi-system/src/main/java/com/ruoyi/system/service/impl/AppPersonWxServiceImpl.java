package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.JsonObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.easyExcel.EasyExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.dto.*;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.excel.ReserveData;
import com.ruoyi.system.mapper.AppPersonMapper;
import com.ruoyi.system.mapper.AppPersonWxMapper;
import com.ruoyi.system.mapper.AppPersonWxVisitMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.*;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppPersonWxServiceImpl extends ServiceImpl<AppPersonWxMapper, AppPersonWx> implements AppPersonWxService {

    @Autowired
    private AppPersonService appPersonService;

    @Resource
    private AppPersonMapper appPersonMapper;

    @Autowired
    private SystemService systemService;

    @Resource
    private AppPersonWxMapper appPersonWxMapper;

    @Autowired
    private ICheckDataService checkDataService;

    @Autowired
    private IAppTemplateDetailService appTemplateDetailService;

    @Resource
    private AppPersonWxVisitService appPersonWxVisitService;

    private static final Logger log = LoggerFactory.getLogger(AppPersonWxServiceImpl.class);

    /**
     * @param saveDTO
     * @return
     */
    @Override
    public String saveInfo(AppPersonWxSaveDTO saveDTO) {
        return Optional.ofNullable(saveDTO).map(dto -> {
            List<AppPersonWx> items = getInfoByToday(saveDTO.getParentPersonId(), saveDTO.getPersonName(), saveDTO.getIdNum(), saveDTO.getMobile());
            if (!CheckUtil.NullOrEmpty(items)) {
                AppPersonWxUpdateDTO updateDTO = new AppPersonWxUpdateDTO();
                BeanUtils.copyProperties(dto, updateDTO);
                updateInfo(updateDTO);
            } else {
                Long personId = appPersonService.getPersonInfo(saveDTO.getParentPersonId(), saveDTO.getIdNum(), saveDTO.getPersonName(), saveDTO.getMobile(), null, null);
                AppPersonWx info = new AppPersonWx();
                BeanUtils.copyProperties(dto, info);
                info.setCreateBy(saveDTO.getPersonName());
                info.setPersonId(personId).setId(IdWorker.getId()).setCreateTime(new Date());
                appPersonWxMapper.insert(info);
                //数据解析
                checkDataService.jsonTransToDB(info);

                AppPersonWxVisit visit = new AppPersonWxVisit();
                BeanUtils.copyProperties(info, visit);
                visit.setAppPersonWxId(visit.getId());
                visit.setId(IdWorker.getId()).setCreateTime(new Date());
                visit.setUpdateTime(new Date()).setUpdateUserPhone(saveDTO.getMobile());
                appPersonWxVisitService.saveInfo(visit);
            }
            //二维码颜色
            Integer colour = -16744448;
            if (saveDTO.getFlag()) {
                colour = 16768605;
            }
            //生成二维码返回base64,1为预检分诊
            String qrcodeType = "1";
            String str = systemService.outQrCode(saveDTO.getEndTime(), qrcodeType, saveDTO.getPersonName(), saveDTO.getIdNum(), saveDTO.getMobile(), colour);
            return str;
        }).orElse("");
    }

    /**
     * 修改预检分诊信息
     *
     * @param updateDTO
     * @return
     */
    @Override
    public AppPersonWxVO updateInfo(AppPersonWxUpdateDTO updateDTO) {
        List<AppPersonWx> items = getInfoByToday(null, updateDTO.getPersonName(), updateDTO.getIdNum(), updateDTO.getMobile());
        AppPersonWxVO result = new AppPersonWxVO();
        if (!CheckUtil.NullOrEmpty(items)) {
            updatePersonMobile(items.get(0).getPersonId(), updateDTO.getMobile());
            AppPersonWx info = new AppPersonWx();
            BeanUtils.copyProperties(updateDTO, info);
            info.setCreateBy(updateDTO.getPersonName());
            info.setId(items.get(0).getId());
            if (items.get(0).getEnabled() == 1) {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        info.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
                        appPersonWxMapper.updateById(info);
                        ;
                    }
                } catch (Exception e) {
                    log.error("获取用户信息错误", e);
                    throw new CustomException("入口扫码之后无权修改");
                }
            } else {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        info.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
                    }
                } catch (Exception e) {
                    log.error("获取用户信息错误", e);
//                    throw new CustomException("入口扫码之后无权修改");
                }
                appPersonWxMapper.updateById(info);
            }

            //数据解析
            checkDataService.jsonTransToDB(info);
            BeanUtils.copyProperties(info, result);

            AppPersonWx wx = this.getById(info.getId());
            AppPersonWxVisit visit = new AppPersonWxVisit();
            BeanUtils.copyProperties(wx, visit);
            visit.setAppPersonWxId(visit.getId());
            visit.setId(IdWorker.getId()).setCreateTime(new Date());
            visit.setUpdateTime(new Date()).setUpdateUserPhone(updateDTO.getMobile());
            visit.setUpdateUserPhone(updateDTO.getMobile());
            try {
                if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                    visit.setUpdateUserName(SecurityUtils.getLoginUser().getUser().getUserName());
                    visit.setUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
                    visit.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
                }
            } catch (Exception e) {
                log.error("获取用户信息错误", e);
            }

            if (items.get(0).getEnabled() == 1) {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        visit.setUpdateUserName(SecurityUtils.getLoginUser().getUser().getUserName());
                        visit.setUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
                        visit.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
                    }
                    appPersonWxVisitService.saveInfo(visit);
                } catch (Exception e) {
                    log.error("获取用户信息错误", e);
                    throw new CustomException("入口扫码之后无权修改");
                }
            } else {
                appPersonWxVisitService.saveInfo(visit);
            }

        }

        //二维码颜色
        Integer colour = -16744448;
        if (updateDTO.getFlag()) {
            colour = 16768605;
        }
        //生成二维码返回base64
        String qrcodeType = "1";
        String str = systemService.outQrCode(updateDTO.getEndTime(), qrcodeType, updateDTO.getPersonName(), updateDTO.getIdNum(), updateDTO.getMobile(), colour);
        result.setQcode(str);
        return result;
    }

    private void updatePersonMobile(Long personId, String mobile) {
        AppPerson appPerson = new AppPerson();
        appPerson.setPersonId(personId).setMobile(mobile);
        appPersonMapper.updateById(appPerson);
    }

    private List<AppPersonWx> getInfoByToday(Long parentPersonId, String personName, String idNum, String mobile) {
        Long personId = appPersonService.getPersonInfo(parentPersonId, idNum, personName, mobile, null, null);
        QueryWrapper<AppPersonWx> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("person_id", personId)
                .between("create_time", DateUtils.getStartTime(), DateUtils.getEndTime())
                .orderByDesc("id");
        return appPersonWxMapper.selectList(queryWrapper);
    }

    /**
     * 根据personId获取当天预检分诊信息
     *
     * @param personId
     * @return
     */
    @Override
    public AppPersonWx getInfoByPersonId(Long personId) {
        QueryWrapper<AppPersonWx> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("person_id", personId)
                .between("create_time", DateUtils.getStartTime(), DateUtils.getEndTime())
                .orderByDesc("id");
        List<AppPersonWx> items = appPersonWxMapper.selectList(queryWrapper);
        AppPersonWx po = new AppPersonWx();
        if (!CheckUtil.NullOrEmpty(items)) {
            return items.get(0);
        }
        return po;
    }

    /**
     * 根据personIds获取当天预检分诊信息
     *
     * @param personIds
     * @return
     */
    @Override
    public AppPersonWx getInfoByPersonIds(List<Long> personIds) {
        QueryWrapper<AppPersonWx> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("person_id", personIds)
                .between("create_time", DateUtils.getStartTime(), DateUtils.getEndTime())
                .orderByDesc("id");
        List<AppPersonWx> items = appPersonWxMapper.selectList(queryWrapper);
        AppPersonWx po = new AppPersonWx();
        if (!CheckUtil.NullOrEmpty(items)) {
            return items.get(0);
        }
        return po;
    }

    /**
     * 获取用户当天的预检分诊信息
     *
     * @param queryDTO
     * @return
     */
    @Override
    public AppPersonWxVO getInfo(AppPersonWxQueryDTO queryDTO) {
        if (queryDTO.getType() != null && queryDTO.getType() == 1 && CheckUtil.NullOrEmpty(queryDTO.getOpenId())) {
            return new AppPersonWxVO();
        }
        if (queryDTO.getPersonId() == null && !CheckUtil.NullOrEmpty(queryDTO.getIdNum())) {
            Long personId = appPersonService.getPersonInfo(null, queryDTO.getIdNum(), queryDTO.getPersonName(), queryDTO.getMobile(), null, null);
            queryDTO.setPersonId(personId).setIdNum(null);
        }
        if (queryDTO.getType() == null) {
            return new AppPersonWxVO();
        } else {
            AppPersonWxVO infoByYear = appPersonWxMapper.getInfoByYear(queryDTO);
            if (infoByYear != null && !DateUtils.isEffectiveDate(infoByYear.getCreateTime())) {
                AppPersonWxVO appPersonWx = new AppPersonWxVO();
                appPersonWx.setPersonName(infoByYear.getPersonName());
                appPersonWx.setMobile(infoByYear.getMobile());
                appPersonWx.setIdNum(infoByYear.getIdNum());
                return appPersonWx;
            }
            log.info(queryDTO.getOpenId()+"===getInfo开始==="+DateUtils.getTime());
            AppPersonWxVO appPersonWxVO = appPersonWxMapper.getInfo(queryDTO);
            log.info(queryDTO.getOpenId()+"===getInfo结束==="+DateUtils.getTime());
            return appPersonWxVO;
        }
    }

    @Override
    public PageInfo weChatQueryList(AppPersonWxYuJianDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<AppPersonQueryWxVO> list = appPersonWxMapper.queryList(queryDTO);
        return new PageInfo(list);
    }

    @Override
    public PageInfo  queryList1(AppPersonWxYuJianDTO queryDTO) {
        List<cn.hutool.json.JSONObject> lj = new ArrayList<>();
        PageHelper.startPage(queryDTO.getPageNum(), queryDTO.getPageSize());
        List<AppPersonQueryWxVO> list = appPersonWxMapper.queryList(queryDTO);
        PageInfo pageInfo = new PageInfo(list);
        if (list.size() > 0) {
            for (AppPersonQueryWxVO appPersonQueryWxVO : list) {
                String destination = appPersonQueryWxVO.getDestination();
                cn.hutool.json.JSONObject json=JSONUtil.parseObj(destination);
                if(ObjectUtil.isNotNull(json)) {
                    String destinationName = json.getStr("destinationName");
                    appPersonQueryWxVO.setDestinationName(destinationName);
                }
            }
            //动态解析表单
            List<Long> ids = list.stream().map(AppPersonQueryWxVO::getId).collect(Collectors.toList());
            List<CheckData> checkDataList = checkDataService.list(new QueryWrapper<CheckData>().in("check_id", ids).select("chinese_name", "chinese_value", "detail_id", "parent_detail_id", "check_id"));
            List<cn.hutool.json.JSONObject> heads = this.tableHead();
            List<CheckData> cl = new ArrayList<>();
            checkDataList.stream().forEach(t -> {
                if (t.getDetailId() != null) {
                    cl.add(t);
                } else {
                    List<CheckData> cd = cl.stream().filter(c -> c.getCheckId().longValue() == t.getCheckId().longValue() && c.getDetailId().equals(t.getParentDetailId())).collect(Collectors.toList());
                    if (ObjectUtil.isNotEmpty(cd)) {
                        cd.get(0).getCheckDataList().add(t);
                    }
                }
            });

            Map<String, Object> map = new HashMap<>(16);
            cl.stream().forEach(t -> {
                List<cn.hutool.json.JSONObject> ls = new ArrayList<>();
                if (map.containsKey(t.getCheckId().toString())) {
                    ls = (List<cn.hutool.json.JSONObject>) map.get(t.getCheckId().toString());
                }
                this.setList(ls, heads, t);
                map.put(t.getCheckId().toString(), ls);
            });


            list.stream().forEach(d -> {
                cn.hutool.json.JSONObject jo = JSONUtil.parseObj(d);
                jo.put("id", jo.getStr("id"));
                jo.put("personId", jo.getStr("personId"));
                jo.put("telphone", jo.containsKey("telphone") ? jo.getStr("telphone") : "");
                jo.put("createBy", jo.containsKey("createBy") ? jo.getStr("createBy") : "");
                if (map.containsKey(d.getId().toString())) {
                    List<cn.hutool.json.JSONObject> keyList = (List<cn.hutool.json.JSONObject>) map.get(d.getId().toString());
                    keyList.stream().forEach(l -> {
                        for (String key : l.keySet()) {
                            String value = l.getStr(key);
                            jo.put(key, value);
                        }
                    });
                }
                lj.add(jo);
            });
            pageInfo.setList(lj);
        }
        return pageInfo;
    }

    @Override
    public List<cn.hutool.json.JSONObject> queryList(AppPersonWxYuJianDTO queryDTO) {
        List<cn.hutool.json.JSONObject> lj = new ArrayList<>();
        List<AppPersonQueryWxVO> list = appPersonWxMapper.queryList(queryDTO);
        if (list.size() > 0) {
            for (AppPersonQueryWxVO appPersonQueryWxVO : list) {
                String destinationName = null;
                String destination = appPersonQueryWxVO.getDestination();
                Object array = JSONArray.parse(destination);
                if (ObjectUtil.isNotEmpty(array)) {
                    JSONObject json = JSONObject.parseObject(array.toString());
                    destinationName = json.getString("destinationName");
                }
                appPersonQueryWxVO.setDestinationName(destinationName);
            }
            //动态解析表单
            List<Long> ids = list.stream().map(AppPersonQueryWxVO::getId).collect(Collectors.toList());
            List<CheckData> checkDataList = checkDataService.list(new QueryWrapper<CheckData>().in("check_id", ids).select("chinese_name", "chinese_value", "detail_id", "parent_detail_id", "check_id"));
            List<cn.hutool.json.JSONObject> heads = this.tableHead();
            List<CheckData> cl = new ArrayList<>();
            checkDataList.stream().forEach(t -> {
                if (t.getDetailId() != null) {
                    cl.add(t);
                } else {
                    List<CheckData> cd = cl.stream().filter(c -> c.getCheckId().longValue() == t.getCheckId().longValue() && c.getDetailId().equals(t.getParentDetailId())).collect(Collectors.toList());
                    if (ObjectUtil.isNotEmpty(cd)) {
                        cd.get(0).getCheckDataList().add(t);
                    }
                }
            });

            Map<String, Object> map = new HashMap<>(16);
            cl.stream().forEach(t -> {
                List<cn.hutool.json.JSONObject> ls = new ArrayList<>();
                if (map.containsKey(t.getCheckId().toString())) {
                    ls = (List<cn.hutool.json.JSONObject>) map.get(t.getCheckId().toString());
                }
                this.setList(ls, heads, t);
                map.put(t.getCheckId().toString(), ls);
            });


            list.stream().forEach(d -> {
                cn.hutool.json.JSONObject jo = JSONUtil.parseObj(d);
                jo.put("id", jo.getStr("id"));
                jo.put("personId", jo.getStr("personId"));
                jo.put("telphone", jo.containsKey("telphone") ? jo.getStr("telphone") : "");
                jo.put("createBy", jo.containsKey("createBy") ? jo.getStr("createBy") : "");
                if (map.containsKey(d.getId().toString())) {
                    List<cn.hutool.json.JSONObject> keyList = (List<cn.hutool.json.JSONObject>) map.get(d.getId().toString());
                    keyList.stream().forEach(l -> {
                        for (String key : l.keySet()) {
                            String value = l.getStr(key);
                            jo.put(key, value);
                        }
                    });
                }
                lj.add(jo);
            });
        }

//        if (queryDTO.getFlag()) {
//            List<cn.hutool.json.JSONObject> xcxOpenId = lj.stream().filter(item -> {
//                return StringUtils.isEmpty(item.getStr("xcxOpenId"));
//            }).collect(Collectors.toList());
//            xcxOpenId.get(0).put("total",xcxOpenId.size() - 1);
//            log.info("最终数据长度----------xcxOpenId:{}",xcxOpenId.size());
//            return xcxOpenId;
//        }
//        log.info("最终数据长度----------:{}",lj.size());
        return lj;
    }

    @Override
    public List<AppPersonQueryWxVO> queryTotal(AppPersonWxYuJianDTO queryDTO) {
        return appPersonWxMapper.queryList(queryDTO);
    }

    public List<cn.hutool.json.JSONObject> setList(List<cn.hutool.json.JSONObject> list, List<cn.hutool.json.JSONObject> heads, CheckData t) {
        cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject();
        heads.stream().forEach(i -> {
            if (i.getStr("label").equals(t.getChineseName())) {
                String str = "";
                if (ObjectUtil.isNotEmpty(t.getCheckDataList())) {
                    List<String> strs = new ArrayList<>();
                    t.getCheckDataList().stream().forEach(cl -> {
                        strs.add(cl.getChineseValue());
                    });
                    str = StringUtils.join(strs, "|");
                } else {
                    str = t.getChineseValue() != null ? t.getChineseValue() : "";
                }
                json.put(i.getStr("prop"), str);
            }
        });
        list.add(json);
        return list;
    }

    @Override
    public List<cn.hutool.json.JSONObject> tableHead() {
        List<cn.hutool.json.JSONObject> list = new ArrayList<>();
        List<AppTemplateDetail> details = appTemplateDetailService.list(new QueryWrapper<AppTemplateDetail>().eq("template_id", 6).eq("is_enabled", 0).orderByAsc("sort"));
        if (ObjectUtil.isNotEmpty(details)) {
            Stream.iterate(0, i -> i + 1).limit(details.size()).forEach(i -> {
                cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject(true);
                json.put("prop", "field".concat(String.valueOf(i)));
                json.put("label", details.get(i).getChineseName());
                list.add(json);
            });
        }
        return list;
    }

    /**
     * 修改预检分诊信息
     *
     * @param updateDTO
     * @return
     */
    @Override
    @Transactional
    public AppPersonWxVO updateVisitStatus(AppPersonWxUpdateDTO updateDTO) {
        List<AppPersonWx> items = getInfoByToday(null, updateDTO.getPersonName(), updateDTO.getIdNum(), updateDTO.getMobile());
        AppPersonWxVO result = new AppPersonWxVO();
        if (!CheckUtil.NullOrEmpty(items)) {
            updatePersonMobile(items.get(0).getPersonId(), updateDTO.getMobile());
            AppPersonWx info = new AppPersonWx();
            BeanUtils.copyProperties(updateDTO, info);
            info.setCreateBy(updateDTO.getPersonName());
            info.setId(items.get(0).getId());
            if (items.get(0).getEnabled() == 1) {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        info.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
                        appPersonWxMapper.updateById(info);
                    }
                } catch (Exception e) {
                    log.error("获取用户信息错误", e);
//                    throw new CustomException("入口扫码之后无权修改");
                    info.setCreateBy("管理员");
                }
            } else {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        info.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserName());
                    }
                } catch (Exception e) {
//                    log.error("获取用户信息错误", e);
//                    throw new CustomException("入口扫码之后无权修改");
                    info.setCreateBy("管理员");
                }
                appPersonWxMapper.updateById(info);
            }

            //数据解析
            checkDataService.jsonTransToDB(info);
            BeanUtils.copyProperties(info, result);

            AppPersonWx wx = this.getById(info.getId());
            AppPersonWxVisit visit = new AppPersonWxVisit();
            BeanUtils.copyProperties(wx, visit);
            visit.setAppPersonWxId(visit.getId());
            visit.setId(IdWorker.getId()).setCreateTime(new Date());
            visit.setUpdateTime(new Date()).setUpdateUserPhone(updateDTO.getMobile());
            visit.setUpdateUserPhone(updateDTO.getMobile());
            try {
                if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                    //修改患者确认页面,操作人字段显示有误:原显示用户账号user_name,现修改成用户昵称nike_name
                    visit.setUpdateUserName(SecurityUtils.getLoginUser().getUser().getNickName ());
                    visit.setUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
                    visit.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
                }
            } catch (Exception e) {
                log.error("获取用户信息错误", e);
            }
            visit.setIsVisit(2);
            if (items.get(0).getEnabled() == 1) {
                try {
                    if (ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())) {
                        visit.setUpdateUserName(SecurityUtils.getLoginUser().getUser().getUserName());
                        visit.setUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
                        visit.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
                    }
                    appPersonWxVisitService.saveInfo(visit);
                } catch (Exception e) {
                    log.error("获取用户信息错误", e);
                    throw new CustomException("入口扫码之后无权修改");
                }
            } else {
                appPersonWxVisitService.saveInfo(visit);
            }
        }

        //二维码颜色
        Integer colour = -16744448;
        if (updateDTO.getFlag()) {
            colour = 16768605;
        }
        //生成二维码返回base64
        String qrcodeType = "1";
        String str = systemService.outQrCode(updateDTO.getEndTime(), qrcodeType, updateDTO.getPersonName(), updateDTO.getIdNum(), updateDTO.getMobile(), colour);
        result.setQcode(str);
        return result;
    }

    /**
     * 导出预检分诊信息
     * @param queryDTO
     * @param inputStream
     * @return
     */
    @Override
    public AjaxResult exportAppPersonWx(AppPersonWxYuJianDTO queryDTO, InputStream inputStream,String uploadUri) {
        // 查出代填信息
        List<cn.hutool.json.JSONObject> jsonObjects = baseMapper.queryAll(queryDTO);

        int i = 1;
        List<ReserveData> reserveDataList = new ArrayList<>();
        for (cn.hutool.json.JSONObject aLong : jsonObjects) {
            if (StringUtils.isEmpty(aLong.getStr("cId"))){
                continue;
            }
            final String startTime = queryDTO.getStartTime();
            final String endTime = queryDTO.getEndTime();
            Object object = baseMapper.getJson(aLong.getLong("cId"),startTime,endTime);
//            AppPersonWxYuJianVisitDTO appPersonWxYuJianVisitDTO = new AppPersonWxYuJianVisitDTO();
//            AppPersonWxYuJianVisitDTO appPersonWxYuJianVisitDTO1 = appPersonWxYuJianVisitDTO.setAppPersonWxId(aLong.getLong("id"));
//            // 列表数据
//            List<cn.hutool.json.JSONObject> appPersonWxVisitList = appPersonWxVisitService.queryList(appPersonWxYuJianVisitDTO1);
//            cn.hutool.json.JSONObject jsonObject = appPersonWxVisitList.get(0);
//            // 详情数据
//            AppPersonWxVisit appPersonWxVisit = appPersonWxVisitService.getById(jsonObject.getLong("id"));
            ReserveData reserveData = new ReserveData();
            reserveData.setSerial(i + "");
            i++;
            reserveData.setPersonName(aLong.getStr("personName"));
            reserveData.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(aLong.getDate("createTime")));
            reserveData.setSymptoms(aLong.getStr("symptoms"));
            reserveData.setMobile(aLong.getStr("mobile"));
            reserveData.setIdNum(aLong.getStr("idNum"));
             cn.hutool.json.JSONObject destination = aLong.getJSONObject("destination");
            reserveData.setDepartment(destination.getStr("destinationName"));
            if ("0".equals(aLong.getStr("appointmentStatus"))){
                reserveData.setConfirm("未确认");
            }else if ("1".equals(aLong.getStr("appointmentStatus")) || "3".equals(aLong.getStr("appointmentStatus"))){
                reserveData.setConfirm("已确认");
            }else {
                reserveData.setConfirm("拒绝");
            }
            if (StringUtils.isEmpty(aLong.getStr("xcxOpenId"))){
                reserveData.setReplace("是");
            }else {
                reserveData.setReplace("否");
            }
            // 解析预检分诊信息里面的json
            JSONArray jsonArray = JSONArray.parseArray(object.toString());
            List<Object> columnType = jsonArray.stream().filter(json -> {
                JSONObject jsonObject1 = JSONObject.parseObject(json.toString());
                return "radio".equals(jsonObject1.getString("columnType"));
            }).collect(Collectors.toList());
            for (Object o : columnType) {
                JSONObject jsonObjectOne = JSONObject.parseObject(o.toString());
                    analyticalData(reserveData,jsonObjectOne);
            }
            if (StringUtils.isEmpty(reserveData.getEpidemic())){
                reserveData.setEpidemic("无");
            }else {
                reserveData.setEpidemic(reserveData.getEpidemic().substring(0, reserveData.getEpidemic().length() - 1));
            }

            // 封装结束
            reserveDataList.add(reserveData);
        }
        final String format = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileName = "电子流调明细汇总表" + format + ".xlsx";
        AjaxResult ajaxResult = null;
        try {
              ajaxResult = EasyExcelUtil.generateExcelByTemplate(reserveDataList, inputStream, fileName,uploadUri);
            return ajaxResult;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 解析json并填充数据
     * @param reserveData
     * @param jsonObjectOne
     */
    private void analyticalData(ReserveData reserveData, JSONObject jsonObjectOne) {
         // 获取selectOptions中的值
        JSONArray selectOptions = jsonObjectOne.getJSONArray("selectOptions");
        for (Object selectOption : selectOptions) {
            final JSONObject jsonObject1 = JSONObject.parseObject(selectOption.toString());
            // 对比selectOptions中每个单独json中value与最外层value的值
            JSONArray remarkOptions = jsonObject1.getJSONArray("remarkOptions");
            if (jsonObjectOne.getString("value").equals(jsonObject1.getString("value")) && CollectionUtils.isNotEmpty(remarkOptions)){
                // 如果者匹配那么再判断这个json中的remarkType是否为3，3代表多选
                if ("3".equals(jsonObject1.getString("remarkType"))){
                    // 获取remarkValue的值并拆分为单独的index值
                    String remarkValue = jsonObject1.getString("remarkValue");
                    String[] split = remarkValue.split(",");
                    for (String s : split) {
                        for (Object remarkOption : remarkOptions) {
                            // 利用拆分后的index值与remarkOptions中的index值做匹配，填充最终数据
                            JSONObject jsonObject2 = JSONObject.parseObject(remarkOption.toString());
                            if (jsonObject2.getString("index").equals(s)){
                                reserveData.setEpidemic(jsonObject2.getString("label") + ",");
                            }
                        }
                    }
                }else {
                    // 如果不是3则直接用remarkValue的值去匹配对应的index值并填充
                    String remarkValue = jsonObject1.getString("remarkValue");
                    for (Object s : remarkOptions) {
                            // 利用拆分后的index值与remarkOptions中的index值做匹配，填充最终数据
                            JSONObject jsonObject2 = JSONObject.parseObject(s.toString());
                            if (jsonObject2.getString("index").equals(remarkValue)){
                                reserveData.setEpidemic(jsonObject2.getString("label") + ",");
                            }
                    }
                }
            }
        }
    }

    @Override
    public AppPersonWxPubVO getExpectInformation(String idNum) {
        AppPersonWxYuJianDTO appPersonWxYuJianDTO = new AppPersonWxYuJianDTO();
        appPersonWxYuJianDTO.setIdNum(idNum);
        final String startTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        appPersonWxYuJianDTO.setStartTime(startTime);
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,1);
        Date time = cal.getTime();
        final String endTime = new SimpleDateFormat("yyyy-MM-dd").format(time);
        appPersonWxYuJianDTO.setEndTime(endTime);
        // 获取预检分诊信息
        List<cn.hutool.json.JSONObject> jsonObjects1 = baseMapper.queryAll(appPersonWxYuJianDTO);
        if (CollectionUtils.isEmpty(jsonObjects1)){
            return  null;
        }
        cn.hutool.json.JSONObject jsonObject1 = jsonObjects1.get(0);
        AppPersonWxPubVO appPersonWxPubVO = new AppPersonWxPubVO();
        final Object json = baseMapper.getJson(jsonObject1.getLong("cId"), startTime, endTime);
        appPersonWxPubVO.setExpectId(jsonObject1.getStr("id"))
                .setPersonName(jsonObject1.getStr("personName"))
                .setPersonPhone(jsonObject1.getStr("mobile"))
                .setPersonIdNum(idNum)
                .setCreateTime(jsonObject1.getStr("createTime"))
                .setSymptoms(jsonObject1.getStr("symptoms"))
                .setDestination(jsonObject1.getStr("destination"))
                .setVisitName(jsonObject1.getStr("visit_name"))
                .setVisitRemark(jsonObject1.getStr("visit_remark"))
                .setVisitPhonenumber(jsonObject1.getStr("visit_phonenumber"))
                .setVisitStartTime(jsonObject1.getStr("visit_start_time"))
                .setUpdateTime(jsonObject1.getStr("update_time"))
                .setReportJson(json.toString())
                .setIsAssistant(jsonObject1.getInt("isAssistant"))
                .setPatentRelation(jsonObject1.getStr("patentRelation"))
                .setPatientName(jsonObject1.getStr("patientName"))
                .setPatientNum(jsonObject1.getStr("patientNum"));

        if(jsonObject1!=null && jsonObject1.getLong("person_id")!=null){
            //二维码颜色
//            Integer colour = -16744448;//green
            appPersonWxPubVO.setFlag("false");//默认绿码
            boolean flag = false;
            String content = StringUtils.isNotEmpty(jsonObject1.getStr("content"))?jsonObject1.getStr("content"):"";
            String symptoms = StringUtils.isNotEmpty(jsonObject1.getStr("symptoms"))?jsonObject1.getStr("symptoms"):"0";
            Double number = 0d;
            try{
                number = Double.parseDouble(symptoms);
            }catch (Exception e){
                e.printStackTrace();
            }
            BigDecimal b1 = new BigDecimal(String.valueOf(number));
            BigDecimal b2 = new BigDecimal(String.valueOf(37.3));

            String contactHistory = StringUtils.isNotEmpty(jsonObject1.getStr("contact_history"))?jsonObject1.getStr("contact_history"):"";
            String epidemicHistory = StringUtils.isNotEmpty(jsonObject1.getStr("epidemic_history"))?jsonObject1.getStr("epidemic_history"):"";
            String riskPosition = StringUtils.isNotEmpty(jsonObject1.getStr("risk_position"))?jsonObject1.getStr("risk_position"):"";
            if(content.length()>1 || b1.subtract(b2).doubleValue()>0 || contactHistory.length()>0 ||
                    epidemicHistory.length()>0 || riskPosition.length()>0){
                flag = true;
            }
            if(flag){
                appPersonWxPubVO.setFlag("true");//黄码
//                colour = 16768605;//yellow
            }
        }else{
            return  null;
        }
        return  appPersonWxPubVO;
    }

    @Override
    public boolean updateStatus(String expectId) {
        final AppPersonWx appPersonWx = getById(Long.parseLong(expectId));
        AppPerson appPerson = appPersonService.getAppPerson(appPersonWx.getPersonId());
        List<cn.hutool.json.JSONObject> jsonObjects = queryList(new AppPersonWxYuJianDTO().setIdNum(appPerson.getIdNum()));
        if (CollectionUtils.isEmpty(jsonObjects)){
            throw  new CustomException("未查到对应数据");
        }
        final cn.hutool.json.JSONObject jsonObject = jsonObjects.get(0);
        if (StringUtils.isEmpty(jsonObject.getStr("openId"))){
             AppPersonWxVO info = getInfo(new AppPersonWxQueryDTO().setType(3).setPersonId(appPerson.getPersonId()));
             AppPersonWxUpdateDTO appPersonWxUpdateDTO = new AppPersonWxUpdateDTO();
             BeanUtils.copyProperties(info,appPersonWxUpdateDTO);
             updateVisitStatus(appPersonWxUpdateDTO);
            return true;
        }else {
            AppPersonWxVO info = getInfo(new AppPersonWxQueryDTO().setType(2).setPersonId(appPerson.getPersonId()));
            AppPersonWxUpdateDTO appPersonWxUpdateDTO = new AppPersonWxUpdateDTO();
            BeanUtils.copyProperties(info,appPersonWxUpdateDTO);
            updateVisitStatus(appPersonWxUpdateDTO);
            return true;
        }
    }

}
