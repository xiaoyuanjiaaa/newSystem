package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpResponseUtil;
import com.ruoyi.common.utils.statics.ConstantDic;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanSaveDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.entity.AppVisitPlan;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.utils.QRCodeUtils;
import com.ruoyi.system.vo.*;
import okhttp3.*;

import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
public class SystemServiceImpl implements SystemService
{
    private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private OkHttpClient client;

    @Autowired
    private SysUserMapper sysUserMapper;

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
    private SysUserMapper userMapper;

    @Value("${logoUrl}")
    private String logoUrl;


    private BASE64Encoder encoder = new BASE64Encoder();

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppPersonInfoVO getPersonInfoByCode(HttpServletRequest request,String urlContent, Long userId, Integer type) {
        log.info("扫码的url,{}", urlContent);
        AppPersonInfoVO result = new AppPersonInfoVO();
        //苏康码
        if(type==1){
            result = getPersonInfoBySkm(urlContent, userId);
        }else if(type==2){
            result = decoderQrCode(request,urlContent);
        }else{
            result = getPersonInfoByJobNum(urlContent);
        }
        //返回码的类型
        result.setType(type);

        //是否是打印的二维码
        if(StringUtils.isNotEmpty(result.getEndTime())){
            //1.获取上次的预检分诊信息
            AppPersonWx appPersonWx = new AppPersonWx();
            if(!ObjectUtil.isEmpty(result.getPersonId())){
                appPersonWx = appPersonWxService.getOne(new QueryWrapper<AppPersonWx>().eq("person_id",result.getPersonId()).orderByDesc("create_time").last("limit 1"));
            }
            if(appPersonWx!=null){
                result.setInspectionTriageStr(appPersonWx);
            }

            //每日填报
            result.setHealthReportStr(new AppHealthReportVO() );

            //2.每次扫码，判断是否当天有未关闭的访客计划
            //2.1当天没有访客计划或者当天没有未关闭的访客计划，则新增计划
            AppVisitPlanVO appVisitPlanVO = getAppVisitPlanVO(result.getPersonId());
            if(appVisitPlanVO!=null){
                result.setAppVisitPlanVO(appVisitPlanVO);
            }else{
                AppVisitPlan plan = appVisitPlanService.getOne(new QueryWrapper<AppVisitPlan>().eq("person_id",result.getPersonId()).orderByDesc("plan_id").last("limit 1"));
                if(plan==null){
                    throw new CustomException("没有历史访客计划");
                }else{
                    AppVisitPlanSaveDTO dto = new AppVisitPlanSaveDTO();
                    plan.setPlanId(null);
                    BeanUtils.copyProperties(plan, dto);
                    AppVisitPlanVO vo = appVisitPlanService.saveAppVisitPlan(dto);
                    result.setAppVisitPlanVO(vo);
                }
            }

            //用手机号查询判断是否是员工
            result.setFlag(changeIsUser(result.getMobile()));
        }else{
            //当天人员当天的预检分诊信息
            //苏康码根据身份证前4后4加姓名获取当天预检分诊信息
            String personName = result.getPersonName();
            String idNum = result.getIdNum();
            String[] strs = StringUtils.split(idNum,"*");
            String startStr = strs[0];
            String endStr = strs[strs.length-1];
            List<AppPerson> persons = appPersonService.list(new QueryWrapper<AppPerson>().eq("person_name",personName).likeRight("id_num",startStr).likeLeft("id_num",endStr));
            List<Long> personIds = persons.stream().map(AppPerson::getPersonId).collect(Collectors.toList());
            AppPersonWx appPersonWx = new AppPersonWx();
            if(!ObjectUtil.isEmpty(personIds)){
                appPersonWx = appPersonWxService.getInfoByPersonIds(personIds);
            }
            if(appPersonWx!=null){
                result.setInspectionTriageStr(appPersonWx);
            }

            //当天人员的当天健康填报信息
            //不能传person_id,因为员工的person_id和访客的person_id可能是不一样的
            String content = urlContent.replaceAll(" ","");
            SysUser sysUser = new SysUser();
            try {
                String personStr = new String(decoder.decodeBuffer(content));
                JSONObject obj = JSONObject.parseObject(personStr);
                String mobile = obj.get("mobile").toString();
                sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("phonenumber",mobile));
            }catch (Exception e){
                log.error(e.getMessage());
            }
//        AppHealthReportVO appHealthReportVO = appHealthReportService.getInfoByPersonId(result.getPersonId());
            if(sysUser != null){
                AppHealthReportVO appHealthReportVO = appHealthReportService.getInfoByPersonId(sysUser.getPersonId());
                if(appHealthReportVO!=null){
                    result.setHealthReportStr(appHealthReportVO);
                }
            }else{
                result.setHealthReportStr(new AppHealthReportVO() );
            }

            //当天是否有未关闭的访客计划
            AppVisitPlanVO appVisitPlanVO = getAppVisitPlanVO(result.getPersonId());
            if(appVisitPlanVO!=null){
                result.setAppVisitPlanVO(appVisitPlanVO);
            }

            //用手机号查询判断是否是员工
            result.setFlag(changeIsUser(result.getMobile()));
        }
        return result;
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
     * 获取苏康码里面的信息
     * @param url
     * @return
     */
    @Override
    public AppPersonInfoVO getSuKangMaInfo(String url) {
        String qrCode = getQrcode(url);
        if(CheckUtil.NullOrEmpty(qrCode)){
            throw new CustomException("苏康码解析失败,解析不到qrCode");
        }
        String result = getWeiJianWeiApi(url,"admin","15266778899");
        AppPersonInfoVO po = getPersonInfo(result, qrCode);
        return po;
    }

