package com.ruoyi.web.task;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.dto.ZhSmsDTO;
import com.ruoyi.system.entity.AppSmsConfig;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.SmsConfig;
import com.ruoyi.system.entity.vo.MaterialSuppliesVo;
import com.ruoyi.system.service.IAppHealthReportService;
import com.ruoyi.system.service.IAppSmsConfigService;
import com.ruoyi.system.service.IMaterialSuppliesService;
import com.ruoyi.system.service.ISmsConfigService;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class NewSmsTask {

    @Autowired
    private IAppSmsConfigService smsConfigService;

    @Autowired
    private ISmsConfigService configService;

    @Autowired
    private IMaterialSuppliesService materialSuppliesService;


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
            if (now.equals(now_copy)){
                List<String> materialList = materialSuppliesService.getWarnMaterial();
                if("material".equals(smsConfig.getType()) && ObjectUtil.isNotEmpty(materialList)){
                    materialList.stream().forEach(t->{
                        smsConfigService.sendMessage(t,smsConfig.getMobiles(),smsConfig.getType());
                    });
                }
            }
        }


    }
}
