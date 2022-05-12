package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppVisitRecordNumDTO;
import com.ruoyi.system.dto.AppVisitRecordSaveDTO;
import com.ruoyi.system.entity.AppVisitRecord;
import com.ruoyi.system.vo.AppVisitRecordVO;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:54
 */
public interface IAppVisitRecordService extends IService<AppVisitRecord> {

    /**
     * 根据计划查询访客记录
     * @param planId
     * @return
     */
    List<AppVisitRecordVO> getAppVisitRecordByPlanId(Long planId);

    /**
     * 添加访客记录数据
     * @param saveDTO
     * @return
     */
    AppVisitRecordVO saveInfo(AppVisitRecordSaveDTO saveDTO);

    /**
     * 大屏-人员流动最频繁区域top5
     * @return
     */
    List<AppVisitRecordNumDTO> getListNum();

}
