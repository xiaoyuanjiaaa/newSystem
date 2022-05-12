package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.dto.MaterialSuppliesDTO;
import com.ruoyi.system.dto.MaterialSuppliesStockDTO;
import com.ruoyi.system.entity.MaterialClassificationMapping;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.vo.MaterialSuppliesVo;
import com.ruoyi.system.entity.vo.SuppliesThresholdVo;
import com.ruoyi.system.mapper.MaterialClassificationMappingMapper;
import com.ruoyi.system.mapper.MaterialOutputMapper;
import com.ruoyi.system.mapper.MaterialSuppliesMapper;
import com.ruoyi.system.mapper.MaterialThresholdMapper;
import com.ruoyi.system.service.IMaterialSuppliesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MaterialSuppliesServiceImpl implements IMaterialSuppliesService {

    private MaterialSuppliesMapper mapper;

    private MaterialOutputMapper outputMapper;

    private MaterialThresholdMapper thresholdMapper;

    private MaterialClassificationMappingMapper mappingMapper;


    @Override
    public List<MaterialSupplies> list(MaterialSupplies materialSupplies) {
        QueryWrapper<MaterialSupplies> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("material_category",materialSupplies.getMaterialCategory());
        queryWrapper.eq("is_deleted",0);
        List<MaterialSupplies> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public int addMaterial(MaterialSupplies materialSupplies) {
        return mapper.insertSupplies(materialSupplies);
    }

    @Override
    public MaterialSupplies getOneById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int update(MaterialSupplies materialSupplies) {
        return mapper.updateSupplies(materialSupplies);
    }

    @Override
    public int updateSendStatus(Long id){
        return mapper.updateSendStatus(id);
    }

    @Override
    public int delSupplies(Long id) {
        return mapper.delSupplies(id);
    }


    @Override
    public List<MaterialInput> getMaterialInput() {
        return mapper.getMaterialInput();
    }

    @Override
    public List<MaterialSupplies> getList(String kindName,String materialName) {
        QueryWrapper<MaterialClassificationMapping> queryWrapper = new QueryWrapper<>();
        List<MaterialClassificationMapping> list = mappingMapper.selectList(queryWrapper);
        if(list.size()>0){
            return mapper.getList(kindName,materialName);
        }
        return mapper.getListOr(kindName,materialName);
    }

    @Override
    public List<MaterialSuppliesVo> evaluate(String materialName) {
        List<MaterialSuppliesVo> list = mapper.evaluate(materialName);
        for (MaterialSuppliesVo supplies : list) {
            if(supplies.getId()!=null){
                if (supplies.getNumber() > supplies.getSafetyReserve()) {
                    supplies.setNumberStats("库存充足");
                } else if (supplies.getNumber() < supplies.getSafetyReserve()) {
                    supplies.setNumberStats("库存不足");
                }
            }else{
                continue;
            }
            //暂留 库存状态
//            if(supplies.getNumber()>supplies.getSafetyReserve()){
//                supplies.setNumberStats("库存充足");
//            }else if(supplies.getNumber()<(supplies.getSafetyReserve()/2)){
//                supplies.setNumberStats("库存不足");
//            }else if(supplies.getNumber()>(supplies.getSafetyReserve()/2)&&supplies.getNumber()<supplies.getSafetyReserve()){
//                supplies.setNumberStats("库存预警");
//            }
        }
        return list;
    }

    @Override
    public List<String> getWarnMaterial(){
        List<String> list = mapper.getWarnMaterial();
        return list;
    }



    /**
     * 生成物资表
     * 校验根据出入库记录产生物资表信息
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void addSupplies() {
        List<MaterialInput> list = mapper.getMaterialInput();
        for (MaterialInput materialInput : list) {
            MaterialSupplies supplies = new MaterialSupplies();
            supplies.setMaterialCategory(materialInput.getMaterialCategory());
            supplies.setInstrumentName(materialInput.getInstrumentName());
            supplies.setMethod(materialInput.getMethod());
            if (materialInput.getIsHighvalue() == 0 || materialInput.getIsHighvalue().equals(0)) {
                supplies.setIsHighvalue(0);
            } else {
                supplies.setIsHighvalue(materialInput.getIsHighvalue());
            }
            if (materialInput.getIsOnce() == 0 || materialInput.getIsOnce().equals(0)) {
                supplies.setIsOnce(0);
            } else {
                supplies.setIsOnce(materialInput.getIsOnce());
            }
            supplies.setMaterialName(materialInput.getMaterialName());
            supplies.setSpecifications(materialInput.getSpecifications());
            supplies.setUnit(materialInput.getUnit());
            supplies.setMonitorState(1);
            supplies.setIsDeleted(0);
            supplies.setManufacturer(materialInput.getManufacturer());
            supplies.setShippingCompany(materialInput.getShippingCompany());
            String materialName = materialInput.getMaterialName();
            String specifications = materialInput.getSpecifications();
            MaterialOutput output = mapper.getMaterialOutput(materialName, specifications);
            double number = materialInput.getNumber();
            if (output != null) {
                number = materialInput.getNumber() - output.getNumber();
                if (number < 0) {
                    number = 0;
                }
            }
            supplies.setNumber((int)Math.ceil(number));
            MaterialSupplies ms = mapper.getOne(materialName, specifications);
            if (ms == null) {
                addMaterial(supplies);
            }
        }
    }


    /**
     * 大屏展示选择物资分类的物资
     * @return
     */
    public List<MaterialSuppliesDTO> getSuppliesScreen(){
        List<SuppliesThresholdVo> getSuppliesThreshold = thresholdMapper.getSuppliesThreshold();
        List<MaterialSuppliesDTO> materialSuppliesDTOList = new ArrayList<>();
        int availableDays = 0;
        for (SuppliesThresholdVo suppliesThresholdVo : getSuppliesThreshold) {
            List<MaterialOutput> getSumNumber = mapper.getMaterialReserveOutput(suppliesThresholdVo.getEvaluateDays().longValue());
            for (MaterialOutput materialOutput : getSumNumber) {
                if(suppliesThresholdVo.getId().longValue() == materialOutput.getId().longValue()) {
                    MaterialSuppliesDTO materialSuppliesDTO = new MaterialSuppliesDTO();
                    materialSuppliesDTO.setId(suppliesThresholdVo.getId());
                    materialSuppliesDTO.setMaterialName(suppliesThresholdVo.getMaterialName());
                    materialSuppliesDTO.setNumber(suppliesThresholdVo.getNumber());
                    availableDays = (int) Math.floor(suppliesThresholdVo.getNumber() / materialOutput.getNumber());
                    materialSuppliesDTO.setAvailableDays(availableDays);
                    if (suppliesThresholdVo.getSafetyReserve() != null) {
                        if (suppliesThresholdVo.getNumber() < suppliesThresholdVo.getSafetyReserve()) {
                            materialSuppliesDTO.setState("库存不足");
                        } else if (suppliesThresholdVo.getNumber() > suppliesThresholdVo.getSafetyReserve()) {
                            materialSuppliesDTO.setState("正常");
                        }
                    }
                    materialSuppliesDTOList.add(materialSuppliesDTO);
                }
            }
            /**
             * 展示所有的物资
             */
//            else if(suppliesThresholdVo.getEvaluateDays()==null){
//                suppliesThresholdVo.setEvaluateDays(30);
//                List<MaterialOutput> getSumNumber = outputMapper.getSumNumber(suppliesThresholdVo.getEvaluateDays().longValue());
//                for (MaterialOutput materialOutput : getSumNumber) {
//                    suppliesThresholdVo.setSafetyReserve((int)Math.ceil(materialOutput.getNumber()*suppliesThresholdVo.getEvaluateDays()));
//                    materialSuppliesDTO.setId(suppliesThresholdVo.getId());
//                    materialSuppliesDTO.setMaterialName(suppliesThresholdVo.getMaterialName());
//                    materialSuppliesDTO.setNumber(suppliesThresholdVo.getNumber());
//                    availableDays = (int)Math.floor(suppliesThresholdVo.getNumber()/materialOutput.getNumber());
//                    materialSuppliesDTO.setAvailableDays(availableDays);
//                    if(suppliesThresholdVo.getSafetyReserve()!=null){
//                        if(suppliesThresholdVo.getNumber() < suppliesThresholdVo.getSafetyReserve()){
//                            materialSuppliesDTO.setState("库存不足");
//                        }else if(suppliesThresholdVo.getNumber() > suppliesThresholdVo.getSafetyReserve()){
//                            materialSuppliesDTO.setState("正常");
//                        }
//                    }
//                    materialSuppliesDTOList.add(materialSuppliesDTO);
//                }
//            }
        }
        return materialSuppliesDTOList;
    }


    /**
     * 大屏-物资库存
     * @return
     */
    @Override
    public List<MaterialSuppliesStockDTO> getMaterialStock() {
        return mapper.getMaterialStock();
    }



}
