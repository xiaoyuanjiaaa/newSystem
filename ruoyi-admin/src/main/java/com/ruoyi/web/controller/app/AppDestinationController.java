package com.ruoyi.web.controller.app;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.dto.AppDestinationQueryDTO;
import com.ruoyi.system.entity.AppDestination;
import com.ruoyi.system.service.IAppDestinationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"目的地管理"})
@RequestMapping("/appDestination")
public class AppDestinationController extends BaseController {

   @Autowired
   private IAppDestinationService appDestinationService;

   @GetMapping("/pageAppDestination")
   @ApiOperation("查询目的地列表")
   public ResultVO<PageInfo<AppDestination>> pageAppDestination(AppDestinationQueryDTO queryDTO){

      return new ResultVO<PageInfo<AppDestination>>(SuccessEnums.QUERY_SUCCESS,appDestinationService.pageAppDestination(queryDTO));
   }
   @GetMapping("/destinationList")
   @ApiOperation("查询目的地列表不分页")
   @ApiImplicitParam(name = "type",value="类型 1.目的地 ，2.出入口，3.检验点",dataType="Integer")
   public ResultVO<List<AppDestination>> destinationList(@RequestParam Integer type){

      return new ResultVO<List<AppDestination>>(SuccessEnums.QUERY_SUCCESS,appDestinationService.destinationList(type));
   }
   @PostMapping("/addAppDestination")
   @ApiOperation("新增目的地")
   public ResultVO addAppDestination(@RequestBody @Valid AppDestination appDestination){
      appDestination.setCreateBy(getUsername());
      appDestination.setUpdateBy(getUsername());
      appDestinationService.addAppDestination(appDestination);
      return new ResultVO<>(SuccessEnums.SAVE_SUCCESS,null);
   }


   @PostMapping("/updateAppDestination")
   @ApiOperation("更新目的地")
   public ResultVO updateAppDestination(@RequestBody AppDestination appDestination){
      appDestination.setUpdateBy(getUsername());
      appDestinationService.updateById(appDestination);
      return new ResultVO<>(SuccessEnums.UPDATE_SUCCESS,null);
   }

   @DeleteMapping("/deleteAppDestination/{id}")
   @ApiOperation("删除目的地")
   public ResultVO deleteAppDestination(@PathVariable("id") Long id){
      appDestinationService.removeById(id);
      return new ResultVO<>(SuccessEnums.DELETE_SUCCESS,null);
   }

}
