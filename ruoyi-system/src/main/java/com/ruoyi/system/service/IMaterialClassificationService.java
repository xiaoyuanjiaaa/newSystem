package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.entity.AppAsset;
import com.ruoyi.system.entity.MaterialClassification;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IMaterialClassificationService extends IService<MaterialClassification> {

    List<MaterialClassification> selectMaterialClassificationList(MaterialClassification materialClassification);

    int updateStatus(List<MaterialClassification> list);

    int insertMaterial(MaterialClassification materialClassification);

    int updateMaterial(MaterialClassification materialClassification);

    MaterialClassification getOneByCode(String kindCode);

    int delMaterial(String kindCode);

    //下拉菜单
    List<MaterialClassification> getDown();
}
