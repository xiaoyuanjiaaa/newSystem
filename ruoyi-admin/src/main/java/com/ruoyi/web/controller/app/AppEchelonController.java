package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.dto.AppEchelonDTO;
import com.ruoyi.system.dto.AppEchelonUserDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.service.IAppEchelonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 20:52
 */
@RestController
@Api(tags = {"梯队管理"})
@RequestMapping("/appAppEchelon")
public class AppEchelonController extends BaseController {

    @Autowired
    private IAppEchelonService appEchelonService;

    @GetMapping("/pageAppEchelon")
    @ApiOperation("列表分页查询")
    public ResultVO<IPage<AppEchelon>> pageAppEchelon(AppEchelonDTO appEchelonDTO){
        IPage<AppEchelon> appEchelonIPage = appEchelonService.listPage(appEchelonDTO);
        return new ResultVO<IPage<AppEchelon>>(SuccessEnums.QUERY_SUCCESS,appEchelonIPage);
    }

    @GetMapping("/getAppEchelon")
    @ApiOperation("查询梯队详情")
    public ResultVO<AppEchelon> getAppDuty(Long id){
        AppEchelon appEchelon = appEchelonService.getByIdAndUser(id);
        if (appEchelon == null){
            throw new CustomException("未查到指定职责",500);
        }
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,appEchelon);
    }
    @PostMapping("/addEchelon")
    @ApiOperation("新增或修改梯队")
    public ResultVO<Boolean> addEchelon(@RequestBody AppEchelon appEchelon) {
        QueryWrapper<AppEchelon> queryWrapper = new QueryWrapper<>();
        if (appEchelon.getId() != null) {
            if (!appEchelonService.judge(appEchelon)) {
                throw new CustomException("梯队已有人员大于要修改的人员容量");
            }
            appEchelon.setUpdateTime(new Date());
            appEchelon.setUpdateBy(getUsername());
            queryWrapper.ne("id", appEchelon.getId());
        }
        queryWrapper.eq("echelon_name", appEchelon.getEchelonName());
        queryWrapper.eq("echelon_type",appEchelon.getEchelonType());
        int count = appEchelonService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("梯队名称重复");
        }
        appEchelon.setCreateTime(new Date());
        appEchelon.setCreateBy(getUsername());
        boolean b = appEchelonService.saveOrUpdateAndUser(appEchelon);
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增或修改失败");
    }

    @PostMapping("/addEchelonUser")
    @ApiOperation("新增梯队人员")
    public ResultVO<Boolean> addEchelonUser(@RequestBody AppEchelonUserDTO appEchelonUserDTO){
        boolean b = appEchelonService.saveUser(appEchelonUserDTO);
        return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS,b);
    }

    @DeleteMapping("/deleteEchelonUser")
    @ApiOperation("删除梯队人员")
    public ResultVO<Boolean> deleteEchelonUser(Long id){
        boolean b = appEchelonService.deleteEchelonUser(id);
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("删除失败");
    }
}
