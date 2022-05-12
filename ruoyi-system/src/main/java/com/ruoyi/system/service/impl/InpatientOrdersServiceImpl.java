package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.InpatientOrders;
import com.ruoyi.system.entity.MaterialClassification;
import com.ruoyi.system.entity.vo.InpatientOrdersTypeNameVo;
import com.ruoyi.system.entity.vo.InpatientOrdersVo;
import com.ruoyi.system.mapper.InpatientOrdersMapper;
import com.ruoyi.system.mapper.MaterialClassificationMapper;
import com.ruoyi.system.service.IInpatientOrdersService;
import com.ruoyi.system.service.IMaterialClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InpatientOrdersServiceImpl  extends ServiceImpl<InpatientOrdersMapper, InpatientOrders> implements IInpatientOrdersService {

    @Autowired
     InpatientOrdersMapper mapper;

    @Override
    public List<InpatientOrders> getList() {
        QueryWrapper <InpatientOrders> queryWrapper = new QueryWrapper<>();


        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<InpatientOrdersVo> getPageList(InpatientOrdersVo vo) {
        List<InpatientOrdersVo> list = mapper.getPageList(vo);
        return list;
    }

    @Override
    public List<InpatientOrdersTypeNameVo> getTypeName() {
        return mapper.getTypeName();
    }
}
