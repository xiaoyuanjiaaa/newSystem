package com.ruoyi.web.controller.app;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.system.dto.AppDestinationQueryDTO;
import com.ruoyi.system.entity.AppDestination;
import com.ruoyi.system.entity.WorkPlaceFrequency;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.WorkPlaceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:43
 */
@RestController
@RequestMapping("/workPlace")
public class WorkPlaceController {

    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private ISysDictDataService dictDataService;

    @GetMapping("/list")
    @ApiOperation("查询工作场所列表")
    public ResultVO<List<WorkPlaceFrequency>> list(){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("work_place");
        List<SysDictData> workPlaces = dictDataService.selectDictDataList(sysDictData);
        List<WorkPlaceFrequency> list = workPlaceService.list(new QueryWrapper<WorkPlaceFrequency>());
        List<WorkPlaceFrequency> data = new ArrayList<>();
        workPlaces.stream().forEach(t->{
            WorkPlaceFrequency wf = new WorkPlaceFrequency();
            wf.setWorkPlaceName(t.getDictLabel());
            wf.setWorkPlace(t.getDictValue());
            List<WorkPlaceFrequency> l = list.stream().filter(d->d.getWorkPlace().equals(t.getDictValue())).collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(l)){
                WorkPlaceFrequency wpf = l.get(0);
                wf.setFrequencyName(wpf.getFrequencyName());
                wf.setFrequency(wpf.getFrequency());
            }
            data.add(wf);
        });
        return new ResultVO<List<WorkPlaceFrequency>>(SuccessEnums.QUERY_SUCCESS,data);
    }


    @GetMapping("/getFrequency")
    @ApiOperation("获取核酸检测频次列表")
    public ResultVO<List<SysDictData>> getFrequency(){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("frequency");
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        return new ResultVO<List<SysDictData>>(SuccessEnums.QUERY_SUCCESS,list);
    }


    @PutMapping("/saveInfo")
    @ApiOperation("保存工作场所")
    public AjaxResult saveInfo(@RequestBody WorkPlaceFrequency workPlaceFrequency){
        String frequency = workPlaceFrequency.getFrequency();
        String frequencyName = dictDataService.selectDictLabel("frequency",frequency);
        workPlaceFrequency.setFrequencyName(frequencyName);
        //防止重复提交
        WorkPlaceFrequency wf = workPlaceService.getOne(new QueryWrapper<WorkPlaceFrequency>().eq("work_place", workPlaceFrequency.getWorkPlace()));
        if(wf != null){
            workPlaceFrequency.setId(wf.getId());
        }
        workPlaceService.saveOrUpdate(workPlaceFrequency);
        return AjaxResult.success();
    }
}
