package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.AESUtils;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpResponseUtil;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanSaveDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.entity.AppVisitPlan;
import com.ruoyi.system.mapper.AppPersonWxMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.*;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class PublicApiSystemServiceImpl implements PublicApiSystemService
{
    private static final Logger log = LoggerFactory.getLogger(PublicApiSystemServiceImpl.class);

    @Autowired
    private OkHttpClient client;

    @Autowired
    private  SysUserMapper sysUserMapper;

    @Autowired
    private AppPersonService appPersonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AppPersonWxService appPersonWxService;

    @Autowired
    private IAppHealthReportService appHealthReportService;

    @Autowired
    private IAppVisitPlanService appVisitPlanService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private  SysUserMapper userMapper;


    private BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 卫健委接口相关字段
     * 卫健委api地址
     * 来源
     * 位置信息
     * 核验人部门
     * 核验人信息
     * 核验人联系方式
     * token
     */
    @Value("${wjw.url}")
    private String wjwUrl;
    @Value("${wjw.source}")
    private String source;
    @Value("${wjw.position}")
    private String position;
    @Value("${wjw.verifierDept}")
    private String verifierDept;
    @Value("${wjw.token}")
    private String token;

    private String erWeiMa = "ErWeiMa";

    /**
     * 五院接口相关
     */
    @Value("${wy.skIdService}")
    private String skIdService;

    @Value("${wy.skAuth}")
    private String skAuth;

    @Value("${wy.skAuthKey}")
    private String skAuthKey;

    @Value("${wy.skBodyKey}")
    private String skBodyKey;

    @Value("${wy.skOrganizeLocation}")
    private String skOrganizeLocation;

    @Value("${wy.skApplyType}")
    private String skApplyType;

    /**
     * 微信相关
     */
    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${spring.profiles.active}")
    private String env;

    @Resource
    private AppPersonWxMapper appPersonWxMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getPersonInfoByCode(String urlContent, Long userId, Integer type) {
        log.info("扫码的url,{}", urlContent);
        JSONObject json = new JSONObject();
        //苏康码
        if(type==1){
            json = getPersonInfoBySkm(urlContent, userId);
        }else if(type==2){
            json = decoderQrCode(urlContent);
        }
        return json;
    }

    @Override
    public AppPersonWxPubVO getExpectInformation(String idNum) {
        AppPersonWxQueryDTO queryDTO = new  AppPersonWxQueryDTO();
        queryDTO.setIdNum(idNum);
        AppPersonWxVO result = getPersonInfoForPublicApi(queryDTO);
        if(result!=null && result.getPersonId()!=null){
            AppPersonWxPubVO appPersonVO = new AppPersonWxPubVO();
             BeanUtils.copyProperties(result,appPersonVO);
            //二维码颜色
//            Integer colour = -16744448;//green
            appPersonVO.setFlag("false");//默认绿码
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
                appPersonVO.setFlag("true");//黄码
//                colour = 16768605;//yellow
            }
            return appPersonVO;
        }else{
            throw new CustomException("根据身份证号和项目未查询到预检分诊信息");
        }
    }

    private Boolean changeIsUser(String mobile){
        boolean flag = false;
        SysUser info = userMapper.checkPhoneUnique(mobile);
        if(StringUtils.isNotNull(info)){
            flag = true;
        }
        return flag;
    }

    private AppVisitPlanVO getAppVisitPlanVO(Long personId){
        AppVisitPlanQueryDTO dto = new AppVisitPlanQueryDTO();
        dto.setFlag(true).setIsClose(0).setPersonId(personId);
        return appVisitPlanService.getAppVisitPlan(dto);
    }

    /**
     * 根据员工号获取员信息
     * @param jobNum
     * @return
     */
    private AppPersonInfoVO getPersonInfoByJobNum(String jobNum) {
        AppPersonInfoVO info = sysUserMapper.getInfoByJobNum(jobNum);
        JSONObject json = new JSONObject();
        json.put("xcValue","当日未核验");
        json.put("userid",info.getIdNum());
        json.put("levelData","4");
        json.put("name",info.getPersonName());
        json.put("exceptionCodeReason","");
        json.put("type","3");

        return info;
    }

    private JSONObject getPersonInfoBySkm(String url, Long userId){
        String result = null;
        // 先截取url中的qrcode,若redis中有则缓存数据则去缓存中心，若匹配不上调取卫健委的接口获取新的数据然后放入缓存中
        String qrCode = getQrcode(url);
        if(CheckUtil.NullOrEmpty(qrCode)){
            throw new CustomException("苏康码解析失败,解析不到qrCode");
        }
        //判断key是否存在
        if(redisTemplate.hasKey(qrCode)){
            result = redisTemplate.opsForValue().get(qrCode)+"".trim();
        }else{
            // 此处先通过url调取卫健委的接口获取这个人的信息以及码的实际颜色
            SysUser user = sysUserMapper.selectUserById(userId);
            result = getWeiJianWeiApi(url,user.getUserName(),user.getPhonenumber());
        }
        JSONObject po = getPersonInfo(result, qrCode);
        return po;
    }

    private String getWyQrCode(String bodyStr, String oauth){
        Map<String, Object> map = new HashMap<>();
        map.put("bodyStr", bodyStr);
        map.put("oauth", oauth);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(HttpResponseUtil.generateRequestParameters(skIdService ,map), HttpMethod.GET, request1, String.class);
        return response.getBody();
    }


    private JSONObject getPersonInfo(String result, String qrCode){
        JSONObject o1 = JSONObject.parseObject(result);
        JSONObject json = new JSONObject();
        String resCode = o1.get("resCode").toString();
        if ( resCode.equals("0") ) {
            redisTemplate.opsForValue().set(qrCode, result, 14, TimeUnit.DAYS);
            JSONObject info = JSONObject.parseObject(o1.get("res").toString());
            String name = info.getString("name");
            String mobile = info.getString("phone");
            String idNum = info.getString("idcardNo");
            String level = info.getString("level");
            json.put("xcValue","当日未核验");
            json.put("userid",idNum);
            json.put("levelData",level);
            json.put("name",name);
            json.put("exceptionCodeReason","请填写身份证号");
            json.put("type",1);
        }else{
            if(resCode.equals("1")){
                throw new CustomException("请求失败，其他异常");
            }else if(resCode.equals("111")){
                throw new CustomException("健康码二维码已失效");
            }else if(resCode.equals("112")){
                throw new CustomException("健康码不匹配，不是苏康码");
            }else if(resCode.equals("116")){
                throw new CustomException("没有访问权限");
            }else if(resCode.equals("118")){
                throw new CustomException("没有访问权限");
            }else if(resCode.equals("119")){
                throw new CustomException("如需使用扫码功能，请先更新信息");
            }else{
                throw new CustomException("请求失败，其他异常");
            }
        }
        return json;
    }

    private String getQrcode(String url){
        if(url.contains("qrCode=")){
            String qrCode = url.split("qrCode=")[1].split("&")[0];
            return qrCode;
        }
        return null;
    }

    /**
     * 卫健委解析苏康码的url
     * @param url
     * @param verifierName
     * @return
     */
    private String getWeiJianWeiApi(String url, String verifierName, String verifierPhone){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("url",url);
        params.add("source",source);
        params.add("position",position);
        params.add("verifierDept",verifierDept);
        params.add("verifierName",verifierName);
        params.add("verifierPhone",verifierPhone);
        params.add("token",token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> response  = restTemplate.exchange(wjwUrl, HttpMethod.POST,requestEntity,String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }


    public JSONObject decoderQrCode(String content) {
        AppPersonInfoVO info = new AppPersonInfoVO();
        JSONObject json = new JSONObject();
        content = content.replaceAll(" ","");
        try {
            String personStr = new String(decoder.decodeBuffer(content));
            JSONObject obj = JSONObject.parseObject(personStr);
            if(obj.containsKey("endTime")){
                String endTime = obj.get("endTime").toString();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date et = sdf.parse(endTime);
                Date now = new Date();
                if (et.before(now)){
                    throw new CustomException("二维码失效，有效期截止"+endTime);
                }
                info.setEndTime(endTime);
            }
            String name = obj.get("name").toString();
            String idNum = null;
            if(obj.get("idNum")!=null && !"".equals(obj.get("idNum"))){
                idNum = obj.get("idNum").toString();
                return  getSkmInfoByIdNum(idNum, name);
            }
            json.put("xcValue","当日未核验");
            json.put("userid",idNum);
            json.put("levelData","4");
            json.put("name",name);
            json.put("exceptionCodeReason","");
            json.put("type","2");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 通过姓名和身份证获取用户苏康码信息
     * @param idNum
     * @param name
     * @return
     */
    @Override
    public JSONObject getSkmInfoByIdNum(String idNum, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("idNum",idNum);
        map.put("name",name);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(HttpResponseUtil.generateRequestParameters(skIdService ,map), HttpMethod.GET, request1, String.class);
        return JSONObject.parseObject(response.getBody());
    }


    /**
     * 获取用户当天的预检分诊信息
     * @param queryDTO
     * @return
     */
    @Override
    public AppPersonWxVO getPersonInfoForPublicApi(AppPersonWxQueryDTO queryDTO) {
        Long personId =appPersonService.getPersonInfoForPublicApi(queryDTO.getIdNum(), queryDTO.getPersonName());
        queryDTO.setPersonId(personId).setIdNum(null);
        return appPersonWxMapper.getInfo(queryDTO);
    }


}
