package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianVisitDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.AppPersonMapper;
import com.ruoyi.system.mapper.AppPersonWxMapper;
import com.ruoyi.system.mapper.AppPersonWxVisitMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AppPersonQueryWxVO;
import com.ruoyi.system.vo.AppPersonWxVisitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppPersonWxVisitServiceImpl extends ServiceImpl<AppPersonWxVisitMapper, AppPersonWxVisit> implements AppPersonWxVisitService {

    @Resource
    private AppPersonWxVisitMapper appPersonWxVisitMapper;
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
    /**
     *
     * @param saveDTO
     * @return
     */
    @Override
    public void saveInfo(AppPersonWxVisit saveDTO) {
        appPersonWxVisitMapper.insert(saveDTO);
    }

    @Override
    public List<cn.hutool.json.JSONObject> queryList(AppPersonWxYuJianVisitDTO queryDTO) {
        List<cn.hutool.json.JSONObject> lj = new ArrayList<>();
        List<AppPersonQueryWxVO> list = appPersonWxVisitMapper.queryList(queryDTO);
        if(list.size()>0){
            for (AppPersonQueryWxVO appPersonQueryWxVO : list) {
                String destinationName=null;
                String destination = appPersonQueryWxVO.getDestination();
                Object array = JSONArray.parse(destination);
                if(ObjectUtil.isNotEmpty(array)){
                    JSONObject json = JSONObject.parseObject(array.toString());
                    destinationName=json.getString("destinationName");
                }
                appPersonQueryWxVO.setDestinationName(destinationName);
            }
            //动态解析表单
            List<Long> ids = list.stream().map(AppPersonQueryWxVO::getId).collect(Collectors.toList());
            List<CheckData> checkDataList = checkDataService.list(new QueryWrapper<CheckData>().in("check_id",ids).select("chinese_name","chinese_value","detail_id","parent_detail_id","check_id"));
            List<cn.hutool.json.JSONObject> heads = this.tableHead();
            List<CheckData> cl = new ArrayList<>();
            checkDataList.stream().forEach(t->{
                if(t.getDetailId() != null){
                    cl.add(t);
                }else{
                    List<CheckData> cd = cl.stream().filter(c->c.getCheckId().longValue() == t.getCheckId().longValue() && c.getDetailId().equals(t.getParentDetailId())).collect(Collectors.toList());
                    if(ObjectUtil.isNotEmpty(cd)){
                        cd.get(0).getCheckDataList().add(t);
                    }
                }
            });

            Map<String, Object> map = new HashMap<>(16);
            cl.stream().forEach(t->{
                List<cn.hutool.json.JSONObject> ls = new ArrayList<>();
                if(map.containsKey(t.getCheckId().toString())){
                    ls = (List<cn.hutool.json.JSONObject>) map.get(t.getCheckId().toString());
                }
                this.setList(ls,heads,t);
                map.put(t.getCheckId().toString(),ls);
            });


            list.stream().forEach(d->{
                cn.hutool.json.JSONObject jo = JSONUtil.parseObj(d);
                jo.put("id",jo.getStr("id"));
                jo.put("personId",jo.getStr("personId"));
                jo.put("telphone",jo.containsKey("telphone")?jo.getStr("telphone"):"");
                jo.put("createBy",jo.containsKey("createBy")?jo.getStr("createBy"):"");
                if(map.containsKey(d.getId().toString())){
                    List<cn.hutool.json.JSONObject> keyList = (List<cn.hutool.json.JSONObject>) map.get(d.getId().toString());
                    keyList.stream().forEach(l->{
                        for (String key : l.keySet()) {
                            String value = l.getStr(key);
                            jo.put(key,value);
                        }
                    });
                }
                lj.add(jo);
            });
        }
        return lj;
    }

    @Override
    public List<AppPersonQueryWxVO> queryTotal(AppPersonWxYuJianVisitDTO queryDTO) {
        return appPersonWxVisitMapper.queryList(queryDTO);
    }

    @Override
    public Long queryIdListById(Long id) {
        return baseMapper.queryIdListById(id);
    }

    public List<cn.hutool.json.JSONObject> tableHead(){
        List<cn.hutool.json.JSONObject> list = new ArrayList<>();
        List<AppTemplateDetail> details = appTemplateDetailService.list(new QueryWrapper<AppTemplateDetail>().eq("template_id",6).eq("is_enabled",0).orderByAsc("sort"));
        if(ObjectUtil.isNotEmpty(details)){
            Stream.iterate(0, i -> i + 1).limit(details.size()).forEach(i -> {
                cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject(true);
                json.put("prop","field".concat(String.valueOf(i)));
                json.put("label",details.get(i).getChineseName());
                list.add(json);
            });
        }
        return list;
    }

    public List<cn.hutool.json.JSONObject> setList(List<cn.hutool.json.JSONObject> list, List<cn.hutool.json.JSONObject> heads, CheckData t){
        cn.hutool.json.JSONObject json = new cn.hutool.json.JSONObject();
        heads.stream().forEach(i->{
            if(i.getStr("label").equals(t.getChineseName())){
                String str = "";
                if(ObjectUtil.isNotEmpty(t.getCheckDataList())){
                    List<String> strs = new ArrayList<>();
                    t.getCheckDataList().stream().forEach(cl->{
                        strs.add(cl.getChineseValue());
                    });
                    str = StringUtils.join(strs,"|");
                }else{
                    str = t.getChineseValue() != null?t.getChineseValue():"";
                }
                json.put(i.getStr("prop"),str);
            }
        });
        list.add(json);
        return list;
    }

    @Override
    public ResultVO<AppPersonWxVisit> detail(AppPersonWxQueryDTO queryDTO) {
        AppPersonWxVisitVO appPersonWxVisitVO = new AppPersonWxVisitVO();
        List<AppPerson> appPersonList = new ArrayList<>();
        if(queryDTO.getMobile()!=null){
            appPersonList=appPersonService.list(new LambdaQueryWrapper<AppPerson>().eq(AppPerson::getIdNum, queryDTO.getId ()).eq(AppPerson::getMobile,queryDTO.getMobile()));
        }else {
            appPersonList = appPersonService.list(new LambdaQueryWrapper<AppPerson>().eq(AppPerson::getIdNum, queryDTO.getId()).orderByDesc(AppPerson::getUpdateTime));
        }
        AppPerson appPerson = appPersonList.stream().findFirst().orElse(null);
        if(appPerson!=null) {
            appPersonWxVisitVO.setPersonName(appPerson.getPersonName());
            appPersonWxVisitVO.setMobile(appPerson.getMobile());
            appPersonWxVisitVO.setIdNum(appPerson.getIdNum());
            LambdaQueryWrapper<AppPersonWxVisit> queryWrapper = new LambdaQueryWrapper<AppPersonWxVisit>().eq(AppPersonWxVisit::getPersonId, appPerson.getPersonId()).orderByDesc(AppPersonWxVisit::getUpdateTime);
            List<AppPersonWxVisit> appPersonWxVisits = appPersonWxVisitService.getBaseMapper().selectList(queryWrapper);
            AppPersonWxVisit appPersonWxVisit = appPersonWxVisits.stream().findFirst().orElse(null);
            if (ObjectUtil.isNotNull(appPersonWxVisit)) {
                BeanUtils.copyProperties(appPersonWxVisit, appPersonWxVisitVO);
                return new ResultVO<AppPersonWxVisit>(SuccessEnums.QUERY_SUCCESS, appPersonWxVisitVO);
            }
        }
        return new ResultVO<AppPersonWxVisit>(SuccessEnums.QUERY_SUCCESS, null);
    }
}
