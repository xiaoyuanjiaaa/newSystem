package com.ruoyi.web.controller.largeScreen;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.dto.*;
import com.ruoyi.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"数据大屏"})
@RequestMapping("/largeScreen")
@AllArgsConstructor
public class largeScreenController extends BaseController {

    IAppHealthReportService reportService;

    IMaterialSuppliesService suppliesService;

    INegativePressureWardService wardService;

    ISysUserService userService;

    IHisInpatientInfoService inpatientInfoService;

    INucleicAcidService acidService;

    IAppVisitRecordService visitRecordService;


    @ApiOperation("物资储备/出库详情")
    @GetMapping("/getSuppliesScreen")
    public TableDataInfo getSuppliesScreen(){
        startPage();
        List<MaterialSuppliesDTO> list = suppliesService.getSuppliesScreen();
        return getDataTable(list);
    }


    @ApiOperation("物资库存")
    @GetMapping("/getMaterialStock")
    public TableDataInfo getMaterialStock(){
        List<MaterialSuppliesStockDTO> list = suppliesService.getMaterialStock();
        return getDataTable(list);
    }


    @ApiOperation("进出负压病房患者人次")
    @GetMapping("/wardDTOResultVO")
    public ResultVO<NegativePressureWardDTO> wardDTOResultVO(){
        NegativePressureWardDTO wardDTO = wardService.negativePressureWard();
        if(ObjectUtil.isNotEmpty(wardDTO)){
            return new ResultVO<NegativePressureWardDTO>(SuccessEnums.QUERY_SUCCESS,wardDTO);
        }
        throw new CustomException("查询失败");
    }


    @GetMapping("/getViewList")
    @ApiOperation("每日填报异常人员")
    public TableDataInfo getViewList(){
        List<AppHealthReportViewDTO> list = reportService.getViewList();
        return getDataTable(list);
    }


    @ApiOperation("院内人员数")
    @GetMapping("/getHospitalNum")
    private ResultVO<HospitalPersonnelDTO> getHospitalNum(){
        HospitalPersonnelDTO personnelDTO = inpatientInfoService.getListNum();
        if(ObjectUtil.isNotEmpty(personnelDTO)){
            return new ResultVO<HospitalPersonnelDTO>(SuccessEnums.QUERY_SUCCESS,personnelDTO);
        }
        throw new CustomException("查询失败");
    }


    @ApiOperation("新冠疫情信息数")
    @GetMapping("/getNucleicAcidNum")
    private ResultVO<NucleicAcidNumDTO> getNucleicAcidNum(){
        NucleicAcidNumDTO numDTO = acidService.getNucleicAcidNum();
        if (ObjectUtil.isNotEmpty(numDTO)){
            return new ResultVO<NucleicAcidNumDTO>(SuccessEnums.QUERY_SUCCESS,numDTO);
        }
        throw new CustomException("查询失败");
    }


    @ApiOperation("新冠疫情新增数")
    @GetMapping("/getAddNucleicAcidNum")
    private ResultVO<NucleicAcidNumDTO> getAddNucleicAcidNum(){
        NucleicAcidNumDTO numDTO = acidService.getAddNucleicAcidNum();
        if (ObjectUtil.isNotEmpty(numDTO)){
            return new ResultVO<NucleicAcidNumDTO>(SuccessEnums.QUERY_SUCCESS,numDTO);
        }
        throw new CustomException("查询失败");
    }




    @ApiOperation("人员流动最频繁区域top5")
    @GetMapping("/getVisitRecordNum")
    public TableDataInfo getVisitRecordNum(){
        List<AppVisitRecordNumDTO> list = visitRecordService.getListNum();
        return getDataTable(list);
    }

}
