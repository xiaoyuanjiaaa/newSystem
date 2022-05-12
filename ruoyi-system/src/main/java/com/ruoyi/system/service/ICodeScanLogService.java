package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.CodeScanLogDTO;
import com.ruoyi.system.dto.CodeScanLogQueryDTO;
import com.ruoyi.system.entity.CodeScanLog;
import com.ruoyi.system.entity.vo.CodeScanLogVo;
import com.ruoyi.system.excel.CodeScanLogExcel;
import com.ruoyi.system.vo.AppRouteReportVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description  
 * @Author  ddl
 * @Date 2021-08-14 01:52 
 */
public interface ICodeScanLogService extends IService<CodeScanLog> {

    CodeScanLogVo saveInfo(HttpServletRequest request, CodeScanLogDTO saveDTO);

    IPage<CodeScanLogVo> getPageInfo(CodeScanLogQueryDTO queryDTO);

    List<CodeScanLogExcel> export(CodeScanLogQueryDTO queryDTO);

}
