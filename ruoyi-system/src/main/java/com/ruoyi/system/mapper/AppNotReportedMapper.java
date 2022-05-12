package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.entity.AppNotReported;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/15 11:04
 */
@Mapper
public interface AppNotReportedMapper extends BaseMapper<AppNotReported> {

    int deleteDatas(Long[] userIds);

    Long[] getListData(Long[] userIds);
}
