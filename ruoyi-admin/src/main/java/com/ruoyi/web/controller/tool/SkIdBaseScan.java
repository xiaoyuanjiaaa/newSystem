package com.ruoyi.web.controller.tool;

import com.alibaba.fastjson.JSON;
import com.ruoyi.system.dto.ScanPostHistory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: wangguxiang
 * @Date: 2020-05-12 10:24
 */
public class SkIdBaseScan
{
    private static Logger logger = LoggerFactory.getLogger(SkIdBaseScan.class);
    public static final int TIME_OUT = 5000;
    public static final String Client_KEY = "clientKey";
    public static final String ReqData = "reqData";
    private String clientKey;
    private String reqData;
    private String serverUrl;
    private String header;
    private String auth;
    private String bodyKey;


    public SkIdBaseScan( String clientKey,String auth, String bodyKey, String reqData, String serverUrl, String header)
    {
        this.clientKey = clientKey;
        this.reqData = reqData;
        this.serverUrl = serverUrl;
        this.header = header;
        this.auth = auth;
        this.bodyKey = bodyKey;
    }

    /**
     * 设置通用参数
     */
    private void initParam(final Map<String, Object> param)
    {
        long time = System.currentTimeMillis() / 1000;
        param.put(Client_KEY, clientKey);
        param.put(ReqData, reqData);
        param.forEach((k, v) ->
        {
            if ( v == null )
            {
                param.put(k, "");
            }
        });
    }

    protected String post(String param)
    {
        //initParam(param);
        Map<String, String> headers = new HashMap<>();
        for ( String header : header.split(",") )
        {
            String headerArr[] = header.split("=");
            if ( headerArr.length == 2 )
            {
                headers.put(headerArr[0], headerArr[1]);
            }
        }
        headers.put("Content-Type", MediaType.TEXT_PLAIN_VALUE);
        headers.put("auth",auth);
        Connection conn = Jsoup.connect(serverUrl).timeout(TIME_OUT).ignoreContentType(true)
                .headers(headers).method(Connection.Method.POST)
                .maxBodySize(0)
                .requestBody(param);
        String result = null;
        ScanPostHistory his = new ScanPostHistory();
        his.setUrl(serverUrl);
        his.setParam(JSON.toJSONString(param));
        his.setHeaders(header);
        his.setCreateTime(LocalDateTime.now());
        try
        {
            Connection.Response response = conn.execute();

            if ( response.statusCode() == 200 )
            {
                result = response.body();
                logger.info(result);
            }
            logger.info("扫描身份证SUCCESS:{},参数:{},响应:{}", serverUrl, param, result);
            his.setResponse(result);
            his.setSuccess(1);
        } catch ( IOException e )
        {
            e.printStackTrace();
            logger.error("扫描身份证FAIL:", e);
            his.setResponse(e.getMessage());
            his.setSuccess(0);
        }
        return result;
    }
}
