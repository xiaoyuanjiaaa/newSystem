package com.ruoyi.web.controller.app;

import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.statics.ConstantDic;
import com.ruoyi.system.dto.AppPersonQueryByCodeDTO;
import com.ruoyi.system.service.SystemService;
import com.ruoyi.system.vo.AppPersonInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.RGBColor;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@Api(tags = {"苏康码或二维码解析"})
@RequestMapping("/system/skm")
public class SystemController {

   private static final Logger log = LoggerFactory.getLogger(SystemController.class);

   @Autowired
   private SystemService systemService;

   /**
    *
    * @param dto
    * @return
    */
   @ApiOperation("根据苏康码或二维码的url解析获取人员基础信息")
   @PostMapping("/getPersonInfoByCode")
   public ResultVO<AppPersonInfoVO> getPersonInfoByCode(HttpServletRequest request,@RequestBody AppPersonQueryByCodeDTO dto){
      System.out.println("-----扫码获取的人员信息开始-----"+dto.getUrlContent());
      if(dto.getUrlContent().contains(ConstantDic.QRCODE)){
         if(!dto.getUrlContent().substring(dto.getUrlContent().length()-6).equals("result")){
            throw new CustomException("请扫描完整的苏康码");
         }
         dto.setType(1);
      }else if(dto.getUrlContent().contains(ConstantDic.ERWEIMA)){
         dto.setType(2);
         dto.setUrlContent(dto.getUrlContent().split(ConstantDic.ERWEIMA)[0]);
      }else{
         dto.setType(3);
      }
      AppPersonInfoVO result = systemService.getPersonInfoByCode(request,dto.getUrlContent(), dto.getUserId(), dto.getType());
      return new ResultVO<AppPersonInfoVO>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("获取苏康码的内容")
   @ApiImplicitParams({@ApiImplicitParam(name = "url", value = "扫苏康码返回的url", dataType = "String")})
   @GetMapping("/getSuKangMaInfo")
   public ResultVO<AppPersonInfoVO> getSuKangMaInfo(String url){
      AppPersonInfoVO result = systemService.getSuKangMaInfo(url);
      return new ResultVO<AppPersonInfoVO>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("获取苏康码的内容")
   @ApiImplicitParams({@ApiImplicitParam(name = "idNum", value = "身份证", dataType = "String"),
           @ApiImplicitParam(name = "name", value = "姓名", dataType = "String")
   })

   @GetMapping("/getSkmInfoByIdNum")
   public ResultVO<String> getSkmInfoByIdNum(HttpServletRequest request, String idNum, String name){
      String result = systemService.getSkmInfoByIdNum(request,idNum, name);
      return new ResultVO<String>(SuccessEnums.QUERY_SUCCESS, result);
   }

   @ApiOperation("获取苏康码的内容并生成二维码")
   @ApiImplicitParams({@ApiImplicitParam(name = "idNum", value = "身份证", dataType = "String"),
           @ApiImplicitParam(name = "name", value = "姓名", dataType = "String"),
           @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String")
   })
   @GetMapping("/getSkmStatus")
   public ResultVO getSkmStatus(HttpServletRequest request,String idNum, String name,String mobile){
      long time_start = System.currentTimeMillis();
      String result = systemService.getSkmInfoByIdNum(request,idNum, name);
      //绿色
      Integer colour = 0;
      if(result.equals("green") || result.equals("未知")){
         colour = -16744448;
      }else if(result.equals("yellow")){
         colour = 16768605;
      }else if(result.equals("red")){
         colour = -65536;
      }
      //生成二维码返回base64
      String qrcodeType = "3";
      String str = systemService.outQrCode(null,qrcodeType,name, idNum, mobile, colour);
      long time_end = System.currentTimeMillis();
      log.info("getSkmStatus====="+(time_end-time_start)+"ms");
      log.info("getSkmStatus====={}",(time_end-time_start)+"ms");
      return new ResultVO<String>(SuccessEnums.QUERY_SUCCESS, str);
   }


   /**
    *
    * @param
    * @return
    */
   @ApiOperation("添加预检分诊信息并生成二维码")
   @GetMapping("/outQrCode")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "name", value = "姓名", dataType = "String"),
           @ApiImplicitParam(name = "idNum", value = "身份证号", dataType = "String"),
           @ApiImplicitParam(name = "mobile", value = "手机号", dataType = "String"),
           @ApiImplicitParam(name = "colour", value = "二维码十进制编码", dataType = "Integer")
   })
   public ResultVO<String> outQrCode(
           String name, String idNum, String mobile, Integer colour){
      String qrcodeType = "1";
      return new ResultVO<String>(SuccessEnums.QUERY_SUCCESS, systemService.outQrCode(null,qrcodeType,name,idNum,mobile,colour));
   }

   @ApiOperation("通过微信code查询openid")
   @GetMapping(value = "queryOpenId/{tempCode}")
   public ResultVO queryOpenId(@PathVariable String tempCode){
      return new ResultVO(SuccessEnums.QUERY_SUCCESS,systemService.queryOpenId(tempCode));
   }

}
