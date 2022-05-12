package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.AppPersonDestinationSaveDTO;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.AppPersonDestination;
import com.ruoyi.system.vo.AppPersonDestinationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 02:18
 */
public interface IAppPersonDestinationService extends IService<AppPersonDestination> {

    /**
     * 添加值班记录
     * @param saveDTO
     * @return
     */
    AppPersonDestinationVO saveInfo(AppPersonDestinationSaveDTO saveDTO);


    List<AppPersonDestinationVO> listPersonDestinationVO(Long personId);
}