    /**
     * 根据员工号获取员信息
     * @param jobNum
     * @return
     */
    private AppPersonInfoVO getPersonInfoByJobNum(String jobNum) {
        AppPersonInfoVO info = sysUserMapper.getInfoByJobNum(jobNum);
        return info;
    }

    private AppPersonInfoVO getPersonInfoBySkm(String url, Long userId){
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
        AppPersonInfoVO po = getPersonInfo(result, qrCode);
        return po;
    }

    /**
     * 人员基本信息生成二维码
     * @param name
     * @param idNum
     * @param mobile
     * @return
     * Integer colour = temperature > 37.3 ? 16777011: 0;
     */
    @Override
    public String   outQrCode(String endTime,String qrcodeType,String name, String idNum, String mobile, Integer colour)  {
        String infoEncode = getPersonInfoObj(endTime,qrcodeType,name, idNum, mobile);
        //此处调用生成二维码的接口
        log.info(name+"===二维码的接口开始==="+ DateUtils.getTime());
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(infoEncode, BarcodeFormat.QR_CODE, 1000, 1000, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BufferedImage image = toBufferedImage(bitMatrix, colour,qrcodeType);
        String base64QrCode = null;
        try {
            //输出流
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            base64QrCode = Base64.encode(stream.toByteArray());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("生成的二维码转成base64异常");
        }
        log.info(name+"===二维码的接口结束==="+ DateUtils.getTime());
        return base64QrCode;
    }

    /**
     * 解密二维码里面的信息
     * @param content
     * @return
     */
    @Override
    public AppPersonInfoVO decoderQrCode(HttpServletRequest request,String content) {
        AppPersonInfoVO info = new AppPersonInfoVO();
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
            String mobile = obj.containsKey("mobile")?obj.get("mobile").toString():"";
            String idNum = null;
            String color = "未知";
            if(obj.get("idNum")!=null && !"".equals(obj.get("idNum"))){
                idNum = obj.get("idNum").toString();
                if(env.equals("prod")){
                    color = getSkmInfoByIdNum(request,idNum, name);
                }else{
                    color = "green";
                }
            }
            //新增qcodeType判断是预检分诊还是每日填报
            String qcodeType = "";
            if(obj.get("type")!=null && !"".equals(obj.get("type"))){
                qcodeType = obj.get("type").toString();
            }
            String personId = obj.get("personId").toString();
            info.setPersonName(name).setMobile(mobile).setIdNum(idNum).setQrcodeColor(color)
                    .setPersonId(CheckUtil.strParseLong(personId)).setQcodeType(qcodeType);
            System.out.println("---------二维码获取用户信息"+info);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 通过姓名和身份证获取用户苏康码信息
     * @param idNum
     * @param name
     * @return
     */
    @Override
    public String getSkmInfoByIdNum(HttpServletRequest request, String idNum, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("idNum",idNum);
        map.put("name",name);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", request.getHeader("Authorization"));
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(HttpResponseUtil.generateRequestParameters("http://223.112.133.206:38008/pubicApi/getSkmInfoByIdNumSys" ,map), HttpMethod.GET, request1, String.class);
        Map<String,Object> mapType = JSON.parseObject(response.getBody(),Map.class);
        log.error(response.getBody());
        if(mapType.containsKey("data")){
            return mapType.get("data").toString();
        }else{
            try {
                log.error("message:"+response.getBody());
                throw new CustomException(mapType.get("message").toString());
            }catch (Exception e){
                log.error(e.getMessage());
                log.error("msg:"+response.getBody());
                throw new CustomException(mapType.get("msg").toString());
            }
        }
    }


//    private String getWyQrCode(String bodyStr, String oauth){
//        Map<String, Object> map = new HashMap<>();
//        map.put("idNum",idNum);
//        map.put("name",name);
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Authorization", request.getHeader("Authorization"));
//        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
//        ResponseEntity<String> response = restTemplate
//                .exchange(HttpResponseUtil.generateRequestParameters("http://223.112.133.206:38008/pubicApi/getSkmInfoByIdNumSys" ,map), HttpMethod.GET, request1, String.class);
//        Map<String,Object> mapType = JSON.parseObject(response.getBody(),Map.class);
//
//        if(mapType.containsKey("data")){
//            return mapType.get("data").toString();
//        }else{
//            throw new CustomException("苏康码"+mapType.get("message").toString());
//        }
//    }

    @Override
    public String queryOpenId(String tempCode) {
        WeiXinVo weiXinVo = null;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+tempCode+"&grant_type=authorization_code";
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK){
                weiXinVo = JSONObject.parseObject(responseEntity.getBody(), WeiXinVo.class);
                System.out.println(weiXinVo);
            }
            if(weiXinVo!=null){
                if(StringUtils.isNotEmpty(weiXinVo.getSession_key())){
                    return weiXinVo.getOpenid();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    /**
     * 人员基本信息生成对象并机密返回
     * @param name
     * @param idNum
     * @param mobile
     * @return
     */
    private String getPersonInfoObj(String endTime,String qrcodeType,String name, String idNum, String mobile){
        JSONObject bj = new JSONObject();
        log.info(name+"===getPersonInfo开始==="+ DateUtils.getTime());
        Long personId = appPersonService.getPersonInfo(null,idNum, name, mobile, null, null);
        log.info(name+"===getPersonInfo结束==="+ DateUtils.getTime());
        bj.put("name",name);
        bj.put("idNum",idNum);
        bj.put("mobile",mobile);
        bj.put("personId",personId.toString());
        bj.put("type",qrcodeType);
        if(StringUtils.isNotEmpty(endTime)){
            bj.put("endTime",endTime);
        }
        String jsonData = encoder.encodeBuffer(bj.toString().getBytes());
        return jsonData+ConstantDic.ERWEIMA;
    }

    private AppPersonInfoVO getPersonInfo(String result, String qrCode){
        AppPersonInfoVO po = new AppPersonInfoVO();
        JSONObject o1 = JSONObject.parseObject(result);
        String resCode = o1.get("resCode").toString();
        if ( resCode.equals("0") ) {
            redisTemplate.opsForValue().set(qrCode, result, 14, TimeUnit.DAYS);
            JSONObject info = JSONObject.parseObject(o1.get("res").toString());
            String name = info.getString("name");
            String mobile = info.getString("phone");
            String idNum = info.getString("idcardNo");
            String level = info.getString("level");
            po.setMobile(mobile).setPersonName(name).setQrcodeColor(level).setIdNum(idNum).setQrcode(qrCode);
            po.setPersonId(appPersonService.getPersonInfo(null,idNum, name, mobile, qrCode, level));
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
        return po;
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
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//        params.add("url",url);
//        params.add("source",source);
//        params.add("position",position);
//        params.add("verifierDept",verifierDept);
//        params.add("verifierName",verifierName);
//        params.add("verifierPhone",verifierPhone);
//        params.add("token",token);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//        ResponseEntity<String> response  = restTemplate.exchange(wjwUrl, HttpMethod.POST,requestEntity,String.class);
//        System.out.println(response.getBody());
//        return response.getBody();
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
        ResponseEntity<String> response  = restTemplate.exchange("http://223.112.133.206:38008/pubicApi/getWeiJianWeiApi", HttpMethod.POST,requestEntity,String.class);
        System.out.println(response.getBody());
        Map<String,Object> mapType = JSON.parseObject(response.getBody(),Map.class);
        return mapType.get("data").toString();
    }

//    private  void writeToFile(BitMatrix matrix, String format, File file)
//
//            throws IOException {
//
//        BufferedImage image = toBufferedImage(matrix);
//
//        if (!ImageIO.write(image, format, file)) {
//
//            throw new IOException("Could not write an image of format "
//
//                    + format + " to " + file);
//
//        }
//
//    }

    private  BufferedImage toBufferedImage(BitMatrix matrix, Integer colour,String qrcodeType) {

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height,

                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                image.setRGB(x, y, matrix.get(x, y) ? colour : 0xFFFFFFFF);

            }

        }
        // 插入图片
        try {
            if("1".equals(qrcodeType)){
                QRCodeUtils.insertImage(image, logoUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;

    }



}
