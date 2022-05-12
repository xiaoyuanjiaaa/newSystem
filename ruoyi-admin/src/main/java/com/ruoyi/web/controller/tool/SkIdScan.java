package com.ruoyi.web.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.dto.SkHealthInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @Description:
 * @Author: wangguxiang
 * @Date: 2020-05-11 10:34
 */
public class SkIdScan extends SkIdBaseScan
{
    private static final String TOKEN = "token";

    private String token;

    private String userName;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String body;

    private String auth;

    private String skBodyKey;


    public SkIdScan(String token, String userName,String body,String auth,String bodyKey, String clientKey, String reqData, String healthScanUrl, String scanHeader)
    {
        super(clientKey, auth,bodyKey, reqData, healthScanUrl, scanHeader);
        this.token = token;
        this.userName = userName;
        this.body = body;
        this.auth = auth;
        this.skBodyKey = bodyKey;
    }

    public ResultVO<SkHealthInfo> excute(Consumer<SkHealthInfo> a)
    {
        String result = post(body);
        if ( StringUtils.isNotEmpty(result) )
        {
            JSONObject resultObj = JSONObject.parseObject(result);
            SkHealthInfo info = new SkHealthInfo();

            String bodyDec2 = "";
            JSONObject jsonObject = null;
            int code = 0;
            if ( resultObj.containsKey("code") )
            {
                code = resultObj.getIntValue("code");
            }
            if(resultObj.containsKey("data")){
                bodyDec2 =  AESSymmtricEncrypt.decrypt(resultObj.getString("data"),skBodyKey);
                jsonObject = JSONObject.parseObject(bodyDec2);
            }

            if ( code == 200 && jsonObject != null)
            {
                if ( resultObj.containsKey("data"))
                {


                    if (jsonObject != null && jsonObject.containsKey("userid") )
                    {
                        info.setIdcardNo(jsonObject.getString("userid"));
                    }
                    if (jsonObject != null && jsonObject.containsKey("name") )
                    {
                        info.setName(jsonObject.getString("name"));
                    }
                    if (jsonObject != null && jsonObject.containsKey("levelData") )
                    {
                        info.setLevel(jsonObject.getString("levelData"));
                    }
                    logger.info("解密完成");
                }
                a.accept(info);
                logger.info("身份证：成功！");
                return new ResultVO<>(SuccessEnums.QUERY_SUCCESS,info);
            } else
            {
                StringBuilder builder = new StringBuilder();
                if ( resultObj.containsKey("data") )
                {
                    builder.append(resultObj.getString("data"));
                }
                logger.info("身份证：失败！");
                return new ResultVO<>(FailEnums.APPEVENTRECORD_QUERY_FAIL,"身份证：" + builder.toString().replace("null", ""));
            }
        }
        return new ResultVO<>(FailEnums.APPEVENTRECORD_QUERY_FAIL,"身份证：扫码失败.");
    }


}
