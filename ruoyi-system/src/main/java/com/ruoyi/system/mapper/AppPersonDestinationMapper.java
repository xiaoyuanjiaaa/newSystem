package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.AppPersonDestination;
import com.ruoyi.system.vo.AppPersonDestinationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:18
 */
public interface AppPersonDestinationMapper extends BaseMapper<AppPersonDestination> {

    List<AppPersonDestinationVO> selectListByWrapper(@Param("ew") QueryWrapper<AppPersonDestination> personDestinationQueryWrapper);
}
