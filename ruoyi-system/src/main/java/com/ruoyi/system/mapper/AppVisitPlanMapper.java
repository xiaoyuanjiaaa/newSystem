package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppVisitPlanQueryDTO;
import com.ruoyi.system.entity.AppVisitPlan;
import com.ruoyi.system.excel.AppVisitExcel;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppVisitPlanVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:05 
 */
public interface AppVisitPlanMapper extends BaseMapper<AppVisitPlan> {

    @Select("<script>update app_visit_plan set is_close=1 where is_close=0 and id_num=#{idNum}</script>")
    Integer updateAppVisitPlanIsColse(@Param("idNum") String idNum);

    /**
     *
     * @param openId
     * @param idNum
     * @return
     */
    AppPersonVO getSymptomsByIdNum(@Param("openId") String openId, @Param("idNum") String idNum);


    List<Map<String,String>> selectProcessList(@Param("appVisit")AppVisitExcel appVisitExcel);

    @Select("<script> update app_person_wx a " +
            " LEFT JOIN app_person b ON a.person_id = b.person_id " +
            " SET a.enabled=1 " +
            "WHERE  id_num = #{idNum}  AND to_days( a.create_time ) = to_days(now()) </script>")
    Integer updateAppVisitStatus(@Param("idNum") String idNum);



    //计算总记录数
    int countTotal(@Param("appVisit")AppVisitExcel appVisitExcel);

    List<Map<String,String>> getAppVisitPlanAll(@Param("appVisit")AppVisitExcel appVisitExcel);

    //计算总记录数
    int getAppVisitPlanAllCount(@Param("appVisit")AppVisitExcel appVisitExcel);

}
