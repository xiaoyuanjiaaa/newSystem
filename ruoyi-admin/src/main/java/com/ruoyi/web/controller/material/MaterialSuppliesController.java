package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.vo.MaterialSuppliesVo;
import com.ruoyi.system.service.IMaterialSuppliesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"物资供应"})
@RequestMapping("/materialSupplies")
public class MaterialSuppliesController extends BaseController {

    @Autowired
    private IMaterialSuppliesService service;

    @GetMapping("/list")
    @ApiOperation("物资供应列表")
    public TableDataInfo list(String kindName,String materialName) {
        startPage();
        List<MaterialSupplies> list = service.getList(kindName,materialName);
        return getDataTable(list);
    }


    @PostMapping("/getOneById")
    @ApiOperation("根据id查询一条")
    public MaterialSupplies getOneById(Long id){
        return service.getOneById(id);
    }


    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody MaterialSupplies materialSupplies){
        int b = service.update(materialSupplies);
        if (b>0){return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS,b);}
        throw new CustomException("修改失败");
    }

    @PostMapping("/updateSendStatus/{id}")
    @ApiOperation("修改是否发送短信")
    public ResultVO<Integer> updateSendStatus(@PathVariable("id") Long id){
        int b = service.updateSendStatus(id);
        if (b>0){return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS,b);}
        throw new CustomException("修改失败");
    }


    @PostMapping("/del")
    @ApiOperation("逻辑删除")
    public ResultVO<Integer> delSupplies(Long id){
        int b = service.delSupplies(id);
        if (b>0){return new ResultVO<Integer>(SuccessEnums.DELETE_SUCCESS,b);}
        throw new CustomException("删除失败");
    }


    @GetMapping("/evaluate")
    @ApiOperation("物资匮乏提醒")
    public TableDataInfo evaluate(String materialName){
        startPage();
        List<MaterialSuppliesVo> list = service. evaluate(materialName);
        return getDataTable(list);
    }




}
