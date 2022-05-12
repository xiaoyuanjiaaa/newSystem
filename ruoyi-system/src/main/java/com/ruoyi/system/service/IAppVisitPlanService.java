package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.dto.AppVisitPlanSaveDTO;
import com.ruoyi.system.dto.AppVisitPlanUpdateDTO;
import com.ruoyi.system.entity.AppVisitPlan;
import com.ruoyi.system.excel.AppVisitExcel;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppVisitPlanVO;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @Author  ddl
 * @Date 2021-08-14 01:54 
 */
public interface IAppVisitPlanService extends IService<AppVisitPlan> {

    /**
     * 根据身份查询当前未关闭的访客计划
     * @param dto
     * @return
     */
    AppVisitPlanVO getAppVisitPlan (AppVisitPlanQueryDTO dto);

    AppVisitPlanVO getAppVisitPlanToday (AppVisitPlanQueryDTO dto);


    /**
     * 添加访客计划
     * @param dto
     * @return
     */
    AppVisitPlanVO saveAppVisitPlan(AppVisitPlanSaveDTO dto);

    /**
     * 根据身份证号码关闭访客计划
     * @param idNum
     * @return
     */
    Integer updateAppVisitPlanIsColse(String idNum);

    /**
     * 修改访客计划
     * @param dto
     * @return
     */
    Integer updateInfo(AppVisitPlanUpdateDTO dto);

    /**
     * 获取用户当天的预检分诊信息
     * @param openId
     * @return
     */
    AppPersonVO getSymptomsByOpenId(String openId, String idNum);


    List<Map<String,String>> selectProcessList(AppVisitExcel appVisitExcel);

    //计算总记录数
    int countTotal(AppVisitExcel appVisitExcel);

    Integer updateAppVisitStatus(String idNum);

    List<Map<String,String>> getAppVisitPlanAll(AppVisitExcel appVisitExcel);

    int getAppVisitPlanAllCount(AppVisitExcel appVisitExcel);

}
