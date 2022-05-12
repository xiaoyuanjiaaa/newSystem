package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.InpatientOrders;
import com.ruoyi.system.entity.vo.InpatientOrdersTypeNameVo;
import com.ruoyi.system.entity.vo.InpatientOrdersVo;

import java.util.List;

public interface InpatientOrdersMapper extends BaseMapper<InpatientOrders> {

    List<InpatientOrdersVo> getPageList(InpatientOrdersVo vo);

    List<InpatientOrdersTypeNameVo> getTypeName();
}
