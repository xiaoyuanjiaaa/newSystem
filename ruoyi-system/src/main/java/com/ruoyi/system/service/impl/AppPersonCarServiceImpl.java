package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.AppPersonCar;
import com.ruoyi.system.mapper.AppPersonCarMapper;
import com.ruoyi.system.service.IAppPersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:10 
 */
@Service
public class AppPersonCarServiceImpl extends ServiceImpl<AppPersonCarMapper, AppPersonCar> implements IAppPersonCarService {

   @Autowired
   private AppPersonCarMapper appPersonCarMapper;

}
