package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.InpatientOrders;
import com.ruoyi.system.entity.MaterialClassification;
import com.ruoyi.system.entity.vo.InpatientOrdersTypeNameVo;
import com.ruoyi.system.entity.vo.InpatientOrdersVo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IInpatientOrdersService extends IService<InpatientOrders> {
    List<InpatientOrders> getList();

    /**
     * 展示列表
     * @param vo
     * @return
     */
    List<InpatientOrdersVo> getPageList(InpatientOrdersVo vo);

    /**
     * 下拉列表
     * @return
     */
    List<InpatientOrdersTypeNameVo> getTypeName();
}
