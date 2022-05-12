package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.vo.UserRosterVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:14
 */
public interface AppRosterUserMapper extends BaseMapper<AppRosterUser> {

    List<UserRosterVO> selectListWrapper(@Param("ew") QueryWrapper<AppRosterUser> queryWrapper);
}
