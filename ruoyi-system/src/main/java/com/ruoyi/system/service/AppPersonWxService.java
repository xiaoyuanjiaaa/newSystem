package com.ruoyi.system.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppPersonWxSaveDTO;
import com.ruoyi.system.dto.AppPersonWxUpdateDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.vo.AppPersonQueryWxVO;
import com.ruoyi.system.vo.AppPersonWxPubVO;
import com.ruoyi.system.vo.AppPersonWxVO;

import java.io.InputStream;
import java.util.List;

public interface AppPersonWxService extends IService<AppPersonWx> {

    /**
     * 添加预检分诊信息
     * @param saveDTO
     * @return
     */
    String saveInfo(AppPersonWxSaveDTO saveDTO);

    /**
     * 修改预检分诊信息
     * @param updateDTO
     * @return
     */
    AppPersonWxVO updateInfo(AppPersonWxUpdateDTO updateDTO);

    /**
     * 根据personId获取当天预检分诊信息
     * @param personId
     * @return
     */
    AppPersonWx getInfoByPersonId(Long personId);

    /**
     * 根据personIds获取当天预检分诊信息
     * @param personIds
     * @return
     */
    AppPersonWx getInfoByPersonIds(List<Long> personIds);

    /**
     * 获取用户当天填写的预检分诊信息
     * @param queryDTO
     * @return
     */
    AppPersonWxVO getInfo(AppPersonWxQueryDTO queryDTO);

    List<cn.hutool.json.JSONObject> queryList(AppPersonWxYuJianDTO queryDTO);
    //预检分诊信息 为了防止和其他调用者耦合 单独做了一个分页查询
    PageInfo queryList1(AppPersonWxYuJianDTO queryDTO);
    //小程序预约确认根据手机号查询
    PageInfo weChatQueryList(AppPersonWxYuJianDTO queryDTO);
    List<AppPersonQueryWxVO> queryTotal(AppPersonWxYuJianDTO queryDTO);


    List<cn.hutool.json.JSONObject> tableHead();


    /**
     * 修改预检分诊信息
     * @param updateDTO
     * @return
     */
    AppPersonWxVO updateVisitStatus(AppPersonWxUpdateDTO updateDTO);

    /**
     * 导出所有代填信息
     * @param queryDTO
     * @return
     */
    AjaxResult exportAppPersonWx(AppPersonWxYuJianDTO queryDTO, InputStream inputStream,String uploadUri);

    /**
     * 根据身份证获取预检分诊信息
     * @param idNum
     * @return
     */
    AppPersonWxPubVO getExpectInformation(String idNum);


    boolean updateStatus(String expectId);
}
