package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.dto.ZhSmsDTO;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.vo.SmsConfigListVO;

import java.util.List;

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

    //短信提醒列表
    TableDataInfo smsConfigList(PageDomain domain);
    //获取手机号（发送给本人）
    void sendSelfPhone();
    //获取手机号（发送给部门负责）
    void sendLeaderPhone(Boolean is);
    //填报提醒获取手机号（发送给指定人员）
    void sendFillPhone(String appointUser,Boolean is);

    //异常提醒获取手机号（发送给指定人员）
    String getAppointPhone(String appointUser,Boolean is);
}
