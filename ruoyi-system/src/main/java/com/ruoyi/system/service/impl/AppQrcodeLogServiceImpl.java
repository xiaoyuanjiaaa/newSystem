package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppQrcodeLog;
import com.ruoyi.system.mapper.AppQrcodeLogMapper;
import com.ruoyi.system.service.IAppQrcodeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:11 
 */
@Service
public class AppQrcodeLogServiceImpl extends ServiceImpl<AppQrcodeLogMapper, AppQrcodeLog> implements IAppQrcodeLogService {

   @Autowired
   private AppQrcodeLogMapper appQrcodeLogMapper;

}
