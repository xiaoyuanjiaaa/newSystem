package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.MaterialThreshold;
import com.ruoyi.system.service.IMaterialThresholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"物资阈值"})
@RequestMapping("/materialThreshold")
public class MaterialThresholdController extends BaseController {

    @Autowired
    IMaterialThresholdService service;

    @GetMapping("/list")
    @ApiOperation("物资阈值列表")
    public TableDataInfo list(MaterialThreshold materialThreshold) {
        startPage();
        List<MaterialThreshold> list = service.list(materialThreshold);
        return getDataTable(list);
    }

    @GetMapping("/getOneById")
    @ApiOperation("根据id查询一条物资阈值")
    @ApiImplicitParam()
    public MaterialThreshold getOneById(Long id) {
        return service.getOneById(id);
    }

    @PostMapping("/add")
    @ApiOperation("选择物资添加阈值")
    @ApiImplicitParams({
            @ApiImplicitParam(),
            @ApiImplicitParam()
    })
    public ResultVO<Integer> add(@RequestBody MaterialThreshold materialThreshold) {
        int b = service.add(materialThreshold);
        if (b > 0) {
            return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS, b);
        }
        throw new CustomException("新增失败");
    }

    @PostMapping("/update")
    @ApiOperation("修改阈值")
    public ResultVO<Integer> update(@RequestBody MaterialThreshold materialThreshold) {
        int b = service.updateThreshold(materialThreshold);
        if (b > 0) {
            return new ResultVO<Integer>(SuccessEnums.UPDATE_SUCCESS, b);
        }
        throw new CustomException("修改失败");
    }

    @PostMapping("/del")
    @ApiOperation("逻辑删除阈值")
    public ResultVO<Integer> delete(Long id) {
        int b = service.del(id);
        if (b > 0) {
            return new ResultVO<Integer>(SuccessEnums.DELETE_SUCCESS, b);
        }
        throw new CustomException("删除失败");
    }


    @GetMapping("/getThreshold")
    @ApiOperation("计算阈值")
    public List<MaterialThreshold> getThreshold(MaterialThreshold materialThreshold) {
        return service.getThreshold(materialThreshold);
    }

    @ApiOperation("查找全局变量")
    @GetMapping("/getAllThreshold")
    public MaterialThreshold getAllThreshold() {
        return service.getAllThreshold();
    }

    @ApiOperation("修改局部阈值")
    @PostMapping("/updateAllThreshold")
    public int updateAllThreshold(@RequestBody MaterialThreshold materialThreshold) {
        return service.updateAllThreshold(materialThreshold);
    }

    @ApiOperation("查询一条阈值")
    @GetMapping("/getOne")
    public ResultVO<MaterialThreshold> getOne(Long id) {
        MaterialThreshold materialThreshold = service.getOne(id);
        if (materialThreshold != null) {
            return new ResultVO<MaterialThreshold>(SuccessEnums.QUERY_SUCCESS, materialThreshold);
        } else {
            return new ResultVO<MaterialThreshold>(200, "该物资无出库记录，无法配置阈值", null);
        }
    }

    @ApiOperation("计算平均值或最大值的阈值")
    @GetMapping("/getOneThresholdNumber")
    public Long getOneThresholdNumber(MaterialThreshold materialThreshold) {
        return service.getOneThresholdNumber(materialThreshold);
    }

    @ApiOperation("未出库物资添加阈值")
    @PostMapping("/insertNoOut")
    public ResultVO<Integer> insertNoOut(@RequestBody MaterialThreshold materialThreshold) {
        int i = service.insertNoOut(materialThreshold);
        if (i > 0) {
            return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS, i);
        }
        throw new CustomException("新增失败");
    }

    @ApiOperation("获取一条未出库的物资的阈值")
    @GetMapping("/getOneNoOutput")
    public ResultVO<MaterialThreshold> getOneNoOutput(Long id) {
        MaterialThreshold materialThreshold = service.getOneNoOutput(id);
        if (materialThreshold != null) {
            return new ResultVO<MaterialThreshold>(SuccessEnums.QUERY_SUCCESS, materialThreshold);
        } else {
            return new ResultVO<MaterialThreshold>(200, "该物资无出库记录，无法配置阈值", null);
        }
    }

}
