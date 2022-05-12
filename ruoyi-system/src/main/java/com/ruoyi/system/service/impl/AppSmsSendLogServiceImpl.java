package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppSmsSendLog;
import com.ruoyi.system.mapper.AppSmsSendLogMapper;
import com.ruoyi.system.service.IAppSmsSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:11 
 */
@Service
public class AppSmsSendLogServiceImpl extends ServiceImpl<AppSmsSendLogMapper, AppSmsSendLog> implements IAppSmsSendLogService {

   @Autowired
   private AppSmsSendLogMapper appSmsSendLogMapper;

}
