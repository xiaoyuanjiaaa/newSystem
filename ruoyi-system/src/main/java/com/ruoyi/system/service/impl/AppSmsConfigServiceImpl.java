package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.RemindObjectEnums;
import com.ruoyi.common.enums.RemindTypeEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.statics.ConstantDic;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.SmsOracleConfigDTO;
import com.ruoyi.system.dto.ZhSmsDTO;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.entity.AppSmsSendLog;
import com.ruoyi.system.entity.SmsConfig;
import com.ruoyi.system.mapper.AppSmsConfigMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.SmsConfigListVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.axis.wsdl.symbolTable.SchemaUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:11
 */
@Slf4j
@Service
public class AppSmsConfigServiceImpl extends ServiceImpl<AppSmsConfigMapper, AppSmsConfig> implements IAppSmsConfigService {

   @Autowired
   private RedisTemplate redisTemplate;

   @Autowired
   private IAppSmsSendLogService smsSendLogService;


   @Autowired
   private OkHttpClient client;

   @Autowired
   private ISmsConfigService smsConfigService;

   @Autowired
   private IAppSmsConfigService appSmsConfigService;

   @Autowired
   private ISysDeptService sysDeptService;
   @Autowired
   private IAppHealthReportService healthReportService;
   @Autowired
   private ISysConfigService sysConfigService;
   @Value("${smsZh.sms.url}")
   private String sendMessageUrl;

   @Value("${smsZh.sms.uid}")
   private String uId;

   @Value("${smsZh.sms.userPwd}")
   private String userPwd;

   @Value("${smsZh.sms.ext}")
   private String ext;

   @Value("${smsZh.sms.message}")
   private String selfMessage;


   private String message="【无锡二院】您的验证码：";

   @Override
   public ResultVO sendMessage(String phone) {
      String smsCode = RandomUtil.randomNumbers(6);
      String requestBody = getMessageInfoObj(phone, message+smsCode);
      String result = sendSms(requestBody);
      if(!CheckUtil.NullOrEmpty(result)){
         log.info("phone={},发送短信验证码，返回结果:{}", phone, result);
         JSONObject resultObj = JSONObject.parseObject(result);
         String resultCode = resultObj.get("resultcode").toString();
         String resultMsg = resultObj.get("resultmsg").toString();
         if (resultCode.equals("0")) {
            String key = ConstantDic.SMS_REGISTER_PREFIX + phone;
            redisTemplate.opsForValue().set(key, smsCode, 5, TimeUnit.MINUTES);
            addAppSmsSendLog(phone, message+smsCode, CheckUtil.strParseLong(resultCode), resultMsg);
            return new ResultVO(SuccessEnums.SMS_SEND_SUCCESS, resultMsg);
         } else {
            addAppSmsSendLog(phone, message+smsCode, CheckUtil.strParseLong(resultCode), resultMsg);
            throw new CustomException(resultMsg);
         }
      }
      return new ResultVO(FailEnums.SMS_SEND_RECORD, null);
   }

   @Override
   public void sendMessage(String prefix,String phone,String type) {
      SmsConfig smsConfig = smsConfigService.getOne(new QueryWrapper<SmsConfig>().eq("type",type));
      if(ObjectUtil.isEmpty(smsConfig)){
         return;
      }
      String messages = "【无锡二院】"+prefix.concat(",").concat(smsConfig.getMessage());
      String requestBody = getMessageInfoObj(phone, messages);
      String result = sendSms(requestBody);
      if(!CheckUtil.NullOrEmpty(result)){
         JSONObject resultObj = JSONObject.parseObject(result);
         String resultCode = resultObj.get("resultcode").toString();
         String resultMsg = resultObj.get("resultmsg").toString();
         addAppSmsSendLog(phone, messages, CheckUtil.strParseLong(resultCode), resultMsg);
      }
   }


   private void addAppSmsSendLog(String mobile, String smsContent, Long smsStatus, String smsErrMsg){
      AppSmsSendLog po = new AppSmsSendLog();
      po.setSmsContent(smsContent).setSmsMobile(mobile).setSmsTime(new Date()).setSmsStatus(smsStatus).setSmsErrMsg(smsErrMsg);
      smsSendLogService.save(po);
   }

   @Override
   public void noticeReportBySms(ZhSmsDTO zhSmsDTO){
      //插入到smslog里面
      AppSmsSendLog appSmsSendLog = new AppSmsSendLog();
      String result = sendSms(JSON.toJSONString(zhSmsDTO));
      appSmsSendLog.setSmsContent(zhSmsDTO.getMessage());
      appSmsSendLog.setSmsMobile(zhSmsDTO.getMobile());
      appSmsSendLog.setSmsTime(new Date(System.currentTimeMillis()));
      if (StringUtils.isNotBlank(result)){
         JSONObject jsonObject = JSONObject.parseObject(result);
         if (!"0".equals(jsonObject.getString("resultcode"))){
            appSmsSendLog.setSmsErrMsg(jsonObject.getString("resultmsg"));
         }
      }
      smsSendLogService.save(appSmsSendLog);
   }



