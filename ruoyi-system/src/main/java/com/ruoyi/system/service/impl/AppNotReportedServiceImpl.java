package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.AppNotReported;
import com.ruoyi.system.mapper.AppNotReportedMapper;
import com.ruoyi.system.service.IAppHealthReportService;
import com.ruoyi.system.service.IAppNotReportedService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.vo.AppHealthReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/15 11:08
 */
@Service
public class AppNotReportedServiceImpl extends ServiceImpl<AppNotReportedMapper, AppNotReported> implements IAppNotReportedService {

    @Autowired
    private IAppHealthReportService reportService;

    @Override
    public Boolean saveData(SysUser user) {
        AppHealthReport infoByPersonId = reportService.getInfoByPersonIdTwo(user.getPersonId());
        if (infoByPersonId != null){
            return false;
        }
        AppNotReported appNotReported = AppNotReported.builder()
                .nickName(user.getNickName())
                .personId(user.getPersonId())
                .statisticsTime(new Date())
                .status(user.getStatus())
                .deptId(user.getDeptId())
                .userId(user.getUserId())
                .build();
        boolean save = save(appNotReported);
        if (!save){
            throw new CustomException("新增未填报数据失败");
        }
        return save;
    }

    @Override
    public Boolean deleteDatas(Long[] userIds) {
        Long[] userIdsTwo = baseMapper.getListData(userIds);
        if (userIdsTwo == null || userIdsTwo.length == 0){
            return false;
        }
        int i = baseMapper.deleteDatas(userIdsTwo);
        if (i == 0){
            throw  new CustomException("删除未填报数据失败");
        }
        return true;
    }
}
