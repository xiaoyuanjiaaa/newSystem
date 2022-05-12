package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.bo.DeptExamineListBO;
import com.ruoyi.system.dto.DeptExamineListDTO;
import com.ruoyi.system.entity.DeptChangeExamine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.vo.DeptExamineListVO;
import com.ruoyi.system.vo.DeptListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 片区更换审核表 Mapper 接口
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-07
 */
public interface DeptChangeExamineMapper extends BaseMapper<DeptChangeExamine> {
   List<DeptListVO> deptList();
  //获取审核列表
   Page<DeptExamineListVO> deptExamineList(@Param("queryBO") DeptExamineListBO queryBO, Page<DeptExamineListVO> page);
}
