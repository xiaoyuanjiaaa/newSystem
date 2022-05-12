package com.ruoyi.common.utils.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
public class HttpResponseUtil {


    /**
     * 生成post请求所需参数
     *
     * @param jsonMap
     * @return
     */
    public static HttpEntity<MultiValueMap<String,Object>> formatPostJson(MultiValueMap<String,Object> jsonMap, HttpHeaders httpHeaders) {
        return new HttpEntity<>(jsonMap, httpHeaders);
    }

    /**
     * 生成get请求uri
     *
     * @param uri
     * @param params
     * @return
     */
    public static String generateRequestParameters(String uri, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(uri);
        if (!params.isEmpty()) {
            sb.append("?");
            for (Map.Entry map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }


}
