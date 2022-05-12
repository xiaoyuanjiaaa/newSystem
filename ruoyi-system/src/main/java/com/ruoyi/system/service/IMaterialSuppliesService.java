package com.ruoyi.system.service;

import com.ruoyi.system.dto.MaterialSuppliesDTO;
import com.ruoyi.system.dto.MaterialSuppliesStockDTO;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.MaterialThreshold;
import com.ruoyi.system.entity.vo.MaterialSuppliesVo;

import java.util.List;

/**
 *
 */
public interface IMaterialSuppliesService  {

    List<MaterialSupplies> list (MaterialSupplies materialSupplies);

    int addMaterial(MaterialSupplies materialSupplies);

    MaterialSupplies getOneById(Long id);

    int update(MaterialSupplies materialSupplies);

    int updateSendStatus(Long id);

    int delSupplies(Long id);

    List<MaterialInput> getMaterialInput();


    List<MaterialSupplies> getList (String kindName,String materialName);

    List<MaterialSuppliesVo> evaluate(String materialName);

    List<String> getWarnMaterial();

    /**
     * 大屏-物资储备/出库详情
     * @return
     */
    List<MaterialSuppliesDTO> getSuppliesScreen();

    /**
     * 大屏-物资库存
     * @return
     */
    List<MaterialSuppliesStockDTO> getMaterialStock();

}
