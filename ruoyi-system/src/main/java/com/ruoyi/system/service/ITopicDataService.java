package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppHealthReportSaveDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.TopicData;

import java.util.Map;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:15
 */

public interface ITopicDataService extends IService<TopicData> {


    void resolve(AppHealthReportSaveDTO saveDTO, AppHealthReport report);

    Boolean resolveReport(String str);

    void jsonTransToDB(AppHealthReport report);
}
