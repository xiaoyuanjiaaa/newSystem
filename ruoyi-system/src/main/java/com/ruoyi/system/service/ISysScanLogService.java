package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.SysScanLogDTO;
import com.ruoyi.system.entity.SysScanLog;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description  
 * @Author  ddl
 * @Date 2021-08-14 01:52 
 */
public interface ISysScanLogService extends IService<SysScanLog> {

    boolean saveInfo(SysScanLogDTO saveDTO);

}
