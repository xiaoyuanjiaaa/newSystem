package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.system.dto.ZhSmsDTO;
import com.ruoyi.system.entity.AppSmsConfig;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:53
 */
public interface IAppSmsConfigService extends IService<AppSmsConfig> {

    ResultVO sendMessage(String phone);

    void sendMessage(String prefix,String phone,String type);


    /*推送短信信息*/
    public void noticeReportBySms(ZhSmsDTO zhSmsDTO);

    //添加短信配置
    ResultVO addSmsConfig(AppSmsConfig appSmsConfig);
}
