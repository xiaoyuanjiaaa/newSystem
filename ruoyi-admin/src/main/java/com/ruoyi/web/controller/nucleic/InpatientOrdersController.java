package com.ruoyi.web.controller.nucleic;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.entity.InpatientOrders;
import com.ruoyi.system.entity.vo.InpatientOrdersTypeNameVo;
import com.ruoyi.system.entity.vo.InpatientOrdersVo;
import com.ruoyi.system.service.IInpatientOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = {"住院医嘱"})
@RequestMapping("/inpatient")
public class InpatientOrdersController extends BaseController {

    @Autowired
     IInpatientOrdersService service;

    @ApiOperation("住院医嘱的无参列表查询")
    @GetMapping("/getList")
    public TableDataInfo getList(){
        startPage();
        List<InpatientOrders> list = service.getList();
        return getDataTable(list);
    }

    @ApiOperation("住院病程录")
    @GetMapping("/getPageList")
    public TableDataInfo getPageList(InpatientOrdersVo vo){
        startPage();
        List<InpatientOrdersVo> list = service.getPageList(vo);
        return getDataTable(list);
    }

    @ApiOperation("病程录分类")
    @GetMapping("/getTypeName")
    public ResultVO<List<String>> getTypeName(){
        List<InpatientOrdersTypeNameVo> list = service.getTypeName();
        List<String> l= list.stream().map(InpatientOrdersTypeNameVo::getTypeName).collect(Collectors.toList());
        return new ResultVO<>(SuccessEnums.QUERY_SUCCESS,l);
    }
}
