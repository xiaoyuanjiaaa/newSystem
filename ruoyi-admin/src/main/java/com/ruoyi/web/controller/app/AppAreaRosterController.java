package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.dto.AreaRosterUserDTO;
import com.ruoyi.system.dto.AreaRosterUserQueryDTO;
import com.ruoyi.system.dto.UserRosterDTO;
import com.ruoyi.system.entity.AppDuty;
import com.ruoyi.system.entity.AppEchelon;
import com.ruoyi.system.entity.AppRoster;
import com.ruoyi.system.entity.WorkPlaceFrequency;
import com.ruoyi.system.excel.AppRosterUserExcel;
import com.ruoyi.system.excel.AreaRosterUserExcel;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AreaRosterUserVO;
import com.ruoyi.system.vo.UserRosterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = {"片区人员排班管理"})
@RequestMapping("/appAreaRoster")
public class AppAreaRosterController extends BaseController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IAppHealthReportService appHealthReportService;
    @Autowired
    private IAppRosterService rosterService;
    @Autowired
    private IAreaRosterUserService areaRosterUserService;
    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private IAppEchelonService appEchelonService;
    @Autowired
    private IAppDutyService appDutyService;
    /**
     * 获取片区人员列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {

        // 获取当前的用户
        Long deptId = user.getDeptId();
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if(sysDept==null){
                        throw new CustomException("部门管理员请先绑定片区");
                    }else if(sysDept!=null) {
                        user.setDeptId(sysDept.getDeptId());
                    }
                }else {
                    user.setDeptId(null);
                }
            }
        }
        //如果传了deptId，强制赋值
        if(deptId!=null){
            user.setDeptId(deptId);
        }
        startPage();
        List<SysUser> list = userService.selectUserRosterList(user);
        return getDataTable(list);
    }

    @GetMapping("/appRoster")
    @ApiOperation("片区负责人查看创建的班次")
    public TableDataInfo pageList(AppRoster appRoster){
        QueryWrapper<AppRoster> queryWrapper=new QueryWrapper<>();
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            queryWrapper.eq("create_by",currentUser.getNickName());
            queryWrapper.eq ( "type",appRoster.getType () );
        }
        List<AppRoster> list = rosterService.list(queryWrapper);
        return getDataTable(list);
    }

    @GetMapping("/workplace")
    public TableDataInfo workplace(){
        QueryWrapper<WorkPlaceFrequency> queryWrapper=new QueryWrapper<>();
        List<WorkPlaceFrequency> list = workPlaceService.list(queryWrapper);
        return getDataTable(list);
    }


    @PostMapping("/add")
    @ApiOperation("新增防疫人员排班信息")
    public ResultVO<Integer> addEpidemicRoster(@RequestBody AreaRosterUserDTO areaRosterUserDTO){
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser) && loginUser.getUser().isAdmin())
        {
            throw new CustomException("请片区负责人对成员进行排班");
        }
        int b = areaRosterUserService.addAreaRosterUser(areaRosterUserDTO);
        if (b==1){ return new ResultVO<Integer>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("新增失败");
    }

    @DeleteMapping("/delete")
    public ResultVO<Boolean> delete(@RequestParam Long ids){
//        List<Long>  list=new ArrayList<>();
//        for (Long id : ids) {
//            list.add(id);
//        }
        boolean b = areaRosterUserService.removeById(ids);
        if (b){ return new ResultVO<Boolean>(SuccessEnums.SAVE_SUCCESS, b);}
        throw new CustomException("删除失败");
    }

    @GetMapping("/rosterUserList")
    @ApiOperation("防疫人员排班信息")
    public ResultVO<PageInfo<AreaRosterUserVO>> getList(AreaRosterUserQueryDTO areaRosterUserQueryDTO){
        Long deptId = areaRosterUserQueryDTO.getDeptId();
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if(sysDept==null){
                        throw new CustomException("部门管理员请先绑定片区");
                    }else if(sysDept!=null) {
                        areaRosterUserQueryDTO.setDeptId(sysDept.getDeptId());
                    }
                }else {
                    areaRosterUserQueryDTO.setDeptId(null);
                }
            }
        }
        //如果传了deptId，强制赋值
        if(deptId!=null){
            areaRosterUserQueryDTO.setDeptId(deptId);
        }
        return new ResultVO<PageInfo<AreaRosterUserVO>>(SuccessEnums.QUERY_SUCCESS,areaRosterUserService.pageList(areaRosterUserQueryDTO));

    }

    //导出
    @GetMapping("/export")
    public AjaxResult export(AreaRosterUserQueryDTO areaRosterUserQueryDTO){
        Long deptId = areaRosterUserQueryDTO.getDeptId();
        // 获取当前的用户
        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，导出全部，如果是部门管理员只导出当前片区的
            if (StringUtils.isNotNull(currentUser))
            {
                boolean deptAdmin = appHealthReportService.isDeptAdmin(currentUser);
                if(deptAdmin){
                    SysDept sysDept = deptService.selectDeptByLeaderId(currentUser.getPersonId());
                    if(sysDept==null){
                        throw new CustomException("部门管理员请先绑定片区");
                    }else if(sysDept!=null) {
                        areaRosterUserQueryDTO.setDeptId(sysDept.getDeptId());
                    }
                }else {
                    areaRosterUserQueryDTO.setDeptId(null);
                }
            }
        }
        //如果传了deptId，强制赋值
        if(deptId!=null){
            areaRosterUserQueryDTO.setDeptId(deptId);
        }
        List<AreaRosterUserExcel> appRosterUserExcels = areaRosterUserService.exportList(areaRosterUserQueryDTO);
        ExcelUtil<AreaRosterUserExcel> util = new ExcelUtil<AreaRosterUserExcel>(AreaRosterUserExcel.class);
        return util.exportExcel(appRosterUserExcels, "片区人员排班表");
    }


    @GetMapping("/echelonList")
    public TableDataInfo echelonList(){
        QueryWrapper<AppEchelon> queryWrapper=new QueryWrapper<>();
        List<AppEchelon> list = appEchelonService.list(queryWrapper);
        return getDataTable(list);
    }

    @GetMapping("/dutyList")
    public TableDataInfo dutyList(){
        QueryWrapper<AppDuty> queryWrapper=new QueryWrapper<>();
        List<AppDuty> list = appDutyService.list(queryWrapper);
        return getDataTable(list);
    }
}
