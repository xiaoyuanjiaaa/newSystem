package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruoyi.common.core.domain.entity.Option;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppHealthReportSaveDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.TopicData;
import com.ruoyi.system.mapper.TopicDataMapper;
import com.ruoyi.system.service.IAppHealthReportService;
import com.ruoyi.system.service.ITopicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:16
 */
@Service
@Transactional
public class ITopicDataServiceImpl extends ServiceImpl<TopicDataMapper, TopicData> implements ITopicDataService {

    @Autowired
    private IAppHealthReportService appHealthReportService;

    @Override
    @Async
    public void resolve(AppHealthReportSaveDTO saveDTO, AppHealthReport report) {
        String remark = report.getRemark();//说明
        Date reportTime = report.getReportTime();
        Long reportId = report.getReportId();
        Long personId = report.getPersonId();
        String reportTemperature = report.getReportTemperature();
        String reportJson = report.getReportJson();
        if ("".equals(reportJson) || reportJson == null) { //未填报时
            TopicData topicData3 = new TopicData();
            topicData3.setId(IdWorker.getId());
            topicData3.setPersonId(personId);
            topicData3.setReportId(reportId);
            topicData3.setReportTime(reportTime);
            topicData3.setRemark(remark);
            saveOrUpdate(topicData3);
        }else {
            // 使用new方法解析reportJson
            Gson gson = new Gson();
            List<Option> list = gson.fromJson(reportJson, new TypeToken<List<Option>>() {
            }.getType());
            //判断该用户是否体温异常
            if ("".equals(reportTemperature) || reportTemperature == null) {//体温正常
                TopicData topicData1 = new TopicData();
                topicData1.setId(IdWorker.getId());
                topicData1.setPersonId(personId);
                topicData1.setChineseName("体温检测情况");
                topicData1.setChineseValue("正常");
                topicData1.setCreateTime(reportTime);
                topicData1.setReportId(reportId);
                topicData1.setReportTime(reportTime);
                saveOrUpdate(topicData1);
            } else { //体温异常
                TopicData topicData2 = new TopicData();
                topicData2.setId(IdWorker.getId());
                topicData2.setPersonId(personId);
                topicData2.setChineseName("体温检测情况");
                topicData2.setChineseValue(reportTemperature);
                topicData2.setCreateTime(new Date());
                topicData2.setReportId(reportId);
                topicData2.setReportTime(reportTime);
                saveOrUpdate(topicData2);
            }

            List<TopicData> listTwo = new ArrayList<>();
            for (Option option : list) { //对单选多选填空进行判断
                if ("radio".equals(option.getColumnType())) { //单选
                    String selectOptions = option.getSelectOptions();
                    Gson gson1 = new Gson();
                    List<Option.SelectOptions> list1 = gson1.fromJson(selectOptions, new TypeToken<List<Option.SelectOptions>>() {
                    }.getType());
//                option.setSelectOptionsList(list1);
                    for (Option.SelectOptions options : list1) {
                        if (String.valueOf(option.getValue()) != null) {
                            if (options.getValue() == Integer.valueOf((int) Double.parseDouble(option.getValue().toString()))) {
                                TopicData topicData = new TopicData();
                                topicData.setChineseValue(options.getLabel());
                                topicData.setPersonId(personId);
                                topicData.setId(IdWorker.getId());
                                topicData.setChineseName(option.getChineseName());
                                topicData.setDetailId(option.getDetailId());
                                topicData.setTemplateId(option.getTemplateId());
                                topicData.setCreateTime(new Date());
                                topicData.setReportId(reportId);
                                topicData.setReportTime(reportTime);
                                listTwo.add(topicData);
                            }
                        }
                    }
                    //备注
                    if(StringUtils.isNotEmpty(option.getHasRemark()) && option.getHasRemark().equals("1")){
                        TopicData topicData = new TopicData();
                        topicData.setChineseValue(option.getRemarkValue());
                        topicData.setPersonId(personId);
                        topicData.setId(IdWorker.getId());
                        topicData.setChineseName(option.getRemarkTitle());
                        topicData.setParentDetailId(option.getDetailId());
                        topicData.setTemplateId(option.getTemplateId());
                        topicData.setCreateTime(new Date());
                        topicData.setReportId(reportId);
                        topicData.setReportTime(reportTime);
                        listTwo.add(topicData);
                    }
                } else if ("checkbox".equals(option.getColumnType())) { //多选
                    String valueStr = option.getValue().toString();
                    List<String> value = Arrays.asList(valueStr.split(","));
                    String selectOptions = option.getSelectOptions();
                    Gson gson1 = new Gson();
                    List<Option.SelectOptions> list1 = gson1.fromJson(selectOptions, new TypeToken<List<Option.SelectOptions>>() {
                    }.getType());
                    List<String> listStr = new ArrayList<>();
                    for (Option.SelectOptions options : list1) {
                        for (int i = 0; i < value.size(); i++) {
                            if(String.valueOf(value.get(i))!=null){
                                if(options.getValue()==Integer.parseInt(String.valueOf(value.get(i)))){
                                    String chineseValue="";
                                    chineseValue=options.getLabel();
                                    listStr.add(chineseValue);
                                }
                            }

                        }
                    }
                    TopicData topicData = new TopicData();
                    topicData.setChineseValue(String.join("|",listStr));
                    topicData.setPersonId(personId);
                    topicData.setId(IdWorker.getId());
                    topicData.setChineseName(option.getChineseName());
                    topicData.setDetailId(option.getDetailId());
                    topicData.setTemplateId(option.getTemplateId());
                    topicData.setCreateTime(new Date());
                    topicData.setReportId(reportId);
                    topicData.setReportTime(reportTime);
                    listTwo.add(topicData);
                } else if ("input".equals(option.getColumnType())) {
                    TopicData topicData = new TopicData();
                    topicData.setChineseValue(option.getValue().toString());
                    topicData.setPersonId(personId);
                    topicData.setId(IdWorker.getId());
                    topicData.setChineseName(option.getChineseName());
                    topicData.setDetailId(option.getDetailId());
                    topicData.setTemplateId(option.getTemplateId());
                    topicData.setCreateTime(new Date());
                    topicData.setReportId(reportId);
                    topicData.setReportTime(reportTime);
                    listTwo.add(topicData);
                }

            }
            saveBatch(listTwo);
        }
    }

