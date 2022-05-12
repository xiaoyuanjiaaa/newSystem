package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.vo.MaterialOutputVo;
import com.ruoyi.system.service.IMaterialOutputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"出库管理"})
@RequestMapping("/materialOutput")
public class MaterialOutputController extends BaseController {
    @Autowired
    IMaterialOutputService service;

    @GetMapping("/list")
    @ApiOperation("出库列表查询")
    public TableDataInfo list(MaterialOutput materialOutput){
        startPage();
        List<MaterialOutput> list = service.list(materialOutput);
        return getDataTable(list);
    }

    @ApiOperation("查询出库记录数")
    @GetMapping("/getOutputNumber")
    public List<MaterialOutputVo> getOutputNumber(Long id,Long evaluateDays){
        return service.getOutputNumber(id,evaluateDays);
    }
}
