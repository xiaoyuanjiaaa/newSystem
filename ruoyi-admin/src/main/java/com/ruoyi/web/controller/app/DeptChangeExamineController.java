package com.ruoyi.web.controller.app;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.system.dto.DeptExamineDTO;
import com.ruoyi.system.dto.DeptExamineListDTO;
import com.ruoyi.system.service.IDeptChangeExamineService;
import com.ruoyi.system.vo.DeptExamineListVO;
import com.ruoyi.system.vo.DeptListVO;
import com.ruoyi.system.vo.DeptStatusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 片区更换审核表 前端控制器
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-07
 */
@Slf4j
@RestController
@Api(tags= {"片区更换审核"})
@RequestMapping("/deptChangeExamine")
public class DeptChangeExamineController {
    @Autowired
    private IDeptChangeExamineService DeptChangeExamineService;

    @Autowired
    private ServerConfig serverConfig;

    @ApiOperation("获取片区审核状态以及门铃码图片")
    @GetMapping("/currentDept")
    public ResultVO<DeptStatusVO> currentDept() {
        return DeptChangeExamineService.deptStatus();
    }

    @ApiOperation("片区列表")
    @GetMapping("/deptList")
    public ResultVO<List<DeptListVO>> deptList() {
        return DeptChangeExamineService.deptList();
    }

    @ApiOperation("申请修改片区")
    @GetMapping("/deptUpdate")
    public ResultVO<Boolean> deptUpdate(@RequestParam Integer deptId) {
        return DeptChangeExamineService.udpateDept(deptId);
    }

    @ApiOperation("片区审核列表")
    @GetMapping("/deptExamineList")
    public TableDataInfo deptExamineList(DeptExamineListDTO deptExamineListDTO) {
        return DeptChangeExamineService.deptExamineList(deptExamineListDTO);
    }

    @ApiOperation("片区审核")
    @GetMapping("/deptExamine")
    public AjaxResult deptExamine(DeptExamineDTO DeptExamineDTO) {
        return DeptChangeExamineService.deptExamine(DeptExamineDTO);
    }

    @ApiOperation("个人门铃码图片上传并保存")
    @PutMapping(value="/picUpload",consumes = "multipart/*",headers ="content-type=multipart/form-data")
    @ApiImplicitParam(name = "file", paramType="form", value = "临时文件", dataType="file", required = true)
    public AjaxResult uploadFile(@RequestPart("file") MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            log.info("开始------------------");
            String fileName = FileUploadUtils.upload(filePath, file);
            log.info("结束------------------");
            String url = serverConfig.getUrl() + fileName;
            DeptChangeExamineService.savePic(url);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            log.info(ajax+"------------------");
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}

