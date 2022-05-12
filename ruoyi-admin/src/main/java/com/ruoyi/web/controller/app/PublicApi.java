package com.ruoyi.web.controller.app;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.statics.ConstantDic;
import com.ruoyi.system.dto.AppPersonQueryByCodeDTO;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppPersonWxUpdateDTO;
import com.ruoyi.system.dto.SysScanLogDTO;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.AppPersonWxService;
import com.ruoyi.system.service.ISysScanLogService;
import com.ruoyi.system.service.PublicApiSystemService;
import com.ruoyi.system.vo.AppPersonInfoVO;
import com.ruoyi.system.vo.AppPersonWxPubVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import com.ruoyi.system.vo.PublicApiPersonInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:45
 */
@RestController
@Api(tags = {"苏康码或二维码解析"})
@RequestMapping("/pubicApi")
public class PublicApi {

    @Autowired
    private PublicApiSystemService systemService;

    @Autowired
    private ISysScanLogService sysScanLogService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private AppPersonWxService appPersonWxService;



    @ApiOperation("医生确认接口")
    @PostMapping("/confirmExpect")
    public ResultVO<Boolean> confirmExpect(@RequestBody JSONObject jsonObject) {
        if (StringUtils.isEmpty(jsonObject.getString("expectId"))){
            throw  new CustomException("请传入预检分诊信息id");
        }
        boolean flag = appPersonWxService.updateStatus(jsonObject.getString("expectId"));
       if (flag){
           return new ResultVO<Boolean>(SuccessEnums.UPDATE_SUCCESS,flag);
       }
        return new ResultVO<Boolean>(FailEnums.UPDATE_FAIL);
    }

    @GetMapping("/getExpectInformation")
    public ResultVO<AppPersonWxPubVO> getExpectInformation(String idNum){
        if (StringUtils.isEmpty(idNum)){
            throw new CustomException("请填写身份证号");
        }
        AppPersonWxPubVO result = appPersonWxService.getExpectInformation(idNum);
        if (ObjectUtil.isEmpty(result)){
            return new  ResultVO<AppPersonWxPubVO>(FailEnums.QUERY_FAIL);
        }
        return new  ResultVO<AppPersonWxPubVO>(SuccessEnums.QUERY_SUCCESS,result);
    }

    @GetMapping("/getSkmInfoByIdNum")
    public ResultVO<JSONObject> getSkmInfoByIdNum(String idNum, String name){
//        String result = systemService.getSkmInfoByIdNum(idNum, name);
        if(StringUtils.isBlank(name)){
            throw new CustomException("请填写姓名");
        }else if(StringUtils.isBlank(idNum)){
            throw new CustomException("请填写身份证号");
        }
        return new ResultVO<JSONObject>(SuccessEnums.QUERY_SUCCESS, systemService.getSkmInfoByIdNum(idNum, name));
    }

    @ApiOperation("通过身份证或者personId获取当天的预检分诊信息")
    @GetMapping("/getYjfzInfoByIdNum")
    public ResultVO<PublicApiPersonInfoVO> getYjfzInfoByIdNum(String idNum, String name){
        AppPersonWxQueryDTO queryDTO = new  AppPersonWxQueryDTO();
        if(StringUtils.isBlank(name)){
            throw new CustomException("请填写姓名");
        }else if(StringUtils.isBlank(idNum)){
            throw new CustomException("请填写身份证号");
        }
        queryDTO.setIdNum(idNum);
        queryDTO.setPersonName(name);
        AppPersonWxVO result = systemService.getPersonInfoForPublicApi(queryDTO);
        if(result!=null && result.getPersonId()!=null){
            PublicApiPersonInfoVO pubicResult  = new PublicApiPersonInfoVO();
            //二维码颜色
//            Integer colour = -16744448;//green
            pubicResult.setLevelData("1");//默认绿码
            boolean flag = false;
            String content = StringUtils.isNotEmpty(result.getContent())?result.getContent():"";
            String symptoms = StringUtils.isNotEmpty(result.getSymptoms())?result.getSymptoms():"0";
            Double number = 0d;
            try{
                number = Double.parseDouble(symptoms);
            }catch (Exception e){
                e.printStackTrace();
            }
            BigDecimal b1 = new BigDecimal(String.valueOf(number));
            BigDecimal b2 = new BigDecimal(String.valueOf(37.3));

            String contactHistory = StringUtils.isNotEmpty(result.getContactHistory())?result.getContactHistory():"";
            String epidemicHistory = StringUtils.isNotEmpty(result.getEpidemicHistory())?result.getEpidemicHistory():"";
            String riskPosition = StringUtils.isNotEmpty(result.getRiskPosition())?result.getRiskPosition():"";
            if(content.length()>1 || b1.subtract(b2).doubleValue()>0 || contactHistory.length()>0 ||
                    epidemicHistory.length()>0 || riskPosition.length()>0){
                flag = true;
            }
            if(flag){
                pubicResult.setLevelData("2");//黄码
//                colour = 16768605;//yellow
            }
            pubicResult.setName(name);
            pubicResult.setUserid(idNum);
            return new ResultVO<PublicApiPersonInfoVO>(SuccessEnums.QUERY_SUCCESS, pubicResult);
        }else{
            throw new CustomException("根据身份证号和项目未查询到预检分诊信息");
        }
    }

    /**
     *
     * @param dto
     * @return
     */
    @ApiOperation("根据苏康码或二维码的url解析获取人员基础信息")
    @PostMapping("/getPublicApiPersonInfoByCode")
    public ResultVO<PublicApiPersonInfoVO> getPublicApiPersonInfoByCode(@RequestBody AppPersonQueryByCodeDTO dto){
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
        JSONObject result = systemService.getPersonInfoByCode(dto.getUrlContent(), dto.getUserId(), dto.getType());
        PublicApiPersonInfoVO pubicResult  = new PublicApiPersonInfoVO();
        pubicResult.setName(result.getString("name"));
        pubicResult.setUserid(result.getString("userid"));
        pubicResult.setExceptionCodeReason(result.getString("exceptionCodeReason"));
        String levelData = result.getString("levelData");
        if(StringUtils.isBlank(levelData)){
            pubicResult.setLevelData("未知");
        }else if(levelData.equals("green")){
            pubicResult.setLevelData("1");
        }else if(levelData.equals("yellow")){
            pubicResult.setLevelData("2");
        }else if(levelData.equals("red")){
            pubicResult.setLevelData("3");
        }else if(levelData.equals("1")||levelData.equals("2")||levelData.equals("3")){
            pubicResult.setLevelData(levelData);
        }else{
            pubicResult.setLevelData("未知");
        }
        return new ResultVO<PublicApiPersonInfoVO>(SuccessEnums.QUERY_SUCCESS, pubicResult);
    }

    @ApiOperation("根据苏康码或二维码的url解析获取人员基础信息")
    @PostMapping("/saveScanLog")
    public AjaxResult saveScanLog(@RequestBody SysScanLogDTO dto){
        System.out.println("-----扫码获取的人员信息开始-----"+dto.toString());
        sysScanLogService.saveInfo(dto);
        return AjaxResult.success("保存成功");
    }

    @ApiOperation("根据苏康码或二维码的url解析获取人员基础信息")
    @GetMapping("/getAppVersion")
    public AjaxResult getAppVersion(){
        return AjaxResult.success("保存成功",sysUserMapper.getAppVersion());
    }

}
