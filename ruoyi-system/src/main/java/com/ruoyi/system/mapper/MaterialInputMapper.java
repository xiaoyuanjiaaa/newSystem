package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.MaterialInput;

public interface MaterialInputMapper extends BaseMapper<MaterialInput> {
    int delete(Long id);

    int insert(MaterialInput materialInput);

    int insertDynamic(MaterialInput materialInput);

    int updateDynamic(MaterialInput materialInput);

    int update(MaterialInput materialInput);

    MaterialInput selectById(Long id);

}