   private String sendSms(String bodyStr){
      MediaType mediaType = MediaType.parse("application/json");
      RequestBody body = RequestBody.create(mediaType, bodyStr);
      Request request = new Request.Builder()
              .url(sendMessageUrl)
              .method("POST", body)
              .addHeader("Content-Type", "application/json")
              .build();
      String response = null;
      try {
         response = client.newCall(request).execute().body().string();
         log.info("****************************sendMessageUrl:" + sendMessageUrl);
         log.info("****************************response:" + response);
      } catch (IOException e) {
         log.info("====================================sendMessageUrl:" + sendMessageUrl);
         e.printStackTrace();
         throw new CustomException("调用中国移动短信返回数据异常");
      }
      return response;
   }




   private String getMessageInfoObj(String mobile, String Params){
      JSONObject bj = new JSONObject();
      bj.put("uid",uId);
      bj.put("userpwd",userPwd);
      bj.put("mobile",mobile);
      bj.put("message",Params);
      bj.put("ext","111");
      return bj.toString();
   }

   @Override
   public ResultVO addSmsConfig(AppSmsConfig appSmsConfig){
      LambdaQueryWrapper<AppSmsConfig> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(AppSmsConfig::getSmsTime,appSmsConfig.getSmsTime());
      List<AppSmsConfig> result=appSmsConfigService.list(queryWrapper);
      if(ObjectUtil.isNotEmpty(result)){
        return new ResultVO<>(FailEnums.REPEAT_TIME_ERROR);
      }
      appSmsConfigService.save(appSmsConfig);
      return new ResultVO<>(SuccessEnums.SAVE_SUCCESS,null);
   }

   @Override
   public TableDataInfo smsConfigList(PageDomain domain) {
      QueryWrapper<AppSmsConfig> queryWrapper = new QueryWrapper<>();
      queryWrapper.orderByAsc("sms_time");
      Page<AppSmsConfig> page = this.page(new Page<>(domain.getPageNum(), domain.getPageSize()), queryWrapper);
      List<SmsConfigListVO> list = new ArrayList<>();
      if(page.getRecords().size()>0){
         for(AppSmsConfig appSmsConfig:page.getRecords()){
            SmsConfigListVO smsConfigListVO = new SmsConfigListVO();
            BeanUtils.copyProperties(appSmsConfig,smsConfigListVO);
            smsConfigListVO.setRemindObject(RemindObjectEnums.getMsg(appSmsConfig.getReminder()));
            smsConfigListVO.setRemindType(RemindTypeEnums.getMsg(appSmsConfig.getType()));
            list.add(smsConfigListVO);
         }
         return new TableDataInfo(list,(int)page.getTotal());
      }
      return new TableDataInfo(null,0);
   }

