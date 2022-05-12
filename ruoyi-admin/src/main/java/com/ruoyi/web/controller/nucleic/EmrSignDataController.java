package com.ruoyi.web.controller.nucleic;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.EmrSignData;
import com.ruoyi.system.service.IEmrSignDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"体征数据"})
@RequestMapping("/emrSign")
public class EmrSignDataController extends BaseController {
    @Autowired
    IEmrSignDataService service;

    @ApiOperation("生命体征的无参列表查询")
    @GetMapping("/getList")
    public TableDataInfo getList(){
        startPage();
        List<EmrSignData> list = service.getList();
        return getDataTable(list);
    }
}
