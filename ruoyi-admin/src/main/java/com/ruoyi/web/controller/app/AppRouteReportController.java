package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.system.dto.AppRouteReportDTO;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.AppRouteReportSaveDTO;
import com.ruoyi.system.dto.AppRouteReportUpdateDTO;
import com.ruoyi.system.service.IAppRouteReportService;
import com.ruoyi.system.vo.AppRouteReportLeftVO;
import com.ruoyi.system.vo.AppRouteReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"行程填报相关接口"})
@RequestMapping("/appRouteReport")
public class AppRouteReportController {

   @Autowired
   private IAppRouteReportService appRouteReportService;

   @ApiOperation("分页查询行程填报数据")
   @GetMapping("/getPageInfo")
   public ResultVO<IPage<AppRouteReportVO>> getPageInfo(AppRouteReportQueryDTO queryDTO) {
      Page<AppRouteReportVO> page = new Page<>(queryDTO.getIndex(), queryDTO.getPageSize());
      IPage<AppRouteReportVO> result = appRouteReportService.getPageInfo(page, queryDTO);
      return new ResultVO<IPage<AppRouteReportVO>>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("添加行程填报数据")
   @PostMapping("/saveInfo")
   public ResultVO<AppRouteReportVO> saveInfo(@RequestBody AppRouteReportSaveDTO saveDTOO) {
      AppRouteReportVO result = appRouteReportService.addIAppRouteReport(saveDTOO);
      return new ResultVO<AppRouteReportVO>(SuccessEnums.SAVE_SUCCESS, result);
   }

   @ApiOperation("批量添加行程填报数据")
   @PostMapping("/saveInfoList")
   public ResultVO<AppRouteReportVO> saveInfoList(@RequestBody List<AppRouteReportSaveDTO> saveDTList) {
      if(!CheckUtil.NullOrEmpty(saveDTList)){
         saveDTList.stream().forEach(saveDTO->{
            appRouteReportService.addIAppRouteReport(saveDTO);
         });
      }
      return new ResultVO<AppRouteReportVO>(SuccessEnums.SAVE_SUCCESS, null);
   }

   @ApiOperation("修改或审核行程填报数据")
   @PostMapping("/updateInfo")
   public ResultVO<Integer> updateInfo(@RequestBody AppRouteReportUpdateDTO updateDTO) {
      Integer result = appRouteReportService.updateAppRouteReport(updateDTO);
      return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS, result);
   }

   @ApiOperation("统计全院人员分布")
   @GetMapping("/listCountPerson")
   public ResultVO<AppRouteReportLeftVO> listCountPerson(AppRouteReportDTO reportDTO) {

      return new ResultVO<AppRouteReportLeftVO>(SuccessEnums.QUERY_SUCCESS, appRouteReportService.listCountPerson(reportDTO));
   }
}
