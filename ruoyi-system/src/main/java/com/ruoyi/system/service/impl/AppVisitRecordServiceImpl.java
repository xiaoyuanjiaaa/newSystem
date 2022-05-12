package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.system.dto.AppVisitRecordNumDTO;
import com.ruoyi.system.dto.AppVisitRecordSaveDTO;
import com.ruoyi.system.entity.AppVisitRecord;
import com.ruoyi.system.mapper.AppVisitRecordMapper;
import com.ruoyi.system.service.IAppVisitRecordService;
import com.ruoyi.system.vo.AppVisitRecordVO;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppVisitRecordServiceImpl extends ServiceImpl<AppVisitRecordMapper, AppVisitRecord> implements IAppVisitRecordService {

   @Autowired
   private AppVisitRecordMapper appVisitRecordMapper;

   @Override
   public List<AppVisitRecordVO> getAppVisitRecordByPlanId(Long planId) {
      List<AppVisitRecordVO> outDto = Lists.newArrayList();
      List<AppVisitRecord> items = appVisitRecordMapper.selectList(
              Wrappers.<AppVisitRecord>query().lambda()
                      .eq(AppVisitRecord::getPlanId, planId).orderByDesc(AppVisitRecord::getCreateTime));
      if(!CheckUtil.NullOrEmpty(items)){
         outDto = items.stream().map(info -> {
            AppVisitRecordVO po = new AppVisitRecordVO();
            BeanUtils.copyProperties(info, po);
            return po;
         }).collect(Collectors.toList());
      }
      return outDto;
   }

   /**
    * 添加访客记录数据
    * @param saveDTO
    * @return
    */
   @Override
   public AppVisitRecordVO saveInfo(AppVisitRecordSaveDTO saveDTO) {
      return Optional.ofNullable(saveDTO).map(dto -> {
         AppVisitRecord info = new AppVisitRecord();
         BeanUtils.copyProperties(dto, info);
         info.setCreateTime(new Date()).setRecordId(IdWorker.getId()).setRecordTime(new Date());
         appVisitRecordMapper.insert(info);
         AppVisitRecordVO outDto = new AppVisitRecordVO();
         BeanUtils.copyProperties(info, outDto);
         return outDto;
      }).orElse(new AppVisitRecordVO());
   }


   @Override
   public List<AppVisitRecordNumDTO> getListNum() {
      List<AppVisitRecordNumDTO> list = appVisitRecordMapper.getListNum();
      return list;
   }
}
