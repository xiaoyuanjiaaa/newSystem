package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppTemplateDetail;
import com.ruoyi.system.mapper.AppTemplateDetailMapper;
import com.ruoyi.system.service.IAppTemplateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:11 
 */
@Service
public class AppTemplateDetailServiceImpl extends ServiceImpl<AppTemplateDetailMapper, AppTemplateDetail> implements IAppTemplateDetailService {

   @Autowired
   private AppTemplateDetailMapper appTemplateDetailMapper;

}
