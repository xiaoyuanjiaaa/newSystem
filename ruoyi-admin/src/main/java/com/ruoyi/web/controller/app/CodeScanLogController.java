package com.ruoyi.web.controller.app;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.dto.CodeScanLogDTO;
import com.ruoyi.system.dto.CodeScanLogQueryDTO;
import com.ruoyi.system.entity.vo.CodeScanLogVo;
import com.ruoyi.system.excel.CodeScanLogExcel;
import com.ruoyi.system.service.ICodeScanLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags= {"职工扫码记录"})
@RequestMapping("/codeScanLog")
public class CodeScanLogController {

    @Autowired
    private ICodeScanLogService codeScanLogService;


    @ApiOperation("添加职工扫码记录并返回信息")
    @PostMapping("/save")
    public ResultVO<CodeScanLogVo> save(HttpServletRequest request, CodeScanLogDTO saveDTO) {
        CodeScanLogVo vo = codeScanLogService.saveInfo(request,saveDTO);
        return new ResultVO<CodeScanLogVo>(SuccessEnums.SAVE_SUCCESS, vo);
    }

    @ApiOperation("职工扫码记录列表")
    @GetMapping("/list")
    public ResultVO<IPage<CodeScanLogVo>> list(CodeScanLogQueryDTO queryDTO) {
        IPage<CodeScanLogVo> result = codeScanLogService.getPageInfo(queryDTO);
        return new ResultVO<IPage<CodeScanLogVo>>(SuccessEnums.QUERY_SUCCESS, result);
    }

    @ApiOperation("职工扫码记录导出")
    @GetMapping("/export")
    public AjaxResult export(CodeScanLogQueryDTO queryDTO){
        List<CodeScanLogExcel> excelList = codeScanLogService.export(queryDTO);
        ExcelUtil<CodeScanLogExcel> util = new ExcelUtil<CodeScanLogExcel>(CodeScanLogExcel.class);
        return util.exportExcel(excelList, "职工扫码记录");
    }
    @ApiOperation("职工扫码记录导出")
    @GetMapping("/exportList")
    public AjaxResult exportList(CodeScanLogQueryDTO queryDTO){
        List<CodeScanLogExcel> excelList = codeScanLogService.export(queryDTO);
        return AjaxResult.success("职工扫码记录", JSON.toJSONString(excelList));
    }

}
