package com.ruoyi.web.task;

import cn.hutool.cron.CronUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.RemindTypeEnums;
import com.ruoyi.quartz.util.CronUtils;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.ZhSmsDTO;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.service.IAppHealthReportService;
import com.ruoyi.system.service.IAppSmsConfigService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Component()
public class SmsTask {


    @Value("${smsZh.sms.uid}")
    private String uId;

    @Value("${smsZh.sms.userPwd}")
    private String userPwd;

    @Value("${smsZh.sms.ext}")
    private String ext;

    @Value("${smsZh.sms.message}")
    private String message;



    @Autowired
    private IAppSmsConfigService smsConfigService;
    @Autowired
    private IAppHealthReportService healthReportService;

    @Scheduled(cron = "0/60 * * * * ?")
    @PostConstruct
    public void noticeUserHealthReport() {
        LoggerFactory.getLogger(SmsTask.class).error("定时任务开始");
        Date now = new Date(System.currentTimeMillis());
        Date now_copy = new Date(System.currentTimeMillis());
        Boolean flag = false;
        String mobile = "";
//        先查询是否存在appconfig配置
        QueryWrapper<AppSmsConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_enabled", 0);
        queryWrapper.eq("type", RemindTypeEnums.NOT_FILL_REMIND.getCode());
        List<AppSmsConfig> smsConfigList = smsConfigService.list(queryWrapper);
                if (CollectionUtils.isEmpty(smsConfigList)) {
            return;
        }
        Boolean is=false;
        for (AppSmsConfig appSmsConfig : smsConfigList) {
            if(appSmsConfig.getReminder()==1){
                is=true;
            }
            now.setHours(appSmsConfig.getSmsTime().getHours());
            now.setMinutes(appSmsConfig.getSmsTime().getMinutes());
            if (now.equals(now_copy)) {
                switch (appSmsConfig.getReminder()) {
                    case 1:
                        smsConfigService.sendSelfPhone();
                        break;
                    case 2:
                        smsConfigService.sendLeaderPhone(is);
                        break;
                    case 3:
                        smsConfigService.sendFillPhone(appSmsConfig.getAppointUser(),is);
                        break;
                }
            }
        }
    }



}
