package com.ruoyi.system.service;

import com.google.zxing.WriterException;
import com.ruoyi.common.core.domain.entity.AppPerson;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.vo.AppPersonInfoVO;
import com.ruoyi.system.vo.AppPersonVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 相关判断业务
 *
 * @author ruoyi
 */
public interface SystemService {
    /**
     * 根据扫描的苏康码url获取用户的基本信息以及码的颜色
     * @param url
     * @return
     */
    AppPersonInfoVO getPersonInfoByCode(HttpServletRequest request,String url, Long userId, Integer type);

    /**
     * 获取苏康码里面的信息
     * @param url
     * @return
     */
    AppPersonInfoVO getSuKangMaInfo(String url);

    /**
     * 根据人员基本信息加密生成二维码并并转成base64返回前端
     * @param name
     * @param idNum
     * @param mobile
     * @return
     */
    String outQrCode(String endTime,String qrcodeType,String name, String idNum, String mobile, Integer colour);

    /**
     * 解密二维码里面的信息
     * @param content
     * @return
     */
    AppPersonInfoVO decoderQrCode(HttpServletRequest request,String content);

    /**
     * 通过姓名和身份证获取用户苏康码信息
     * @param idNum
     * @param mobile
     * @return
     */
    String getSkmInfoByIdNum(HttpServletRequest request, String idNum, String mobile);

    String queryOpenId(String tempCode);

}
