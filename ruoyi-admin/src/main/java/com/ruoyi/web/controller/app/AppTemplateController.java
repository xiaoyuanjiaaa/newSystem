package com.ruoyi.web.controller.app;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.entity.AppTemplate;
import com.ruoyi.system.entity.AppTemplateDetail;
import com.ruoyi.system.service.IAppTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@RequestMapping("/appTemplate")
@Api(tags = {"模板"})
public class AppTemplateController {

   @Autowired
   private IAppTemplateService appTemplateService;


   @ApiOperation(value = "分页查询模板")
   @GetMapping("/pageAppTemplate")
   public ResultVO<PageInfo<AppTemplate>> pageAppTemplate(PageDomain pageDomain){
      return new ResultVO<PageInfo<AppTemplate>>(SuccessEnums.QUERY_SUCCESS,appTemplateService.pageAppTemplate(pageDomain));
   }

   @ApiOperation(value = "分页查询模板")
   @GetMapping("/pageAppTemplateRegister")
   public ResultVO<PageInfo<AppTemplate>> pageAppTemplateRegister(PageDomain pageDomain){
      return new ResultVO<PageInfo<AppTemplate>>(SuccessEnums.QUERY_SUCCESS,appTemplateService.pageAppTemplate(pageDomain));
   }


   @ApiOperation(value = "添加模板")
   @PostMapping("/addAppTemplate")
   public ResultVO addAppTemplate(@RequestBody AppTemplate appTemplate){
      appTemplateService.save(appTemplate);
      return new ResultVO<>(SuccessEnums.SAVE_SUCCESS,null);
   }


   @ApiOperation(value = "修改模板")
   @PostMapping("/updateAppTemplate")
   public ResultVO updateAppTemplate(@RequestBody AppTemplate appTemplate){
      appTemplateService.updateById(appTemplate);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @ApiOperation(value = "删除模板")
   @DeleteMapping("/deleteAppTemplate/{id}")
   public ResultVO deleteAppTemplate(@PathVariable("id") Long id){
      appTemplateService.removeById(id);
      return new ResultVO<>(SuccessEnums.DELETE_SUCCESS,null);
   }





}
