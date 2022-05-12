package com.ruoyi.web.controller.material;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.service.IMaterialClassificationMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author  dll
 * @Date 2021-11-03 10：08
 */
@RestController
@Api(tags = {"物资分类管理"})
@RequestMapping("/materialClassificationMapping")
public class MaterialClassificationMappingController extends BaseController {

    @Autowired
    private IMaterialClassificationMappingService service;

    /**
     * 选择勾选添加
     * @param kindCode
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("选中添加")
    public ResultVO<Integer> addCheck(String kindCode){
        int i = service.addCheck(kindCode);
        if(i>0){return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS,i);}
        throw new CustomException("新增失败");
    }

}
