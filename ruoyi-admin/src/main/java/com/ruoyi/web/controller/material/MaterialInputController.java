package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.service.IMaterialInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"入库管理"})
@RequestMapping("/materialInput")
public class MaterialInputController extends BaseController {
    @Autowired
    IMaterialInputService service;

    @GetMapping("/list")
    @ApiOperation("入库列表查询")
    public TableDataInfo list(MaterialInput materialInput){
        startPage();
        List<MaterialInput> list = service.list(materialInput);
        return getDataTable(list);
    }

    @GetMapping("/getOneById")
    @ApiOperation("根据id查询一条")
    public ResultVO<MaterialInput> getOneById(Long id){
        MaterialInput input =  service.getOneById(id);
        if (input != null) {
            return new ResultVO<>(SuccessEnums.QUERY_SUCCESS,input);
        }
        throw new CustomException("查询失败");
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(MaterialInput materialInput){
        int b = service.add(materialInput);
        if(b>0){return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS,b);}
        throw new CustomException("新增失败");
    }
}
