package com.ruoyi.web.controller.nucleic;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.NucleicAcidThreshold;
import com.ruoyi.system.service.INucleicAcidThresholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"核酸抗体阈值"})
@RequestMapping("/nucleicAcidThreshold")
public class NucleicAcidThresholdController extends BaseController {

    @Autowired
    private INucleicAcidThresholdService service;

    /**
     * 添加阈值
     * @param nucleicAcidThreshold
     * @return
     */
    @ApiOperation("添加核酸抗体阈值")
    @PostMapping("/insert")
    public ResultVO<Integer> insert(@RequestBody NucleicAcidThreshold nucleicAcidThreshold){
        int i = service.insert(nucleicAcidThreshold);
        if(i>0){return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS,i);}
        throw new CustomException("新增失败");
    }

    @ApiOperation("获取一条阈值")
    @GetMapping("/getOne")
    public ResultVO<NucleicAcidThreshold> getOne(Long id){
        NucleicAcidThreshold nucleicAcidThreshold = service.getOne(id);
        if(nucleicAcidThreshold!=null){
            return new ResultVO<NucleicAcidThreshold>(SuccessEnums.QUERY_SUCCESS,nucleicAcidThreshold);
        }
        throw new CustomException("查询失败");
    }

    /**
     * 修改阈值
     * @param nucleicAcidThreshold
     * @return
     */
    @ApiOperation("修改阈值")
    @PostMapping("/update")
    public ResultVO<Integer> update(@RequestBody NucleicAcidThreshold nucleicAcidThreshold){
        int i = service.update(nucleicAcidThreshold);
        if(i>0){return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS,i);}
        throw new CustomException("修改失败");
    }

    /**
     * 查询布尔值阈值
     * @param type
     * @return
     */
    @ApiOperation("查询布尔类型阈值")
    @GetMapping("/getListByThreshold")
    public TableDataInfo getListByThreshold(String type){
        startPage();
        List<NucleicAcid> list = service.getListByThreshold(type);
        return getDataTable(list);
    }

    /**
     * 查询数值阈值
     * @param type
     * @return
     */
    @ApiOperation("查询数值阈值")
    @GetMapping("/getListByNumber")
    public TableDataInfo getListByNumber(String type){
        startPage();
        List<NucleicAcid> list = service.getListByNumber(type);
        return getDataTable(list);
    }
}
