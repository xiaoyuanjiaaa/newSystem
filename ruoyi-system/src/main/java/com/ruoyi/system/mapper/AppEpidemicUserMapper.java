package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.AppEpidemicUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 19:49
 */
@Mapper
public interface AppEpidemicUserMapper extends BaseMapper<AppEpidemicUser> {

    int deleteByIdYes(Long id);
}
