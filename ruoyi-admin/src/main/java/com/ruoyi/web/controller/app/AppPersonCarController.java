package com.ruoyi.web.controller.app;

import com.ruoyi.system.service.IAppPersonCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@RequestMapping("/appPersonCar")
public class AppPersonCarController {

   @Autowired
   private IAppPersonCarService appPersonCarService;

}
