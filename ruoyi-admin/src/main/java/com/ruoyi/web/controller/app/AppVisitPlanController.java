package com.ruoyi.web.controller.app;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanSaveDTO;
import com.ruoyi.system.dto.AppVisitPlanUpdateDTO;
import com.ruoyi.system.entity.AppChangeReport;
import com.ruoyi.system.excel.AppVisitExcel;
import com.ruoyi.system.service.IAppVisitPlanService;
import com.ruoyi.system.service.impl.AppVisitPlanServiceImpl;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppVisitPlanVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@Api(tags = {"访客计划"})
@RequestMapping("/appVisitPlan")
public class AppVisitPlanController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger(AppVisitPlanController.class);

   @Autowired
   private IAppVisitPlanService appVisitPlanService;

   @ApiOperation("根据信息获取访问记录")
   @GetMapping("/getAppVisitPlan")
   public ResultVO<AppVisitPlanVO> getAppVisitPlan(AppVisitPlanQueryDTO dto) {
      AppVisitPlanVO result = appVisitPlanService.getAppVisitPlanToday(dto);
      return new ResultVO<AppVisitPlanVO>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("根据信息获取今天访问记录")
   @GetMapping("/getAppVisitPlanToday")
   public ResultVO<AppVisitPlanVO> getAppVisitPlanToday(AppVisitPlanQueryDTO dto) {
      AppVisitPlanVO result = appVisitPlanService.getAppVisitPlanToday(dto);
      return new ResultVO<AppVisitPlanVO>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("添加访客计划")
   @PostMapping("/saveInfo")
   public ResultVO<AppVisitPlanVO> saveInfo(@RequestBody AppVisitPlanSaveDTO dto) {
      // 若在外部添加访客计划需要将之前没有关闭的访客计划关闭
      if(dto.getType()==1){
         appVisitPlanService.updateAppVisitPlanIsColse(dto.getIdNum());
      }
      if("true".equals(dto.getYjfzcodeColor())){
         dto.setYjfzcodeColor("yellow");
      }else{
         dto.setYjfzcodeColor("green");
      }
      appVisitPlanService.updateAppVisitStatus(dto.getIdNum());
      String destinationName=null;
      String destination = dto.getDestination();
      Object array = JSONArray.parse(destination);
      if(ObjectUtil.isNotEmpty(array)){
         JSONObject json = JSONObject.parseObject(array.toString());
         destinationName=json.getString("destinationName");
         dto.setDestinationName(destinationName);
      }
      AppVisitPlanVO result = appVisitPlanService.saveAppVisitPlan(dto);
      log.info("访客计划保存姓名======"+result.getPersonName());
      return new ResultVO<AppVisitPlanVO>(SuccessEnums.OPERATION_SUCCESS, result);
   }

   @ApiOperation("关闭访客计划")
   @PostMapping("/updateInfo")
   public ResultVO<Integer> updateInfo(@RequestBody AppVisitPlanUpdateDTO dto) {
      Integer result = appVisitPlanService.updateInfo(dto);
      return new ResultVO<Integer>(SuccessEnums.OPERATION_SUCCESS, result);
   }

   @GetMapping("/list")
   @ApiOperation(value = "PDA扫码", notes = "PDA扫码")
   public AjaxResult list(AppVisitExcel appVisitExcel){
      startPage();
      Map<String,Object> map=new HashMap<>();
      List<Map<String,String>> list = appVisitPlanService.selectProcessList(appVisitExcel);
      int count = appVisitPlanService.countTotal(appVisitExcel);
      map.put("list",list);
      map.put("total",count);
      return AjaxResult.success(map);
   }


   @GetMapping("/listRecord")
   @ApiOperation(value = "PDA扫码", notes = "PDA扫码")
   public AjaxResult listRecord(AppVisitExcel appVisitExcel){
      startPage();
      Map<String,Object> map=new HashMap<>();
      List<Map<String,String>> list = appVisitPlanService.getAppVisitPlanAll(appVisitExcel);
      int count = appVisitPlanService.getAppVisitPlanAllCount(appVisitExcel);
      map.put("list",list);
      map.put("total",count);
      return AjaxResult.success(map);
   }

}
