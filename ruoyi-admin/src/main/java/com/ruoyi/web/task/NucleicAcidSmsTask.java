package com.ruoyi.web.task;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.SmsConfig;
import com.ruoyi.system.service.IAppSmsConfigService;
import com.ruoyi.system.service.INucleicAcidService;
import com.ruoyi.system.service.ISmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class NucleicAcidSmsTask {

    @Autowired
    private IAppSmsConfigService smsConfigService;

    @Autowired
    private ISmsConfigService configService;

    @Autowired
    private INucleicAcidService acidService;

//    @Scheduled(cron = "0 */1 * * * ?")
//    @PostConstruct
    public void noticeUserHealthReport(){
        Date now = new Date(System.currentTimeMillis());
        Date now_copy = new Date(System.currentTimeMillis());

        //先查询是否存在appconfig配置
        QueryWrapper<SmsConfig> queryWrapper = new QueryWrapper<>();
        List<SmsConfig> smsConfigList = configService.list(queryWrapper);
        if (CollectionUtils.isEmpty(smsConfigList)){
            return;
        }
        for (SmsConfig smsConfig : smsConfigList) {
            now.setHours(smsConfig.getSendTime().getHours());
            now.setMinutes(smsConfig.getSendTime().getMinutes());

            if(now.equals(now_copy)){
                List<NucleicAcid> acidList = acidService.getCountList();
                if("nucleicAcid".equals(smsConfig.getType()) && ObjectUtil.isNotEmpty(acidList)){
                    smsConfigService.sendMessage(" ",smsConfig.getMobiles(),smsConfig.getType());
                }
            }
        }


    }

}
