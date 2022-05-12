package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.dto.NegativePressureWardDTO;
import com.ruoyi.system.entity.NegativePressureWard;
import com.ruoyi.system.mapper.NegativePressureWardMapper;
import com.ruoyi.system.service.INegativePressureWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
public class NegativePressureWardServiceImpl extends ServiceImpl<NegativePressureWardMapper, NegativePressureWard> implements INegativePressureWardService {

    @Autowired
    NegativePressureWardMapper mapper;

    @Override
    public NegativePressureWardDTO negativePressureWard() {

        NegativePressureWardDTO wardDTO = new NegativePressureWardDTO();
        Calendar cale = Calendar.getInstance();

        //当天
        String toDays = LocalDate.now().toString();
        String beginToDays = toDays + " 00" + "-00" + "-00";
        String endToDays = toDays + " 23" + "-59" + "-59";
        List<NegativePressureWard> toDaysList = mapper.getList(beginToDays,endToDays);
        wardDTO.setToday(toDaysList.size());

        //本周
        String beginTime = getStartOrEndDayOfWeek(null,true);
        String endTime = getStartOrEndDayOfWeek(null,false);
        List<NegativePressureWard> weekList = mapper.getList(beginTime,endTime);
        wardDTO.setWeek(weekList.size());


        //本月
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        List<NegativePressureWard> monthList = mapper.getList(firstday,lastday);
        wardDTO.setMonth(monthList.size());

        //本年
        int year = cale.get(Calendar.YEAR);
        String beginYear = year + "-01" + "-01";
        String endYear = year + "-12" + "-31";
        List<NegativePressureWard> yearList = mapper.getList(beginYear,endYear);
        wardDTO.setYear(yearList.size());

        return wardDTO;
    }
    /**
     * 周
     * @param today
     * @param isFirst
     * @return
     */
    public static String getStartOrEndDayOfWeek(LocalDate today, Boolean isFirst){
        LocalDate resDate = LocalDate.now();
        if (today == null) {
            today = resDate;
        }
        DayOfWeek week = today.getDayOfWeek();
        int value = week.getValue();
        if (isFirst) {
            resDate = today.minusDays(value - 1);
        } else {
            resDate = today.plusDays(7 - value);
        }
        return resDate.toString();
    }



}
