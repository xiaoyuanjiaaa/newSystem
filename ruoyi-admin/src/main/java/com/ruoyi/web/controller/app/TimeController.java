package com.ruoyi.web.controller.app;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private ISysDictDataService dictDataService;

    //判断当前时间是不是规定时间
    @GetMapping("/judgedTime")
    public Boolean judged(){
        String start=null;
        String end=null;
        int intStart=0;
        int intEnd=0;
        int intNow=0;
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("app_time");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        for (int i = 0; i < sysDictDataList.size(); i++) {
            start = sysDictDataList.get(0).getDictValue();//开始时间
            end = sysDictDataList.get(sysDictDataList.size()-1).getDictValue();//结束时间
        }
        //判断
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        String now = df.format(new Date());// new Date()为获取当前系统时间
        String strStart = start.substring(0,2); //05
        String strEnd = end.substring(0,2); //11
        String strNow = now.substring(0,2); //当前时间
        try {
            intStart = Integer.parseInt(strStart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            intEnd = Integer.parseInt(strEnd);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            intNow = Integer.parseInt(strNow);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if(intStart<=intNow && intNow<=intEnd){
            return true;
        }
        return false;
    }
}
