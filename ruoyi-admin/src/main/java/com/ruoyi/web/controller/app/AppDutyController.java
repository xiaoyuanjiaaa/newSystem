package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppDutyDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.service.IAppDutyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:27
 */
@RestController
@Api(tags = {"职责管理"})
@RequestMapping("/appAppDuty")
public class AppDutyController extends BaseController {

    @Autowired
    private IAppDutyService appDutyService;

    @PostMapping("/addAppDuty")
    @ApiOperation("新增职责")
    public ResultVO<Boolean> addAppDuty(@RequestBody AppDuty appDuty){
        QueryWrapper<AppDuty> queryWrapper=new QueryWrapper<>();
        if(appDuty.getId()!=null){
            queryWrapper.ne("id",appDuty.getId());
        }
        queryWrapper.eq("duty_name",appDuty.getDutyName());
        int count = appDutyService.count(queryWrapper);
        if(count>0){
            throw new CustomException("职责名称重复");
        }
        boolean b = appDutyService.saveOrUpdate(appDuty);
        if (b){
            if (appDuty.getId() != null){
                appDutyService.updateRosterUser(appDuty);
            }
            return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增失败");
    }

    @GetMapping("/pageAppDuty")
    @ApiOperation("列表分页查询")
    public ResultVO<IPage<AppDuty>> pageEpidemicUser( AppDutyDTO appDutyDTO){
        IPage<AppDuty> appDutyIPage = appDutyService.listPage(appDutyDTO);
        System.out.println(appDutyIPage);
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,appDutyIPage);
    }



    @GetMapping("/getAppDuty")
    @ApiOperation("查询职责详情")
    public ResultVO<AppDuty> getAppDuty(Long id){
        AppDuty appDuty = appDutyService.getById(id);
        if (appDuty == null){
            throw new CustomException("未查到指定职责",500);
        }
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,appDuty);
    }

    @DeleteMapping("/deleteAppDuty")
    @ApiOperation("删除职责")
    public ResultVO<Boolean> deleteAppDuty(String ids){
        List<String> idOne = StringUtils.str2List(ids, ",", true, true);
        for (String s : idOne) {Long.parseLong(s);}
        boolean b = appDutyService.removeByIds(idOne);
        if (b){return  new ResultVO(SuccessEnums.DELETE_SUCCESS,b);}
        throw new CustomException("删除失败");
    }



}
