package com.ruoyi.web.controller.nucleic;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.vo.NucleicAcidVo;
import com.ruoyi.system.service.INucleicAcidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = {"核酸抗体"})
@RequestMapping("/nucleicAcid")
public class NucleicAcidController extends BaseController {
    @Autowired
    private INucleicAcidService service;

    @ApiOperation("核酸抗体分页列表")
    @GetMapping("/getList")
    public TableDataInfo getList(NucleicAcid nucleicAcid){
        startPage();
        List<NucleicAcid> list = service.getList(nucleicAcid);
        return getDataTable(list);
    }

    @ApiOperation("核酸抗体分类分页列表")
    @GetMapping("/getCountList")
    public TableDataInfo getCountList(String type){
        startPage();
        List<NucleicAcid> list = service.getPageList(type);
        return getDataTable(list);
    }

    @ApiOperation("核酸结果分类")
    @GetMapping("/getNucleicAcid")
    public TableDataInfo getNucleicAcid(){
        List<NucleicAcidVo> list = service.getNucleicAcid();
        return getDataTable(list);
    }

    @ApiOperation("抗体结果分类")
    @GetMapping("/getAntibody")
    public TableDataInfo  getAntibody(){
        List<NucleicAcidVo> list = service.getAntibody();
        return getDataTable(list);
    }
}
