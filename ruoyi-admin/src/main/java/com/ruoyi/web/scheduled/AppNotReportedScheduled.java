package com.ruoyi.web.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.AppNotReported;
import com.ruoyi.system.service.IAppNotReportedService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.impl.MaterialSuppliesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 *
 * @Author: jianguo
 * @Date: 2021/9/15 11:16
 */
@Component
//@RestController
//@RequestMapping("test111")
public class AppNotReportedScheduled {

//    @Autowired
//    private MaterialSuppliesServiceImpl suppliesService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IAppNotReportedService reportedService;

    @Scheduled(cron = "1 0 0 * * ?")
//    @Async("threadPoolTaskExecutor")
//    @GetMapping("test222")
//    @Scheduled(cron = "*/1 * * * * ")
    public void reportNo() {
        System.out.println("-==============================================-");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("del_flag", "0");
        wrapper.ne("status", 3);
        wrapper.ne("status", 1);
        wrapper.ne("user_id", 1);
        List<SysUser> list = userService.list(wrapper);
        List<AppNotReported> listTwo = new ArrayList<>();
        for (SysUser sysUser : list) {
            AppNotReported appNotReported = AppNotReported.builder()
                    .deptId(sysUser.getDeptId())
                    .personId(sysUser.getPersonId())
                    .statisticsTime(new Date())
                    .status(sysUser.getStatus())
                    .nickName(sysUser.getNickName())
                    .userId(sysUser.getUserId())
                    .build();
            listTwo.add(appNotReported);
        }
        reportedService.saveBatch(listTwo);
    }

//    @Scheduled(cron = "0 0 4 * * ?")
//    @Scheduled(cron = "*/1 * * * * ")
//    public void list(){
//        suppliesService.addSupplies();
//    }


}
