package com.ruoyi.web.controller.app;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.entity.AppSmsSendLog;
import com.ruoyi.system.service.IAppSmsSendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@Api(tags = {"短信发送记录"})
@RestController
@RequestMapping("/appSmsSendLog")
public class AppSmsSendLogController extends BaseController {

   @Autowired
   private IAppSmsSendLogService appSmsSendLogService;

   @GetMapping("/pageListSmsSendLog")
   @ApiOperation("分页展示所有的短信推送日志")
   public ResultVO<PageInfo<AppSmsSendLog>> pageListSmsSendLog(PageDomain pageDomain){

      PageHelper.startPage(pageDomain.getPageNum(),pageDomain.getPageSize(), pageDomain.getOrderBy());
      List<AppSmsSendLog> appSmsSendLogList = appSmsSendLogService.list();
      return new ResultVO<PageInfo<AppSmsSendLog>>(SuccessEnums.QUERY_SUCCESS,new PageInfo<AppSmsSendLog>(appSmsSendLogList));
   }



}
