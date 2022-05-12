package com.ruoyi.web.controller.app;

import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.dto.AppVisitRecordSaveDTO;
import com.ruoyi.system.service.IAppVisitRecordService;
import com.ruoyi.system.vo.AppVisitRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@Api(tags= {"到访记录"})
@RequestMapping("/appVisitRecord")
public class AppVisitRecordController {

   @Autowired
   private IAppVisitRecordService appVisitRecordService;

   @ApiOperation("添加到访记录")
   @PostMapping("/saveAppVisitRecord")
   public ResultVO<AppVisitRecordVO> saveAppVisitRecord(@RequestBody AppVisitRecordSaveDTO saveDTO) {
      AppVisitRecordVO result = appVisitRecordService.saveInfo(saveDTO);
      return new ResultVO<AppVisitRecordVO>(SuccessEnums.OPERATION_SUCCESS, result);
   }

}
