package com.ruoyi.web.controller.nucleic;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.HisInpatientInfo;
import com.ruoyi.system.service.IHisInpatientInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"His住院人基本信息"})
@RequestMapping("/hisInpatientInfo")
public class HisInpatientInfoController extends BaseController {
    @Autowired
    IHisInpatientInfoService service;

    @ApiOperation("His住院人基本信息的无参列表查询")
    @GetMapping("/getList")
    public TableDataInfo getList(){
        startPage();
        List<HisInpatientInfo> list = service.getList();
        return getDataTable(list);
    }

}
