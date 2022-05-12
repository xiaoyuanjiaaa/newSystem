package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.MaterialClassification;
import com.ruoyi.system.entity.MaterialClassificationMapping;
import com.ruoyi.system.mapper.MaterialClassificationMapper;
import com.ruoyi.system.mapper.MaterialClassificationMappingMapper;
import com.ruoyi.system.service.IMaterialClassificationMappingService;
import com.ruoyi.system.service.IMaterialClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialClassificationServiceImpl extends ServiceImpl<MaterialClassificationMapper, MaterialClassification> implements IMaterialClassificationService {

    @Resource
    MaterialClassificationMapper mapper;
    @Resource
    MaterialClassificationMappingMapper mappingMapper;
    @Autowired
    IMaterialClassificationMappingService mappingService;

    @Override
    public List<MaterialClassification> selectMaterialClassificationList(MaterialClassification materialClassification)
    {
        return mapper.selectListByWrapper(materialClassification);
    }

    @Override
    public int updateStatus(List<MaterialClassification> list){
        List<String> cancels = list.stream().filter(t->t.getStatus() == 1).map(MaterialClassification::getKindCode).collect(Collectors.toList());
        List<String> confirms = list.stream().filter(t->t.getStatus() == 2).map(MaterialClassification::getKindCode).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(cancels)){
            mappingMapper.delete(new QueryWrapper<MaterialClassificationMapping>().in("kind_code",cancels));
        }
        if(ObjectUtil.isNotEmpty(confirms)){
            mappingMapper.delete(new QueryWrapper<MaterialClassificationMapping>().in("kind_code",confirms));
            List<MaterialClassificationMapping> mappingList = new ArrayList<>();
            confirms.stream().forEach(t->{
                MaterialClassificationMapping mapping = new MaterialClassificationMapping();
                mapping.setKindCode(t);
                mappingList.add(mapping);
            });
            mappingService.saveBatch(mappingList);
        }
        return 1;
    }

    /**
     * 添加
     * @param materialClassification
     * @return
     */
    @Override
    public int insertMaterial(MaterialClassification materialClassification) {
        return mapper.insert(materialClassification);
    }

    /**
     * 修改
     * @param materialClassification
     * @return
     */
    @Override
    public int updateMaterial(MaterialClassification materialClassification) {
        return mapper.updateMaterial(materialClassification);
    }

    /**
     * 查询一条数据
     * @param kindCode
     * @return
     */
    @Override
    public MaterialClassification getOneByCode(String kindCode) {
        return mapper.getOneByCode(kindCode);
    }

    /**
     * 删除
     * @param kindCode
     * @return
     */
    @Override
    public int delMaterial(String kindCode) {
        return mapper.delByCode(kindCode);
    }

    @Override
    public List<MaterialClassification> getDown() {
        return mapper.getDown();
    }
}
