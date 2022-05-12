package com.ruoyi.web.controller.app;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.SysRegisterDto;
import com.ruoyi.system.entity.SysRegister;
import com.ruoyi.system.service.ISysRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jianguo
 * @Date: 2021/10/19 15:56
 */
@RestController
@Api(tags = {"注册信息管理"})
@RequestMapping("/register/wy")
public class SysRegisterWYController extends BaseController {

    @Autowired
    private ISysRegisterService registerService;

    @GetMapping("/tableHead")
    @ApiOperation("动态表头")
    public ResultVO tableHead(){
        List<JSONObject> lsit = registerService.tableHeadNew();
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,lsit);
    }

    @GetMapping("/pageRegister")
    @ApiOperation("列表分页查询")
    public ResultVO<IPage<JSONObject>> pageEpidemicUser(SysRegisterDto sysRegisterDto){
        IPage<SysRegister> registerIPage = registerService.listPage(sysRegisterDto);
        IPage<JSONObject> page = registerService.setParams(registerIPage);
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,page);
    }

    @GetMapping("/detail")
    @ApiOperation("查询详情")
    public ResultVO<SysRegister> detail(Long id){
        if (id == null){
            throw new CustomException("请传入对应数据id");
        }
        return new ResultVO(SuccessEnums.QUERY_SUCCESS,registerService.getById(id));
    }

}
