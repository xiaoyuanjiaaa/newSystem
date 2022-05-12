package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.dto.DeptExamineDTO;
import com.ruoyi.system.dto.DeptExamineListDTO;
import com.ruoyi.system.entity.DeptChangeExamine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.vo.DeptExamineListVO;
import com.ruoyi.system.vo.DeptListVO;
import com.ruoyi.system.vo.DeptStatusVO;

import java.util.List;

/**
 * <p>
 * 片区更换审核表 服务类
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-07
 */
public interface IDeptChangeExamineService extends IService<DeptChangeExamine> {
      //片区列表
      ResultVO<List<DeptListVO>> deptList();

      //修改片区
      ResultVO<Boolean> udpateDept(Integer deptChangeId);

      //片区审核列表
      TableDataInfo deptExamineList(DeptExamineListDTO dto);

      //片区审核
      AjaxResult deptExamine(DeptExamineDTO dto);

      //获取当前片区状态
      ResultVO<DeptStatusVO> deptStatus();

      //片区审核
      AjaxResult savePic(String url);

}
