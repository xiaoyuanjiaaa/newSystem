package com.ruoyi.web.controller.app;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mchange.v1.util.ArrayUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.entity.SmsConfig;
import com.ruoyi.system.service.ISmsConfigService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"短信配置"})
@RequestMapping("/smsConfig")
public class SmsConfigController {

   @Autowired
   private ISmsConfigService smsConfigService;

   @Autowired
   private ISysUserService userService;

   @ApiOperation(value = "查询短信配置")
   @GetMapping("/list")
   public ResultVO<List<SmsConfig>> listSmsConfig(){
      QueryWrapper<SmsConfig> queryWrapper = new QueryWrapper<>();
      List<SmsConfig> list = smsConfigService.list(queryWrapper);
      list.stream().forEach(t->{
         String mobiles = t.getMobiles();
         List<String> mobileList = Arrays.asList(mobiles.split(","));
         if(ObjectUtil.isNotEmpty(mobileList)){
            List<SysUser> users = userService.list(new QueryWrapper<SysUser>().in("phonenumber",mobileList).select("user_id","nick_name","phonenumber"));
            t.setSysUsers(users);
         }
      });
      return new ResultVO<List<SmsConfig>>(SuccessEnums.QUERY_SUCCESS,list);
   }


   @ApiOperation(value = "添加短信配置")
   @PutMapping("/add")
   public ResultVO addSmsConfig(@RequestBody SmsConfig smsConfig){
      SmsConfig config = smsConfigService.getOne(new QueryWrapper<SmsConfig>().eq("type",smsConfig.getType()));
      if(ObjectUtil.isEmpty(config)){
         smsConfigService.save(smsConfig);
         return new ResultVO<>(SuccessEnums.SAVE_SUCCESS,null);
      }else{
         return new ResultVO<>(FailEnums.SAVE_FAIL,"已存在该类型的配置");
      }
   }


   @ApiOperation(value = "修改短信配置")
   @PostMapping("/update")
   public ResultVO updateSmsConfig(@RequestBody SmsConfig smsConfig){
      smsConfigService.updateById(smsConfig);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @ApiOperation(value = "删除短信配置")
   @DeleteMapping("/delete/{id}")
   public ResultVO updateSmsConfig(@PathVariable("id") Long id){
      smsConfigService.removeById(id);
      return new ResultVO<>(SuccessEnums.DELETE_SUCCESS,null);
   }

}
