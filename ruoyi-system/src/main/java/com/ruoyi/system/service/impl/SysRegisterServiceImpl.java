package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.dto.SysRegisterDto;
import com.ruoyi.system.entity.AppTemplateDetail;
import com.ruoyi.system.entity.RegisterData;
import com.ruoyi.system.entity.SysRegister;
import com.ruoyi.system.mapper.SysRegisterMapper;
import com.ruoyi.system.service.IAppTemplateDetailService;
import com.ruoyi.system.service.IRegisterDataService;
import com.ruoyi.system.service.ISysRegisterService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: jianguo
 * @Date: 2021/10/19 14:16
 */
@Service
public class SysRegisterServiceImpl extends ServiceImpl<SysRegisterMapper, SysRegister> implements ISysRegisterService {

    @Autowired
    private IRegisterDataService registerDataService;

    @Autowired
    private IAppTemplateDetailService appTemplateDetailService;

    @Override
    public IPage<SysRegister> listPage(SysRegisterDto sysRegisterDto) {
        QueryWrapper<SysRegister> queryWrapper = new QueryWrapper<>();
        if (sysRegisterDto.getUserName() != null){
            queryWrapper.like("nick_name",sysRegisterDto.getUserName());
        }
        if (sysRegisterDto.getSex() != null){
            queryWrapper.eq("sex",sysRegisterDto.getSex());
        }
        if (sysRegisterDto.getStatus() != null){
            queryWrapper.eq("status",sysRegisterDto.getStatus());
        }
        if (sysRegisterDto.getIsAsc().equals("desc")){
            queryWrapper.orderByDesc("create_time");
        }else {
            queryWrapper.orderByAsc("create_time");
        }
        queryWrapper.select(SysRegister.class,i->!i.getProperty().equals("registerJson"));
        IPage<SysRegister> page = new Page<>();
        page.setCurrent(sysRegisterDto.getPageNum());
        page.setSize(sysRegisterDto.getPageSize());
        IPage <SysRegister> rsult = page(page,queryWrapper);
        return rsult;
    }

    @Override
    public JSONObject tableHead(){
        JSONObject json = new JSONObject(true);
        List<AppTemplateDetail> details = appTemplateDetailService.list(new QueryWrapper<AppTemplateDetail>().eq("template_id",5).eq("is_enabled",0).orderByAsc("sort"));
        if(ObjectUtil.isNotEmpty(details)){
            Stream.iterate(0, i -> i + 1).limit(details.size()).forEach(i -> {
                json.put("field".concat(String.valueOf(i)),details.get(i).getChineseName());
            });
        }
        return json;
    }

    @Override
    public List<JSONObject> tableHeadNew(){
        List<JSONObject> list = new ArrayList<>();
            List<AppTemplateDetail> details = appTemplateDetailService.list(new QueryWrapper<AppTemplateDetail>().eq("template_id",5).eq("is_enabled",0).orderByAsc("sort"));
            if(ObjectUtil.isNotEmpty(details)){
                Stream.iterate(0, i -> i + 1).limit(details.size()).forEach(i -> {
                    JSONObject json = new JSONObject(true);
                    json.put("prop","field".concat(String.valueOf(i)));
                    json.put("label",details.get(i).getChineseName());
                    list.add(json);
                });
            }
        return list;
    }

    @Override
    public IPage<JSONObject> setParams(IPage<SysRegister> registerIPage){
        List<JSONObject> lj = new ArrayList<>();
        IPage<JSONObject> page = new Page<>();
        page.setRecords(lj);
        page.setTotal(registerIPage.getTotal());
        page.setCurrent(registerIPage.getCurrent());
        page.setPages(registerIPage.getPages());
        page.setSize(registerIPage.getSize());

        List<Long> registerIds = registerIPage.getRecords().stream().map(SysRegister::getId).collect(Collectors.toList());
        if(ObjectUtil.isEmpty(registerIds)){
            return page;
        }
        List<RegisterData> data = registerDataService.list(new QueryWrapper<RegisterData>().in("register_id",registerIds));
        JSONObject heads = this.tableHead();
        //解析数据
        Map<String, Object> map = new HashMap<>(10);
        data.stream().forEach(t->{
            List<JSONObject> list = new ArrayList<>();
            if(map.containsKey(t.getRegisterId().toString())){
                list = (List<JSONObject>) map.get(t.getRegisterId().toString());
            }
            list = setList(list,heads,t);
            map.put(t.getRegisterId().toString(),list);
        });

        registerIPage.getRecords().stream().forEach(d->{
            JSONObject jo = JSONUtil.parseObj(d);
            jo.put("id",jo.getStr("id"));
            if(map.containsKey(d.getId().toString())){
                List<JSONObject> ls = (List<JSONObject>) map.get(d.getId().toString());
                ls.stream().forEach(l->{
                    for (String key : l.keySet()) {
                        String value = l.getStr(key);
                        jo.put(key,value);
                    }
                });
            }
            lj.add(jo);
        });
        page.setRecords(lj);
        return page;
    }


    public List<JSONObject> setList(List<JSONObject> list,JSONObject heads,RegisterData t){
        String filedName = "";
        for (String key : heads.keySet()) {
            String value = heads.getStr(key);
            if(value.equals(t.getChineseName())){
                filedName = key;
            }
        }
        String finalFiledName = filedName;
        list.add(new JSONObject(){{put(finalFiledName,t.getChineseValue());}});
        return list;
    }
}
