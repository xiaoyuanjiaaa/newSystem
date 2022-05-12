package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.vo.MaterialOutputVo;
import com.ruoyi.system.mapper.MaterialOutputMapper;
import com.ruoyi.system.mapper.MaterialSuppliesMapper;
import com.ruoyi.system.service.IMaterialOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MaterialOutputServiceImpl implements IMaterialOutputService {

    @Autowired
    MaterialSuppliesMapper suppliesMapper;
    @Autowired
    MaterialOutputMapper mapper;


    @Override
    public List<MaterialOutput> list(MaterialOutput materialOutput) {
        QueryWrapper<MaterialOutput> queryWrapper = new QueryWrapper<>();
        List<MaterialOutput> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<MaterialOutputVo> getOutputNumber(Long id,Long evaluateDays) {
        QueryWrapper<MaterialSupplies> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        MaterialSupplies materialSupplies = suppliesMapper.selectOne(queryWrapper);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dayList = new ArrayList();

        List<MaterialOutputVo> list = mapper.getOutputNumber(evaluateDays,materialSupplies.getMaterialName(),materialSupplies.getSpecifications());
        if(ObjectUtil.isNotEmpty(evaluateDays)){
            for (int i = 1; i < evaluateDays+1; i++) {
                c.setTime(new Date());
                c.add(Calendar.DATE,- i);
                Date d = c.getTime();
                dayList.add(format.format(d));
            }
        }
        Long voId = 0L;
        String dates = "";
        for (MaterialOutputVo materialOutputVo : list) {
            voId = materialOutputVo.getId();
            dates = materialOutputVo.getOutputDate();
        }
        List<MaterialOutputVo> listOutput = new ArrayList<>();
        for (String o : dayList) {
            MaterialOutput materialOutput = mapper.getOutputDate(evaluateDays, materialSupplies.getMaterialName(), materialSupplies.getSpecifications(),o);
            if (materialOutput == null) {
                MaterialOutputVo outputVo = new MaterialOutputVo();
                outputVo.setId(voId);
                outputVo.setOutputDate(o);
                outputVo.setNumber(0);
                listOutput.add(outputVo);
            }
            if(materialOutput != null){
                MaterialOutputVo outputVo = new MaterialOutputVo();
                outputVo.setId(materialOutput.getId());
                outputVo.setOutputDate(materialOutput.getOutputDate());
                outputVo.setNumber((int)materialOutput.getNumber());
                listOutput.add(outputVo);
            }
        }
        return listOutput;
    }
}
