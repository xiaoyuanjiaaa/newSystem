package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppCar;
import com.ruoyi.system.mapper.AppCarMapper;
import com.ruoyi.system.service.IAppCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:09 
 */
@Service
public class AppCarServiceImpl extends ServiceImpl<AppCarMapper, AppCar> implements IAppCarService {

   @Autowired
   private AppCarMapper appCarMapper;

}
