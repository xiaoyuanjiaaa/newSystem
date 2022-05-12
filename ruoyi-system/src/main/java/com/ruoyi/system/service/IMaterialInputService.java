package com.ruoyi.system.service;

import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.entity.MaterialOutput;

import java.util.List;

public interface IMaterialInputService {

    List<MaterialInput> list(MaterialInput materialInput);

    MaterialInput getOneById(Long id);

    int add(MaterialInput materialInput);
}
