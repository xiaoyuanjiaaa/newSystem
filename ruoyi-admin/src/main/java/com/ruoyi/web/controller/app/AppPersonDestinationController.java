package com.ruoyi.web.controller.app;

import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.dto.AppPersonDestinationSaveDTO;
import com.ruoyi.system.service.IAppPersonDestinationService;
import com.ruoyi.system.vo.AppPersonDestinationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags= {"值班记录"})
@RequestMapping("/appPersonDestination")
public class AppPersonDestinationController {

    @Autowired
    private IAppPersonDestinationService appPersonDestinationService;

    @ApiOperation("添加值班记录")
    @PostMapping("/saveInfo")
    public ResultVO<AppPersonDestinationVO> saveInfo(@RequestBody AppPersonDestinationSaveDTO saveDTO) {
        AppPersonDestinationVO result = appPersonDestinationService.saveInfo(saveDTO);
        System.out.println(result.toString());
        return new ResultVO<AppPersonDestinationVO>(SuccessEnums.SAVE_SUCCESS, result);
    }
}
