package com.ruoyi.system.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.SysRegisterDto;
import com.ruoyi.system.entity.SysRegister;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/10/19 14:07
 */
public interface ISysRegisterService extends IService<SysRegister> {


    /**
     * 分页查询注册信息
     * @param sysRegisterDto
     * @return
     */
    IPage<SysRegister> listPage(SysRegisterDto sysRegisterDto);

    JSONObject tableHead();

    List<JSONObject> tableHeadNew();

    IPage<JSONObject> setParams(IPage<SysRegister> registerIPage);
}
