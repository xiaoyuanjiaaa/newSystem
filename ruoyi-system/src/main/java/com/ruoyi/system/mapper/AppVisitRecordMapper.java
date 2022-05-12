package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppVisitRecordNumDTO;
import com.ruoyi.system.entity.AppVisitRecord;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:05
 */
public interface AppVisitRecordMapper extends BaseMapper<AppVisitRecord> {

    /**
     * 大屏-人员流动最频繁区域top5
     * @return
     */
    List<AppVisitRecordNumDTO> getListNum();

}
