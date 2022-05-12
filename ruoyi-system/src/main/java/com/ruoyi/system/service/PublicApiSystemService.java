package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.vo.AppPersonInfoVO;
import com.ruoyi.system.vo.AppPersonWxPubVO;
import com.ruoyi.system.vo.AppPersonWxVO;

/**
 * 相关判断业务
 *
 * @author ruoyi
 */
public interface PublicApiSystemService {

    /**
     * 通过姓名和身份证获取用户苏康码信息
     * @param idNum
     * @param mobile
     * @return
     */
    JSONObject getSkmInfoByIdNum(String idNum, String mobile);

    /**
     * 获取用户当天填写的预检分诊信息
     * @param queryDTO
     * @return
     */
    AppPersonWxVO getPersonInfoForPublicApi(AppPersonWxQueryDTO queryDTO);

    /**
     * 根据扫描的苏康码url获取用户的基本信息以及码的颜色
     * @param url
     * @return
     */
    JSONObject getPersonInfoByCode(String url, Long userId, Integer type);

    /**
     * 对外开放接口，根据身份证获取预检分诊信息
     * @param idNum
     * @return
     */
    AppPersonWxPubVO getExpectInformation(String idNum);
}
