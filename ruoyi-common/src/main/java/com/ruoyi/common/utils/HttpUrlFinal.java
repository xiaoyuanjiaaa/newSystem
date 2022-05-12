package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.http.HttpResponseUtil;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;

/**
 * @Author: jianguo
 * @Date: 2022/1/24 16:01
 */
public class HttpUrlFinal {

    /**
     * get请求
     * @param request
     * @param map
     * @param url
     * @return
     */
    public static ResponseEntity get(HttpServletRequest request,Map<String, Object> map,String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", request.getHeader("Authorization"));
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(HttpResponseUtil.generateRequestParameters("http://58.215.222.166:5080" + url ,map), HttpMethod.GET, request1, String.class);
        return  response;
    }

    /**
     * post请求
     * @param request
     * @param object
     * @param url
     * @return
     */
    public static ResponseEntity post(HttpServletRequest request,Object object,String url){
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString("http://58.215.222.166:5080"+ url).build().toUri();
        RequestEntity<String> requestEntity = RequestEntity.post(uri)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Accept",MediaType.APPLICATION_JSON.toString())
                .header("Authorization", request.getHeader("Authorization"))
                .body(JSON.toJSONString(object));
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        return response;
    }

    /**
     * post请求
     * @param request
     * @param object
     * @param url
     * @return
     */
    public static ResponseEntity put(HttpServletRequest request,Object object,String url){
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString("http://58.215.222.166:5080" + url).build().toUri();
        RequestEntity<String> requestEntity = RequestEntity.put(uri)
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Accept",MediaType.APPLICATION_JSON.toString())
                .header("Authorization", request.getHeader("Authorization"))
                .body(JSON.toJSONString(object));
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        return response;
    }


    /**
     * delete请求
     * @param request
     * @param map
     * @param url
     * @return
     */
    public static ResponseEntity delete(HttpServletRequest request,Map<String, Object> map,String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", request.getHeader("Authorization"));
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate
                .exchange(HttpResponseUtil.generateRequestParameters("http://58.215.222.166:5080" + url ,map), HttpMethod.DELETE, request1, String.class);
        return  response;
    }
}

