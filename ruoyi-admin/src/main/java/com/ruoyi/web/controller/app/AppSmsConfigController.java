package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.service.IAppSmsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"短信配置"})
@RequestMapping("/appSmsConfig")
public class AppSmsConfigController {

   @Autowired
   private IAppSmsConfigService appSmsConfigService;

   @ApiOperation(value = "查询短信配置")
   @GetMapping("/listSmsConfig")
   public TableDataInfo listSmsConfig(PageDomain domain){
      return appSmsConfigService.smsConfigList(domain);
   }


   @ApiOperation(value = "添加短信配置")
   @PostMapping("/addSmsConfig")
   public ResultVO addSmsConfig(@RequestBody AppSmsConfig appSmsConfig){
      return appSmsConfigService.addSmsConfig(appSmsConfig);
   }


   @ApiOperation(value = "修改短信配置")
   @PostMapping("/updateSmsConfig")
   public ResultVO updateSmsConfig(@RequestBody AppSmsConfig appSmsConfig){
      appSmsConfigService.updateById(appSmsConfig);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @ApiOperation(value = "删除短信配置")
   @DeleteMapping("/deleteSmsConfig/{id}")
   public ResultVO updateSmsConfig(@PathVariable("id") Long id){
      appSmsConfigService.removeById(id);
      return new ResultVO<>(SuccessEnums.DELETE_SUCCESS,null);
   }

   @ApiOperation("发送短信验证码")
   @PostMapping("/sendMessage/{phone}")
   public ResultVO sendMessage(@PathVariable String phone) {
      return appSmsConfigService.sendMessage(phone);
   }



}
