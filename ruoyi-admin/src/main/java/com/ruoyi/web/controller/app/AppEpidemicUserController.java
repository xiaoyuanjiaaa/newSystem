package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.AppEpidemicUserDTO;
import com.ruoyi.system.dto.EpidemicUserDTO;
import com.ruoyi.system.dto.UserRosterDTO;
import com.ruoyi.system.dto.UserRosterDTO;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.entity.AppEpidemicUser;
import com.ruoyi.system.service.IAppEpidemicUserService;
import com.ruoyi.system.vo.UserRosterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 19:54
 */
@RestController
@Api(tags = {"防疫人员管理"})
@RequestMapping("/appEpidemic")
public class AppEpidemicUserController extends BaseController {

    @Autowired
    private IAppEpidemicUserService appEpidemicUserService;

    @GetMapping("/pageEpidemicUser")
    @ApiOperation("列表分页查询")
    public ResultVO<IPage<AppEpidemicUser>> pageEpidemicUser(AppEpidemicUserDTO epidemicUserDTO){
        IPage iPage = appEpidemicUserService.listPage(epidemicUserDTO);
        return new ResultVO<IPage<AppEpidemicUser>>(SuccessEnums.QUERY_SUCCESS,iPage);
    }


    @GetMapping("/getEpidemicUser")
    @ApiOperation("获取指定防疫人员信息")
    public AppEpidemicUser getEpidemicUser(Long id){
        AppEpidemicUser epidemicUser = appEpidemicUserService.getEpidemicUser(id);
        return epidemicUser;
    }

    @PostMapping("/addEpidemic")
    @ApiOperation("新增或修改防疫人员")
    public ResultVO<Boolean> addEchelon(@RequestBody AppEpidemicUser appEpidemicUser){
        boolean b = appEpidemicUserService.saveOrUpdate(appEpidemicUser);
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增或修改失败");
    }

    @PostMapping("/addEpidemicRoster")
    @ApiOperation("新增防疫人员排班信息")
    public ResultVO<Boolean> addEpidemicRoster(@RequestBody UserRosterDTO userRosterDTO){

        boolean b = appEpidemicUserService.addEpidemicRoster(userRosterDTO);
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增失败");
    }

    @PostMapping("/editStatus")
    @ApiOperation("强制修改状态")
    public ResultVO<Boolean> editStatus(@RequestBody EpidemicUserDTO epidemicUserDTO){
        List<Long> userIds = epidemicUserDTO.getUserIds();
        boolean b=false;
        if(userIds.size()>0){
            for (Long userId : userIds) {
                QueryWrapper<AppEpidemicUser> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("user_id",userId);
                AppEpidemicUser one = appEpidemicUserService.getOne(queryWrapper);
                one.setWorkStatus(epidemicUserDTO.getWorkStatus());
                b = appEpidemicUserService.saveOrUpdate(one);
            }
        }
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增或修改失败");
    }

//    @PostMapping("/addEpidemicRoster")
//    @ApiOperation("新增防疫人员排班信息")
//    public ResultVO<Boolean> addEpidemicRoster(@RequestBody UserRosterDTO userRosterDTO){
//        boolean b = appEpidemicUserService.addEpidemicRoster(userRosterDTO);
//        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
//        throw new CustomException("新增失败");
//    }



}
