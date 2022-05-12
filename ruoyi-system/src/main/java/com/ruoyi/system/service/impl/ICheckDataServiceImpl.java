package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.CheckDataMapper;
import com.ruoyi.system.service.ICheckDataService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:16
 */
@Service
@Transactional
public class ICheckDataServiceImpl extends ServiceImpl<CheckDataMapper, CheckData> implements ICheckDataService {

    @Override
    @Async
    public void jsonTransToDB(AppPersonWx appPersonWx) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        String date = dateFormat.format(new Date());
        this.remove(new QueryWrapper<CheckData>().eq("check_id",appPersonWx.getId()));

        String reportJson = appPersonWx.getReportJson();
        if(StringUtil.isEmpty(reportJson)){
            return;
        }
        JSONArray array = JSONArray.parseArray(reportJson);
        List<CheckData> checkDataList = new ArrayList<>();
        //遍历registerJson
        if(ObjectUtil.isNotEmpty(array)){
            for(Object object:array){
                JSONObject json = JSONObject.parseObject(object.toString());
                CheckData data = new CheckData();
                data.setDetailId(json.getLong("detailId"));
                data.setTemplateId(json.getLong("templateId"));
                data.setChineseName(json.getString("chineseName"));
                data.setSort(json.getInteger("sort"));
                data.setCheckId(appPersonWx.getId());
                data.setCheckTime(appPersonWx.getCreateTime());
                if(json.containsKey("selectOptions") && StringUtil.isNotEmpty(json.getString("selectOptions"))){
                    String selectOptions = json.getString("selectOptions");
                    JSONArray options = JSONArray.parseArray(selectOptions);
                    //单选
                    if("radio".equals(json.getString("columnType"))){
                        List<Object> jl =  options.stream().filter(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            return jo.containsKey("value") && jo.getString("value").equals(json.getString("value"));
                        }).collect(Collectors.toList());
                        List<String> ls = jl.stream().map(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            return jo.getString("label");
                        }).collect(Collectors.toList());
                        data.setChineseValue(ObjectUtil.isNotEmpty(ls)?ls.get(0):"");
                        checkDataList.add(data);

                        //备注
                        if(ObjectUtil.isNotEmpty(jl)){
                            JSONObject jsonObject = JSON.parseObject(jl.get(0).toString());
                            if(jsonObject.containsKey("hasRemark") && "1".equals(jsonObject.getString("hasRemark"))){
                                CheckData cdata = new CheckData();
                                cdata.setDetailId(null);
                                cdata.setParentDetailId(json.getLong("detailId"));
                                cdata.setTemplateId(json.getLong("templateId"));
                                cdata.setSort(null);
                                cdata.setCheckId(appPersonWx.getId());
                                cdata.setCheckTime(appPersonWx.getCreateTime());
                                //解析remarkOptions
                                cdata.setChineseName(jsonObject.getString("remarkTitle"));
                                //1输入框 2单选 3多选
                                if("1".equals(jsonObject.getString("remarkType"))){
                                    cdata.setChineseValue(jsonObject.getString("remarkValue"));
                                }else if(jsonObject.containsKey("remarkOptions") && ObjectUtil.isNotEmpty(jsonObject.getJSONArray("remarkOptions"))){
                                    JSONArray ja = jsonObject.getJSONArray("remarkOptions");
                                    List<String> strs = new ArrayList<>();
                                    String ljs = jsonObject.getString("remarkValue");
                                    List<String> lj = Arrays.asList(ljs.split(","));
                                    ja.stream().forEach(t->{
                                        JSONObject jo = JSONObject.parseObject(t.toString());
                                        String value = jo.getString("index");
                                        if(lj.contains(value)){
                                            strs.add(jo.getString("label"));
                                        }
                                    });
                                    if(ObjectUtil.isNotEmpty(strs))cdata.setChineseValue( StringUtils.join(strs,"|"));
                                }
                                checkDataList.add(cdata);
                            }
                        }

                        //多选
                    }else if("checkbox".equals(json.getString("columnType"))){
                        List<String> strs = new ArrayList<>();
                        String ljs = json.containsKey("value")?json.getString("value"):"";
                        List<String> lj = Arrays.asList(ljs.split(","));
                        options.stream().forEach(t->{
                            JSONObject jo = JSONObject.parseObject(t.toString());
                            String value = jo.getString("value");
                            if(lj.contains(value)){
                                strs.add(jo.getString("label"));
                            }
                        });
                        data.setChineseValue(ObjectUtil.isNotEmpty(strs)? StringUtils.join(strs,"|"):"");
                        checkDataList.add(data);
                    }
                }else{
                    //填空
                    if("input".equals(json.getString("columnType")) || "input-date".equals(json.getString("columnType"))){
                        data.setChineseValue(json.getString("value"));
                        checkDataList.add(data);
                    }
                }
            }
        }
        try{
            this.saveBatch(checkDataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
