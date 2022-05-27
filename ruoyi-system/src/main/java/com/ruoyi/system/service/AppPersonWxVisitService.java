package com.ruoyi.system.service;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianDTO;
import com.ruoyi.system.dto.AppPersonWxYuJianVisitDTO;
import com.ruoyi.system.entity.AppPersonWxVisit;
import com.ruoyi.system.vo.AppPersonQueryWxVO;

import java.util.List;


public interface AppPersonWxVisitService extends IService<AppPersonWxVisit> {

    /**
     * 添加预检分诊信息
     * @param saveDTO
     * @return
     */
    void saveInfo(AppPersonWxVisit saveDTO);

    List<JSONObject> queryList(AppPersonWxYuJianVisitDTO queryDTO);
    List<AppPersonQueryWxVO> queryTotal(AppPersonWxYuJianVisitDTO queryDTO);

    Long queryIdListById(Long id);

    ResultVO<AppPersonWxVisit> detail(AppPersonWxQueryDTO queryDTO);
}
