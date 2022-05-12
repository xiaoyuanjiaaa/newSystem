package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.MaterialOutputMapper;
import com.ruoyi.system.mapper.MaterialSuppliesMapper;
import com.ruoyi.system.mapper.MaterialThresholdMapper;
import com.ruoyi.system.service.IMaterialThresholdService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialThresholdServiceImpl  extends ServiceImpl<MaterialThresholdMapper, MaterialThreshold> implements IMaterialThresholdService {

    
    @Autowired
    MaterialThresholdMapper mapper;
    @Autowired
    MaterialSuppliesMapper suppliesMapper;
    @Autowired
    MaterialOutputMapper outputMapper;

    @Override
    public List<MaterialThreshold> list(MaterialThreshold materialThreshold) {
        QueryWrapper<MaterialThreshold> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        List<MaterialThreshold> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public MaterialThreshold getOneById(Long id) {
        MaterialThreshold materialThreshold = mapper.getOneById(id);
        Long automaticReserve = 0L;
        QueryWrapper<MaterialSupplies> suppliesQueryWrapper = new QueryWrapper<>();
        suppliesQueryWrapper.eq("id",materialThreshold.getId());
        MaterialSupplies materialSupplies = suppliesMapper.selectOne(suppliesQueryWrapper);
        if(materialThreshold.getEvaluateStandard()==1){
            MaterialOutput materialOutput = outputMapper.getOneOutputAvgNumer(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(),materialSupplies.getSpecifications());
            Long number = (long)Math.ceil(materialOutput.getNumber()/materialThreshold.getEvaluateDays());
            automaticReserve =materialThreshold.getReserveDays()*number;
            materialThreshold.setAutomaticReserve(automaticReserve);
            return materialThreshold;
        }else if(materialThreshold.getEvaluateStandard()==2){
            MaterialOutput materialOutput = outputMapper.getOutputSumNumber(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(),materialSupplies.getSpecifications());
            Long number = (long)Math.ceil(materialOutput.getNumber());
            automaticReserve =materialThreshold.getReserveDays()*number;
            materialThreshold.setAutomaticReserve(automaticReserve);
            return materialThreshold;
        }
        return materialThreshold;
    }

    @Override
    public int add(MaterialThreshold materialThreshold) {
        int i = 0;
        if(materialThreshold.getIsAll()==1){
            materialThreshold.setId(1L);
            mapper.updateThreshold(materialThreshold);
            QueryWrapper<MaterialThreshold> thressholdWrapper = new QueryWrapper<>();
            thressholdWrapper.eq("is_all",1);
            mapper.delete(thressholdWrapper);
            if(materialThreshold.getEvaluateStandard() == 1){
                List<MaterialThreshold> materialThresholdList =  getThreshold(materialThreshold);
                MaterialThreshold threshold = mapper.getAllThreshold();
                if(ObjectUtil.isEmpty(threshold)){
                    threshold = new MaterialThreshold();
                    threshold.setThresholdName("全局配置");
                    threshold.setEvaluateDays(materialThreshold.getEvaluateDays());
                    threshold.setReserveDays(materialThreshold.getReserveDays());
                    threshold.setEvaluateStandard(materialThreshold.getEvaluateStandard());
                    mapper.add(threshold);
                }
                if(ObjectUtil.isNotEmpty(materialThresholdList)){
                    for (MaterialThreshold mTList : materialThresholdList) {
                        mTList.setThresholdName(materialThreshold.getThresholdName());
                        mTList.setIsAll(1);
                    }
                    mapper.saveAll(materialThresholdList);
                }
            }else if(materialThreshold.getEvaluateStandard() == 2){
                List<MaterialThreshold> materialThresholdList =  getThreshold(materialThreshold);
                MaterialThreshold threshold = mapper.getAllThreshold();
                if(ObjectUtil.isEmpty(threshold)){
                    threshold = new MaterialThreshold();
                    threshold.setThresholdName("全局配置");
                    threshold.setEvaluateDays(materialThreshold.getEvaluateDays());
                    threshold.setReserveDays(materialThreshold.getReserveDays());
                    threshold.setEvaluateStandard(materialThreshold.getEvaluateStandard());
                    mapper.add(threshold);
                }
                if(ObjectUtil.isNotEmpty(materialThresholdList)){
                    int a = 0;
                    for (MaterialThreshold mTList : materialThresholdList) {
                        mTList.setThresholdName(materialThreshold.getThresholdName());
                        mTList.setIsAll(1);
                        a++;
                    }
                    mapper.saveAll(materialThresholdList);
                }
            }
            i=1;
        }else if(materialThreshold.getIsAll()==2){
            MaterialThreshold mater = mapper.getOneBuSuppliesId(materialThreshold.getSuppliersId());
            materialThreshold.setId(mater.getId());
            mapper.updateThreshold(materialThreshold);
            i=1;
        }
        return i;
    }

    @Override
    public int updateThreshold(MaterialThreshold materialThreshold) {
        return mapper.updateThreshold(materialThreshold);
    }

    @Override
    public int del(Long id) {
        return mapper.del(id);
    }

    /**
     * 全局计算阈值
     * @param materialThreshold
     */
    @Override
    public List<MaterialThreshold> getThreshold(MaterialThreshold materialThreshold){
        /* 根据物资对象获取出库每日总数量 */
        List<MaterialOutput> getSumNumber = outputMapper.getSumNumber(materialThreshold.getEvaluateDays());
        List<MaterialThreshold> thresholdList = new ArrayList<>();
        if( ObjectUtil.isNotEmpty(getSumNumber)){
            /* 平均值计算 */
            if(materialThreshold.getEvaluateStandard() == 1){
                double number = 0;
                for (MaterialOutput output : getSumNumber) {
                    MaterialThreshold thresholds = mapper.getOneBuSuppliesId(output.getId());
                    if(thresholds == null){
                        MaterialThreshold threshold = new MaterialThreshold();
                        BeanUtils.copyProperties(materialThreshold,threshold);
                        number = Math.ceil(output.getNumber());
                        double sumNumber = Math.ceil(number*materialThreshold.getReserveDays());
                        threshold.setSafetyReserve((long)sumNumber);
                        threshold.setEvaluateStandard(1);
                        threshold.setSuppliersId(output.getId());
                        thresholdList.add(threshold);
                    }
                }
                return thresholdList;
            }
            if(materialThreshold.getEvaluateStandard() == 2) /* 最大值计算 */
            {
                QueryWrapper<MaterialSupplies> queryWrapper = new QueryWrapper<>();
                List<MaterialSupplies> getSupplies = suppliesMapper.selectList(queryWrapper);
                for (MaterialSupplies getSupply : getSupplies) {
                    Long maxNumber  = outputMapper.getMaxNumber(materialThreshold.getEvaluateDays(),getSupply.getMaterialName(),getSupply.getSpecifications());
                    if(ObjectUtil.isEmpty(maxNumber)){
                        maxNumber = 0L;
                    }
                    Long safetyReserve = maxNumber * materialThreshold.getEvaluateDays();
                    MaterialThreshold threshold = new MaterialThreshold();
                    threshold.setSafetyReserve(safetyReserve);
                    threshold.setEvaluateStandard(2);
                    threshold.setSuppliersId(getSupply.getId());
                    thresholdList.add(threshold);
                }
                return thresholdList;
            }
        }
        return thresholdList;
    }

    @Override
    public MaterialThreshold getAllThreshold() {
        return mapper.getAllThreshold();
    }

    @Override
    public int updateAllThreshold(MaterialThreshold materialThreshold) {
        if(ObjectUtil.isNotEmpty(materialThreshold.getReserveDays()) || ObjectUtil.isNotEmpty(materialThreshold.getEvaluateDays()) || ObjectUtil.isNotEmpty(materialThreshold.getEvaluateStandard())) {
            return mapper.updateAllThreshold(materialThreshold);
        }
        return 0;
    }

    @Override
    public MaterialThreshold getOne(Long id) {
        MaterialThreshold materialThreshold = mapper.getOne(id);
        Long automaticReserve = 0L;
        if(ObjectUtil.isNotEmpty(materialThreshold)) {
            MaterialSupplies materialSupplies = suppliesMapper.getOneById(materialThreshold.getSuppliersId());
            if(ObjectUtil.isNotEmpty(materialThreshold.getEvaluateStandard())){
                if (materialThreshold.getEvaluateStandard() == 1) {
                    MaterialOutput materialOutput = outputMapper.getOneOutputAvgNumer(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(), materialSupplies.getSpecifications());
                    if(materialOutput!=null){
                    Long number = (long) Math.ceil(materialOutput.getNumber() / materialThreshold.getEvaluateDays());
                    automaticReserve = materialThreshold.getReserveDays() * number;

                    }
                    return materialThreshold;
                }
                if (materialThreshold.getEvaluateStandard() == 2) {
                    MaterialOutput materialOutput = outputMapper.getOutputSumNumber(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(), materialSupplies.getSpecifications());
                    Long number = (long) Math.ceil(materialOutput.getNumber());
                    automaticReserve = materialThreshold.getReserveDays() * number;
                    materialThreshold.setAutomaticReserve(automaticReserve);
                    return materialThreshold;
                }
            }else if(ObjectUtil.isNotEmpty(materialThreshold.getSafetyReserve())){
                return null;
            }else{
                return mapper.getOneNoOutput(id);
            }
        }else{
            return null;
        }
        return materialThreshold;
    }

    @Override
    public Long getOneThresholdNumber(MaterialThreshold materialThreshold) {
        Long safetyReserve = 0L;
        QueryWrapper<MaterialSupplies> suppliesQueryWrapper = new QueryWrapper<>();
        suppliesQueryWrapper.eq("id",materialThreshold.getId());
        MaterialSupplies materialSupplies = suppliesMapper.selectOne(suppliesQueryWrapper);
        if(ObjectUtil.isNotNull(materialSupplies)){
        if(materialThreshold.getEvaluateStandard()==1){
            MaterialOutput materialOutput = outputMapper.getOneOutputAvgNumer(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(),materialSupplies.getSpecifications());
            return Optional.ofNullable(materialOutput).map(vo -> {
                Long number = (long)Math.ceil(vo.getNumber()/materialThreshold.getEvaluateDays());
                Long result =materialThreshold.getReserveDays()*number;
                return result;
                    }).orElse(null);
        }else if(materialThreshold.getEvaluateStandard()==2){
            MaterialOutput materialOutput = outputMapper.getOutputSumNumber(materialThreshold.getEvaluateDays(), materialSupplies.getMaterialName(),materialSupplies.getSpecifications());
            return Optional.ofNullable(materialOutput).map(vo -> {
                Long number = (long)Math.ceil(vo.getNumber());
                Long result1 =materialThreshold.getReserveDays()*number;
                return result1;
            }).orElse(null);

        }
        };
        return safetyReserve;
    }

    @Override
    public int insertNoOut(MaterialThreshold materialThreshold) {
        MaterialThreshold materialThresholds = mapper.getOneBuSuppliesId(materialThreshold.getSuppliersId());
        if(ObjectUtil.isEmpty(materialThresholds)){
            return mapper.insertNoOut(materialThreshold);
        }else if(ObjectUtil.isNotEmpty(materialThresholds)){
            return mapper.updateNoOut(materialThresholds.getId(),materialThreshold.getSafetyReserve());
        }
        return 1;
    }

    @Override
    public MaterialThreshold getOneNoOutput(Long id) {
        MaterialThreshold materialThreshold = mapper.getOneNoOutput(id);
        return materialThreshold;
    }


}
