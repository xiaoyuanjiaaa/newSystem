package com.ruoyi.system.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianDTO;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.vo.AppPersonQueryWxVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AppPersonWxMapper extends BaseMapper<AppPersonWx> {

    /**
     *
     * @param queryDTO
     * @return
     */
    AppPersonWxVO getInfo(@Param("queryDTO") AppPersonWxQueryDTO queryDTO);

    AppPersonWxVO getInfoByYear(@Param("queryDTO") AppPersonWxQueryDTO queryDTO);

    List<AppPersonQueryWxVO> queryList(@Param("queryDTO") AppPersonWxYuJianDTO queryDTO);

    List<JSONObject> queryAll(@Param("queryDTO") AppPersonWxYuJianDTO queryDTO);

    Object getJson(@Param("cId") Long cId,  @Param("startTime") String startTime,@Param("endTime")  String endTime);
}
