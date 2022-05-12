package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppPersonQueryDTO;
import com.ruoyi.system.dto.AppPersonSaveDTO;
import com.ruoyi.system.dto.AppPersonWxQueryDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.service.AppPersonService;
import com.ruoyi.system.vo.AppPersonInfoVO;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:44
 */
@RestController
@Api(tags = {"人员底库"})
@RequestMapping("/appPerson")
public class AppPersonController {

    @Autowired
    private AppPersonService appPersonService;

    @PostMapping("/addAppPerson")
    @ApiOperation("新增人员地库信息")
    public ResultVO<AppPersonVO> addAppPerson(@RequestBody AppPersonSaveDTO saveDTO){
        AppPersonVO result = appPersonService.saveAppPerson(saveDTO);
        return new ResultVO<AppPersonVO>(SuccessEnums.SAVE_SUCCESS, result);
    }

    @GetMapping("/getAppPerson")
    @ApiOperation("通过手机号查询人员信息")
    public ResultVO<AppPersonVO> getAppPerson(AppPersonQueryDTO queryDTO){
        AppPersonVO result = appPersonService.getAppPerson(queryDTO);
        return new ResultVO<AppPersonVO>(SuccessEnums.QUERY_SUCCESS, result);
    }

    @ApiOperation("获取预检分诊信息")
    @GetMapping("/list")
    public ResultVO<PageInfo<AppPersonWxVO>> list(AppPersonQueryDTO queryDTO) {
        if(StringUtils.isEmpty(queryDTO.getYjfzTime())){
            queryDTO.setYjfzTime(DateUtils.getDate());
        }
        PageInfo<AppPersonWxVO> list = appPersonService.getList(queryDTO);
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,list);
    }
}
