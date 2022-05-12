package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppTemplateDetailQueryDTO;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.entity.AppTemplateDetail;
import com.ruoyi.system.service.IAppTemplateDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@RequestMapping("/appTemplateDetail")
@Api(tags = {"模板详情"})
@Slf4j
public class AppTemplateDetailController {

   @Autowired
   private IAppTemplateDetailService appTemplateDetailService;

   @ApiOperation(value = "查询模板配置")
   @GetMapping("/listAppTemplateDetail/{templateId}")
   public ResultVO<List<AppTemplateDetail>> listAppTemplateDetail(@PathVariable("templateId") Long templateId, AppTemplateDetailQueryDTO queryDTO){
      QueryWrapper<AppTemplateDetail> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("template_id",templateId);
      if (StringUtils.isNotEmpty(queryDTO.getIsEnabled())){
         queryWrapper.eq("is_enabled",queryDTO.getIsEnabled());
      }
      queryWrapper.orderByAsc("CONVERT(sort,SIGNED)");
      final List<AppTemplateDetail> list = appTemplateDetailService.list(queryWrapper);
      list.forEach(appTemplateDetail -> {
         if (StringUtils.isEmpty(appTemplateDetail.getIsRequired())){
            appTemplateDetail.setIsRequired("0");
         }
      });
      final List<AppTemplateDetail> collect = list
              .stream().sorted(Comparator.comparing(AppTemplateDetail :: getIsRequired).reversed()).collect(Collectors.toList());
      return new ResultVO<List<AppTemplateDetail>>(SuccessEnums.QUERY_SUCCESS,collect);
   }

   @ApiOperation(value = "查询模板配置")
   @GetMapping("/listAppTemplateDetailRegister/{templateId}")
   public ResultVO<List<AppTemplateDetail>> listAppTemplateDetailRegister(@PathVariable("templateId") Long templateId, AppTemplateDetailQueryDTO queryDTO){
      QueryWrapper<AppTemplateDetail> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("template_id",templateId);
      if (StringUtils.isNotEmpty(queryDTO.getIsEnabled())){
         queryWrapper.eq("is_enabled",queryDTO.getIsEnabled());
      }
      queryWrapper.orderByAsc("CONVERT(sort,SIGNED)");
      final List<AppTemplateDetail> list = appTemplateDetailService.list(queryWrapper);
      list.forEach(appTemplateDetail -> {
         if (StringUtils.isEmpty(appTemplateDetail.getIsRequired())){
            appTemplateDetail.setIsRequired("0");
         }
      });
      final List<AppTemplateDetail> collect = list
              .stream().sorted(Comparator.comparing(AppTemplateDetail::getIsRequired).reversed()).collect(Collectors.toList());
      return new ResultVO<List<AppTemplateDetail>>(SuccessEnums.QUERY_SUCCESS,collect);
   }


   @ApiOperation(value = "添加模板配置")
   @PostMapping("/addAppTemplateDetail")
   public ResultVO addAppTemplateDetail(@RequestBody AppTemplateDetail appTemplateDetail){
      appTemplateDetailService.save(appTemplateDetail);
      return new ResultVO<>(SuccessEnums.SAVE_SUCCESS,null);
   }


   @ApiOperation(value = "修改模板配置")
   @PostMapping("/updateAppTemplateDetail")
   public ResultVO updateAppTemplateDetail(@RequestBody AppTemplateDetail appTemplateDetail){
      appTemplateDetailService.updateById(appTemplateDetail);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @ApiOperation(value = "批量修改模板配置")
   @PostMapping("/updateAppTemplateDetailBatch")
   public ResultVO updateAppTemplateDetail(@RequestBody List<AppTemplateDetail> appTemplateDetails){
      appTemplateDetailService.updateBatchById(appTemplateDetails);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @ApiOperation(value = "删除模板配置")
   @DeleteMapping("/deleteAppTemplateDetail/{id}")
   public ResultVO deleteAppTemplateDetail(@PathVariable("id") Long id){
      appTemplateDetailService.removeById(id);
      return new ResultVO<>(SuccessEnums.DELETE_SUCCESS,null);
   }


}
