package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanSaveDTO;
import com.ruoyi.system.dto.AppVisitPlanUpdateDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.entity.AppVisitPlan;
import com.ruoyi.system.excel.AppVisitExcel;
import com.ruoyi.system.mapper.AppVisitPlanMapper;
import com.ruoyi.system.service.AppPersonService;
import com.ruoyi.system.service.AppPersonWxService;
import com.ruoyi.system.service.IAppVisitPlanService;
import com.ruoyi.system.service.IAppVisitRecordService;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppVisitPlanVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:12 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppVisitPlanServiceImpl extends ServiceImpl<AppVisitPlanMapper, AppVisitPlan> implements IAppVisitPlanService {

   private static final Logger log = LoggerFactory.getLogger(AppVisitPlanServiceImpl.class);

   @Resource
   private AppVisitPlanMapper appVisitPlanMapper;

   @Autowired
   private IAppVisitRecordService iAppVisitRecordService;

   @Autowired
   private AppPersonService appPersonService;

   @Autowired
   private AppPersonWxService appPersonWxService;

   /**
    * 查询访客信息
    * @param dto
    * @return
    */
   @Override
   public AppVisitPlanVO getAppVisitPlan(AppVisitPlanQueryDTO dto) {
      AppVisitPlanVO result = new AppVisitPlanVO();
      QueryWrapper<AppVisitPlan> queryWrapper = new QueryWrapper<>();
      if(!CheckUtil.NullOrEmpty(dto.getIdNum())){
         queryWrapper.like("id_num", dto.getIdNum());
      }
      if(dto.getPersonId()!=null){
         queryWrapper.eq("person_id", dto.getPersonId());
      }
      if(!CheckUtil.NullOrEmpty(dto.getMobile())){
         queryWrapper.eq("mobile", dto.getMobile());
      }
      if(!CheckUtil.NullOrEmpty(dto.getPersonName())){
         queryWrapper.eq("person_name", dto.getPersonName());
      }
      if(dto.getIsClose()!=null){
         queryWrapper.eq("is_close", dto.getIsClose());
      }
      if(dto.getFlag()){
         queryWrapper.between("create_time", DateUtils.getStartTime(), DateUtils.getEndTime());
      }

      queryWrapper.orderByDesc("plan_id");
      List<AppVisitPlan> items = appVisitPlanMapper.selectList(queryWrapper);
      if(!CheckUtil.NullOrEmpty(items)){
         BeanUtils.copyProperties(items.get(0), result);
         result.setItems(iAppVisitRecordService.getAppVisitRecordByPlanId(result.getPlanId()));
      }
      return result;
   }

   @Override
   public AppVisitPlanVO getAppVisitPlanToday(AppVisitPlanQueryDTO dto) {
      AppVisitPlanVO result = new AppVisitPlanVO();
      QueryWrapper<AppVisitPlan> queryWrapper = new QueryWrapper<>();
      if(dto.getPersonId()!=null){
         queryWrapper.eq("person_id", dto.getPersonId());
      }
      if(!CheckUtil.NullOrEmpty(dto.getIdNum())){
         queryWrapper.like("id_num", dto.getIdNum());
      }
      if(!CheckUtil.NullOrEmpty(dto.getMobile())){
         queryWrapper.eq("mobile", dto.getMobile());
      }
      if(dto.getIsClose()!=null){
         queryWrapper.eq("is_close", dto.getIsClose());
      }else{
         queryWrapper.eq("is_close", 0);
      }
      if(dto.getFlag()){
         queryWrapper.between("create_time", DateUtils.getStartTime(), DateUtils.getEndTime());
      }
      queryWrapper.orderByDesc("plan_id");
      List<AppVisitPlan> items = appVisitPlanMapper.selectList(queryWrapper);
      if(!CheckUtil.NullOrEmpty(items)){
         BeanUtils.copyProperties(items.get(0), result);
         result.setItems(iAppVisitRecordService.getAppVisitRecordByPlanId(result.getPlanId()));
      }
      return result;
   }

   /**
    * 添加访客计划
    * @param saveDTO
    * @return
    */
   @Override
   public AppVisitPlanVO saveAppVisitPlan(AppVisitPlanSaveDTO saveDTO) {

      return Optional.ofNullable(saveDTO).map(dto -> {
         //todo 临时日志打印
         log.info("访客姓名======"+dto.getPersonName());
         Long planId = IdWorker.getId();
         AppVisitPlan info = new AppVisitPlan();
         BeanUtils.copyProperties(dto, info);
         if(dto.getType()==2){//2出口
            info.setIsClose(1L).setUpdateTime(new Date()).setExport(saveDTO.getDoor())
                    .setDestinationDeptName(saveDTO.getDestinationDeptName())
                    .setCodeSource(saveDTO.getQcodeType()==null?3:saveDTO.getQcodeType());
         }

         if(saveDTO.getPersonId()==null && !CheckUtil.NullOrEmpty(saveDTO.getIdNum())){
            Long personId = getPersonIdByIdNum(saveDTO.getIdNum(), saveDTO.getPersonName(), saveDTO.getMobile());
            info.setPersonId(personId);
            if(personId != null && StringUtils.hasText(saveDTO.getLandLine())){
               appPersonService.update(new UpdateWrapper<AppPerson>().eq("person_id",personId).set("telephone",saveDTO.getLandLine()));
               appPersonWxService.update(new UpdateWrapper<AppPersonWx>().eq("person_id",personId).ge("create_time",DateUtils.getDate()).set("telphone",saveDTO.getLandLine()));
            }
         }
         info.setPlanId(planId).setCreateTime(new Date()).setIsClose(0L).setEntrance(saveDTO.getDoor())
                 .setDestinationDeptName(saveDTO.getDestinationDeptName())
                 .setCodeSource(saveDTO.getQcodeType()==null?3:saveDTO.getQcodeType());
         appVisitPlanMapper.insert(info);
         AppVisitPlanVO outDto = new AppVisitPlanVO();
         BeanUtils.copyProperties(info, outDto);
         return outDto;
      }).orElse(new AppVisitPlanVO());
   }

   /**
    * 根据身份证关闭访客计划
    * @param idNum
    * @return
    */
   @Override
   public Integer updateAppVisitPlanIsColse(String idNum) {
      return appVisitPlanMapper.updateAppVisitPlanIsColse(idNum);
   }




   /**
    * 修改访客计划
    * @param dto
    * @return
    */
   @Override
   public Integer updateInfo(AppVisitPlanUpdateDTO dto) {
      AppVisitPlan info = new AppVisitPlan();
      BeanUtils.copyProperties(dto, info);
      info.setIsClose(1L).setUpdateTime(new Date()).setExport(dto.getDoor())
              .setDestinationDeptName(dto.getDestinationDeptName())
              .setCodeSource(dto.getQcodeType()==null?3:dto.getQcodeType());
      return appVisitPlanMapper.updateById(info);
   }

   /**
    * 查询访客今天预检分诊信息
    * @param openId
    * @return
    */
   @Override
   public AppPersonVO getSymptomsByOpenId(String openId, String idNum) {
      return appVisitPlanMapper.getSymptomsByIdNum(openId,idNum);
   }

   @Override
   public  List<Map<String,String>> selectProcessList(AppVisitExcel appVisitExcel) {
      List<Map<String, String>> list = appVisitPlanMapper.selectProcessList(appVisitExcel);
      list.stream().forEach(d->{
         if(d.containsKey("appPersonWxId")){
            d.put("appPersonWxId", String.valueOf(d.get("appPersonWxId")));
         }
      });
      if (appVisitExcel.getFlag()){
         List<Map<String, String>> yjfzcodeColor = list.stream().filter(stringStringMap -> {
            return !"green".equals(stringStringMap.get("yjfzcodeColor"));
         }).collect(Collectors.toList());
         return yjfzcodeColor;
      }
      return list;
   }

   @Override
   public int countTotal(AppVisitExcel appVisitExcel) {
      return appVisitPlanMapper.countTotal(appVisitExcel);
   }

   private Long getPersonIdByIdNum(String idNum, String name, String mobile){
      return appPersonService.getPersonInfo(null,idNum, name, mobile, null, null);
   }

   /**
    * 根据身份证关闭访客计划
    * @param idNum
    * @return
    */
   @Override
   public Integer updateAppVisitStatus(String idNum) {
      return appVisitPlanMapper.updateAppVisitStatus(idNum);
   }


   @Override
   public List<Map<String, String>> getAppVisitPlanAll(AppVisitExcel appVisitExcel) {
      return appVisitPlanMapper.getAppVisitPlanAll(appVisitExcel);
   }

   @Override
   public int getAppVisitPlanAllCount(AppVisitExcel appVisitExcel) {
      return appVisitPlanMapper.getAppVisitPlanAllCount(appVisitExcel);
   }

}