   @Override
   public void sendSelfPhone() {
      AppHealthReportCountDTO appHealthReportCountDTO = new AppHealthReportCountDTO();
      appHealthReportCountDTO.setCurrentDay(LocalDate.now());
      appHealthReportCountDTO.setPageSize(10000);
      PageInfo<SysUser> userPageInfo =  healthReportService.pageSysUser(appHealthReportCountDTO);
      if (CollectionUtils.isNotEmpty(userPageInfo.getList())){
      StringBuffer mobile = new StringBuffer();
      for (SysUser sysUser : userPageInfo.getList()) {
         if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(sysUser.getPhonenumber())){
            mobile.append(sysUser.getPhonenumber());
            mobile.append(",");
         }
      }
      mobile.deleteCharAt(mobile.length()-1);
      log.info("手机号：++++++++++++++"+mobile);
      log.info("短信内容：++++++++++++++"+selfMessage);
         //发送短信
         SmsOracleConfigDTO dto = new SmsOracleConfigDTO();
         dto.setCreateDate(new Timestamp(DateUtils.getCurrentTimeStamp()));
         dto.setMobile(mobile.toString());
         dto.setMessage(selfMessage);
         log.info("发送短信参数：++++++++++++++"+dto.toString());
         //发送oracle
         sysConfigService.insertSms(dto);
         //发送短信日志
         appSmsConfigService.sendSmsLog(dto.getMessage(),dto.getMobile());
      }
   }

   @Override
   public void sendLeaderPhone(Boolean is) {
      AppHealthReportCountDTO appHealthReportCountDTO = new AppHealthReportCountDTO();
      appHealthReportCountDTO.setCurrentDay(LocalDate.now());
      appHealthReportCountDTO.setPageSize(10000);
      PageInfo<SysUser> userPageInfo =  healthReportService.pageSysUser(appHealthReportCountDTO);
      if(ObjectUtil.isNotEmpty(userPageInfo.getList())) {
         Map<Long, List<String>> map = userPageInfo.getList().stream().collect(Collectors.groupingBy(SysUser::getDeptId, Collectors.mapping(SysUser::getNickName, Collectors.toList())));

         List<Long> deptIds = userPageInfo.getList().stream().map(SysUser::getDeptId).distinct().collect(Collectors.toList());
         List<SysDept> deptList = sysDeptService.list(new LambdaQueryWrapper<SysDept>().in(SysDept::getDeptId, deptIds));
         for (SysDept dept : deptList) {
           List<String> list= map.get(dept.getDeptId());
           if(is&&ObjectUtil.isNotEmpty(list)&&list.contains(dept.getLeader())){
              list.remove(dept.getLeader());
           }
            String str=StringUtils.join(list, ",");
           String message="【无锡二院】"+str+"今日的健康信息还未上报，请及时填写";
            log.info("短信内容：++++++++++++++"+message);
            log.info("手机号：++++++++++++++"+dept.getPhone());
            //发送短信
            SmsOracleConfigDTO dto = new SmsOracleConfigDTO();
            dto.setCreateDate(new Timestamp(DateUtils.getCurrentTimeStamp()));
            dto.setMobile(dept.getPhone());
            dto.setMessage(message);
            log.info("发送短信参数：++++++++++++++"+dto.toString());
            sysConfigService.insertSms(dto);
            appSmsConfigService.sendSmsLog(dto.getMessage(),dto.getMobile());
         }
      }
   }

   @Override
   public String getAppointPhone(String appointUser,Boolean is) {
      if(ObjectUtil.isNull(appointUser)){
         return null;
      }
      Map<String, String> map = JSONObject.parseObject(appointUser, new TypeReference<Map<String, String>>() {});
      if(is){map.keySet().removeIf(key -> key.equals(SecurityUtils.getUsername()));}
      StringBuffer mobile = new StringBuffer();
      for (Map.Entry<String, String> entry : map.entrySet()) {
         mobile.append(entry.getValue());
         mobile.append(",");
      }
      mobile.deleteCharAt(mobile.length()-1);
      log.info("手机号：++++++++++++++"+mobile);
      return mobile.toString();
   }

   @Override
   public void sendFillPhone(String appointUser, Boolean is) {
      if (ObjectUtil.isNotNull(appointUser)){
         AppHealthReportCountDTO appHealthReportCountDTO = new AppHealthReportCountDTO();
         appHealthReportCountDTO.setCurrentDay(LocalDate.now());
         appHealthReportCountDTO.setPageSize(10000);
         PageInfo<SysUser> userPageInfo =  healthReportService.pageSysUser(appHealthReportCountDTO);
         log.info("未填报列表：++++++++++++++"+userPageInfo.getList());
         String str="";
         Map<String, String> map = JSONObject.parseObject(appointUser, new TypeReference<Map<String, String>>() {
         });
         //如果有本人发送  指定人员发送需要删除本人
         if(ObjectUtil.isNotEmpty(userPageInfo.getList())){
            List<String> nameList=userPageInfo.getList().stream().map(SysUser::getNickName).collect(Collectors.toList());
            if (is) {
               for(String name:nameList){
                  for (Map.Entry<String, String> element : map.entrySet()) {
                     if(name.equals(element.getValue())){
                        map.remove(element.getKey());
                     }
                  }
               }
            }
            str = StringUtils.join(nameList, ",");
         }
         for (Map.Entry<String, String> entry : map.entrySet()) {
            String message="【无锡二院】"+str+"今日的健康信息还未上报，请及时填写";
            log.info("短信内容：++++++++++++++"+message);
            //发送短信
            SmsOracleConfigDTO dto = new SmsOracleConfigDTO();
            dto.setCreateDate(new Timestamp(DateUtils.getCurrentTimeStamp()));
            dto.setMobile(entry.getValue());
            dto.setMessage(message);
            log.info("发送短信参数：++++++++++++++"+dto.toString());
            sysConfigService.insertSms(dto);
            //写入短信日志
            appSmsConfigService.sendSmsLog(dto.getMessage(),dto.getMobile());
         }

      }
   }

   @Override
   public void sendSmsLog(String message,String mobile) {
      AppSmsSendLog appSmsSendLog=new AppSmsSendLog();
      appSmsSendLog.setSmsContent(message);
      appSmsSendLog.setSmsMobile(mobile);
      appSmsSendLog.setSmsTime(new Date(System.currentTimeMillis()));
      smsSendLogService.save(appSmsSendLog);
   }
}
