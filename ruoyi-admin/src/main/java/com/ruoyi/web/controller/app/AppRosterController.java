package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.AppEpidemicUserDTO;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.excel.AppRosterUserExcel;
import com.ruoyi.system.excel.SysUserExcel;
import com.ruoyi.system.service.IAppRosterService;
import com.ruoyi.system.service.IAppRosterUserService;
import com.ruoyi.system.vo.AppChangeReportVO;
import com.ruoyi.system.vo.UserRosterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 9:35
 */
@RestController
@Api(tags = {"防疫人员排班管理"})
@RequestMapping("/appAppRoster")
public class AppRosterController extends BaseController {

    @Autowired
    private IAppRosterService rosterService;

    @Autowired
    private IAppRosterUserService appRosterUserService;

    @GetMapping("/pageAppRoster")
    @ApiOperation("列表分页查询")
    public ResultVO<IPage<AppRoster>> pageAppRoster(AppRosterQueryDTO appEchelonDTO){
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            appEchelonDTO.setCreateBy(currentUser.getNickName());
        }
        IPage iPage = rosterService.listPage(appEchelonDTO);
        return new ResultVO<IPage<AppRoster>>(SuccessEnums.QUERY_SUCCESS,iPage);
    }

    @GetMapping("/getAppRoster")
    @ApiOperation("获取指定排班信息")
    public ResultVO<AppRoster> getAppRoster(Long id){
        AppRoster appRoster = rosterService.getById(id);
        if (appRoster == null){
            throw new CustomException("未查到指定排班信息");
        }
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,appRoster);
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation("新增或修改排班信息")
    public ResultVO<Boolean> addOrUpdate(@RequestBody AppRoster appRoster){
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            appRoster.setCreateBy(currentUser.getNickName());

            if(appRoster.getTimeEndOne() == null)appRoster.setTimeEndOne("");
            if(appRoster.getTimeEndTwo() == null)appRoster.setTimeEndTwo("");
            if(appRoster.getTimeStartOne() == null)appRoster.setTimeStartOne("");
            if(appRoster.getTimeStartTwo() == null)appRoster.setTimeStartTwo("");
            //新增条件构造器
            QueryWrapper<AppRoster> queryWrapper = new QueryWrapper<>();
            if(appRoster.getId()!=null){//若id相同，说明是编辑
                queryWrapper.ne("id",appRoster.getId());//添加条件id不同的即排除自己
            }
            queryWrapper.eq("roster_name",appRoster.getRosterName());//添加条件name相同
            queryWrapper.eq ( "type",appRoster.getType());//添加数据隔离校验
            queryWrapper.eq ( "create_by",appRoster.getCreateBy());//添加创建者权限
            int count = rosterService.count(queryWrapper);
            if(count>0){
                throw new CustomException("班次名称重复");
            }
            Boolean result = rosterService.saveOrUpdate(appRoster);
            if (result){
                if (appRoster.getId() != null){
                    List<AppRosterUser> rosterUsers = appRosterUserService.
                            list(new QueryWrapper<AppRosterUser>().eq("roster_id", appRoster.getId()));
                    if (!CollectionUtils.isEmpty(rosterUsers)){
                        for (AppRosterUser rosterUser : rosterUsers) {
                            rosterUser.setTimeEndOne(appRoster.getTimeEndOne());
                            rosterUser.setTimeStartOne(appRoster.getTimeStartOne());
                            rosterUser.setTimeStartTwo(appRoster.getTimeStartTwo());
                            rosterUser.setTimeEndTwo(appRoster.getTimeEndTwo());
                            rosterUser.setRosterName(appRoster.getRosterName());
                        }
                        boolean b = appRosterUserService.updateBatchById(rosterUsers);
                        if (!b){
                            throw new CustomException("修改人员排班信息失败");
                        }
                    }
                }
            }
            return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, result);
        }
        throw new CustomException("找不到当前用户");
    }

    @GetMapping("/list")
    @ApiOperation("防疫人员排班信息")
    public ResultVO<PageInfo<UserRosterVO>> getList(AppRosterQueryDTO appRosterQueryDTO){
        return new ResultVO<PageInfo<UserRosterVO>>(SuccessEnums.QUERY_SUCCESS,appRosterUserService.pageList(appRosterQueryDTO));
    }

    @DeleteMapping("/delete")
    public ResultVO<Boolean> delete( String ids){
        List<String> idOne = StringUtils.str2List(ids, ",", true, true);
        for (String s : idOne) {Long.parseLong(s);}
        boolean b = rosterService.removeByIds(idOne);
        if (b){return  new ResultVO(SuccessEnums.DELETE_SUCCESS,b);}
        throw new CustomException("删除失败");
    }

    @DeleteMapping("/delete/rosterUser")
    public ResultVO<Boolean> deleteRosterUser( String ids){
        List<String> idOne = StringUtils.str2List(ids, ",", true, true);
        for (String s : idOne) {Long.parseLong(s);}
        boolean b = rosterService.removeRosterUserByIds(idOne);
        if (b){return  new ResultVO(SuccessEnums.DELETE_SUCCESS,b);}
        throw new CustomException("删除失败");
    }

    //导出
    @GetMapping("/export")
    public AjaxResult export(AppRosterQueryDTO appRosterQueryDTO){
        List<AppRosterUserExcel> appRosterUserExcels = appRosterUserService.exportList(appRosterQueryDTO);
        ExcelUtil<AppRosterUserExcel> util = new ExcelUtil<AppRosterUserExcel>(AppRosterUserExcel.class);
        return util.exportExcel(appRosterUserExcels, "梯队排班表");
    }
}