    @Override
    public Boolean resolveReport(String str) {
        Gson gson = new Gson();
        List<Option> list= gson.fromJson(str, new TypeToken<List<Option>>() {}.getType());
        List<TopicData> listTwo = new ArrayList<>();
        for (Option option : list) {
            String selectOptions = option.getSelectOptions();
            Gson gson1 = new Gson();
            List<Option.SelectOptions> list1= gson1.fromJson(selectOptions, new TypeToken<List<Option.SelectOptions>>() {}.getType());
            for (Option.SelectOptions options : list1) {
                if(String.valueOf(option.getValue())!=null){
                    if(options.getValue()==Integer.valueOf((int) Double.parseDouble(option.getValue().toString()))){
                        TopicData topicData=new TopicData();
                        topicData.setChineseValue(options.getLabel());
                        topicData.setPersonId(1429006804838313986L);
                        topicData.setId(IdWorker.getId());
                        topicData.setChineseName(option.getChineseName());
                        topicData.setDetailId(option.getDetailId());
                        topicData.setTemplateId(option.getTemplateId());
                        topicData.setCreateTime(new Date());

                        listTwo.add(topicData);
                    }
                }
            }
        }
        return  saveBatch(listTwo);

    }


    @Override
    @Async
    public void jsonTransToDB(AppHealthReport report) {
        Long personId = report.getPersonId();
        Date reportTime = report.getReportTime();
        String reportTemperature = report.getReportTemperature();
        String reportJson = report.getReportJson();
        JSONArray array = JSONArray.parseArray(reportJson);
        List<TopicData> topicDataList = new ArrayList<>();
        TopicData topicData = new TopicData();
        topicData.setPersonId(personId);
        topicData.setReportId(report.getReportId());
        topicData.setReportTime(reportTime);
        if (StringUtil.isEmpty(reportJson) && StringUtil.isNotEmpty(report.getRemark())) {
            //未填报时
            topicData.setRemark(report.getRemark());
        }else{
            //判断该用户是否体温异常
            topicData.setChineseName("体温检测情况");
            topicData.setChineseValue(StringUtil.isNotEmpty(reportTemperature)?reportTemperature:"正常");
        }
        topicDataList.add(topicData);
        //遍历reportJson
        if(ObjectUtil.isNotEmpty(array)){
            for(Object object:array){
                JSONObject json = JSONObject.parseObject(object.toString());
                TopicData data = new TopicData();
                data.setDetailId(json.getLong("detailId"));
                data.setTemplateId(json.getLong("templateId"));
                data.setPersonId(personId);
                data.setReportId(report.getReportId());
                data.setChineseName(json.getString("chineseName"));
                data.setReportTime(reportTime);
                if(json.containsKey("selectOptions")){
                    String selectOptions = json.getString("selectOptions");
                    JSONArray options = JSONArray.parseArray(selectOptions);
                    //单选
                    if("radio".equals(json.getString("columnType"))){
                        List<String> ls = options.stream().filter(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            return jo.containsKey("value") && jo.getString("value").equals(json.getString("value"));
                        }).map(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            return jo.getString("label");
                        }).collect(Collectors.toList());
                        data.setChineseValue(ObjectUtil.isNotEmpty(ls)?ls.get(0):"-");
                        topicDataList.add(data);
                        //备注
                        if(json.containsKey("hasRemark") && json.getString("hasRemark").equals("1")){
                            TopicData data2 = new TopicData();
                            data2.setParentDetailId(json.getLong("detailId"));
                            data2.setTemplateId(json.getLong("templateId"));
                            data2.setPersonId(personId);
                            data2.setReportId(report.getReportId());
                            data2.setChineseName(json.getString("remarkTitle"));
                            data2.setReportTime(reportTime);
                            data2.setChineseValue(json.getString("remarkValue"));
                            topicDataList.add(data2);
                        }
                        //多选
                    }else if("checkbox".equals(json.getString("columnType"))){
                        List<String> strs = new ArrayList<>();
                        String ljs = json.getString("value");
                        List<String> lj = Arrays.asList(ljs.split(","));
                        options.stream().forEach(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            String value = jo.getString("value");
                            if(lj.contains(value)){
                                strs.add(jo.getString("label"));
                            }
                        });
                        data.setChineseValue(ObjectUtil.isNotEmpty(strs)? StringUtils.join(strs,"|"):"-");
                        topicDataList.add(data);
                    }
                }else{
                    //填空
                    if("input".equals(json.getString("columnType"))){
                        data.setChineseValue(json.getString("value"));
                        topicDataList.add(data);
                    }
                }
            }
        }
        saveBatch(topicDataList);
    }

//    private Date startTime() throws ParseException {
//
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date start = formater2.parse(formater.format(new Date())+ " 00:00:00");
//        return start;
//    }
//
//    private Date endTime() throws ParseException {
//
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date end = formater2.parse(formater.format(new Date())+ " 23:59:59");
//        return end;
//    }
}
