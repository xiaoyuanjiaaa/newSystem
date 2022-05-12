package com.ruoyi.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppPersonWxYuJianDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianVisitDTO;
import com.ruoyi.system.entity.AppPersonWxVisit;
import com.ruoyi.system.vo.AppPersonQueryWxVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPersonWxVisitMapper extends BaseMapper<AppPersonWxVisit> {


    List<AppPersonQueryWxVO> queryList(@Param("queryDTO") AppPersonWxYuJianVisitDTO queryDTO);

    Long queryIdListById(Long id);
}
