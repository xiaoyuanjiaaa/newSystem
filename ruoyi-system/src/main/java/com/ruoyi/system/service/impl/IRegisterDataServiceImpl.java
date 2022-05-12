package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.entity.RegisterData;
import com.ruoyi.system.entity.SysRegister;
import com.ruoyi.system.mapper.RegisterDataMapper;
import com.ruoyi.system.service.IRegisterDataService;
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
public class IRegisterDataServiceImpl extends ServiceImpl<RegisterDataMapper, RegisterData> implements IRegisterDataService {

    @Override
    @Async
    public void jsonTransToDB(SysRegister sysRegister) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        String date = dateFormat.format(new Date());
        this.remove(new QueryWrapper<RegisterData>().eq("register_id",sysRegister.getId()).ge("register_time",date));

        String registerJson = sysRegister.getRegisterJson();
        if(StringUtil.isEmpty(sysRegister.getRegisterJson())){
            return;
        }
        JSONArray array = JSONArray.parseArray(registerJson);
        List<RegisterData> registerDataList = new ArrayList<>();
        //遍历registerJson
        if(ObjectUtil.isNotEmpty(array)){
            for(Object object:array){
                JSONObject json = JSONObject.parseObject(object.toString());
                RegisterData data = new RegisterData();
                data.setDetailId(json.getLong("detailId"));
                data.setTemplateId(json.getLong("templateId"));
                data.setChineseName(json.getString("chineseName"));
                data.setSort(json.getInteger("sort"));
                data.setRegisterId(sysRegister.getId());
                data.setRegisterTime(sysRegister.getCreateTime());
                if(json.containsKey("selectOptions") && StringUtil.isNotEmpty(json.getString("selectOptions"))){
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
                        registerDataList.add(data);
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
                        registerDataList.add(data);
                    }
                }else{
                    //填空
                    if("input".equals(json.getString("columnType"))){
                        data.setChineseValue(json.getString("value"));
                        registerDataList.add(data);
                    }
                }
            }
        }
        this.saveBatch(registerDataList);
    }

}
