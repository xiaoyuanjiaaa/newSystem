package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.MaterialClassification;
import com.ruoyi.system.service.IMaterialClassificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"物资分类"})
@RequestMapping("/materialClassification")
public class MaterialClassificationlController extends BaseController {

    @Autowired
    private IMaterialClassificationService materialClassificationService;

    @GetMapping("/list")
    @ApiOperation("物资分类列表")
    public TableDataInfo list(MaterialClassification materialClassification)
    {
        startPage();
        List<MaterialClassification> list = materialClassificationService.selectMaterialClassificationList(materialClassification);
        return getDataTable(list);
    }

    @PutMapping("/updateStatus")
    @ApiOperation("物资分类选择")
    public AjaxResult updateStatus(@RequestBody List<MaterialClassification> list)
    {
        return toAjax(materialClassificationService.updateStatus(list));
    }

    /**
     * 新增
     * @param materialClassification
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增物资分类")
    public ResultVO<Integer> insert(@RequestBody MaterialClassification materialClassification){
        int b = materialClassificationService.insertMaterial(materialClassification);
        if (b>0){ return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增失败");
    }

    /**
     * 根据编码查询一条数据
     * @param kindCode
     * @return
     */
    @GetMapping("/getByCode")
    @ApiOperation("查询一条")
    public MaterialClassification getOneById(String kindCode){
        return materialClassificationService.getOneByCode(kindCode);
    }

    /**
     * 修改
     * @param materialClassification
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update( @RequestBody MaterialClassification materialClassification){
        int b = materialClassificationService.updateMaterial(materialClassification);
        if (b>0){return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS,b);}
        throw new CustomException("修改失败");
    }

    /**
     * 删除
     * @param kindCode
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(String kindCode){
        int b = materialClassificationService.delMaterial(kindCode);
        if(b>0){return new ResultVO<Integer>(SuccessEnums.DELETE_SUCCESS,b);}
        throw new CustomException("删除失败");
    }

    @GetMapping("/getDown")
    @ApiOperation("下拉选中物资分类")
    public List<MaterialClassification> getDown(){
        return materialClassificationService.getDown();
    }



}
