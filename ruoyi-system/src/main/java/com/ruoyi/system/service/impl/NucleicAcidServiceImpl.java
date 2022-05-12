package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.dto.NucleicAcidNumDTO;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.NucleicAcidRecord;
import com.ruoyi.system.entity.vo.NucleicAcidVo;
import com.ruoyi.system.mapper.NucleicAcidMapper;
import com.ruoyi.system.mapper.NucleicAcidRecordMapper;
import com.ruoyi.system.service.INucleicAcidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class NucleicAcidServiceImpl extends ServiceImpl<NucleicAcidMapper,NucleicAcid> implements INucleicAcidService {

    @Autowired
    private NucleicAcidMapper mapper;
    @Autowired
    private NucleicAcidRecordMapper recordMapper;

    @Override
    public List<NucleicAcid> getList(NucleicAcid nucleicAcid) {
        QueryWrapper<NucleicAcid> queryWrapper = new QueryWrapper<>();
        if(nucleicAcid.getName()!=null){
            queryWrapper.like("name",nucleicAcid.getName());
        }
        queryWrapper.eq("type",nucleicAcid.getType());
        if(nucleicAcid.getResult()!=null){
            queryWrapper.eq("result",nucleicAcid.getResult());
        }
        List<NucleicAcid> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<NucleicAcid> getCountList() {
        List<NucleicAcid> list = mapper.getCountList();
        int i = 0;
        for (NucleicAcid acid : list) {
            if(acid.getPatientId()!=null){
                acid.setShortMessage("疫情告警!!!");
                NucleicAcidRecord record = new NucleicAcidRecord();
                record.setPatientId(acid.getPatientId());
                record.setNewsStatus(1);
                recordMapper.insert(record);
                i++;
            }
        }
        return list;
    }

    @Override
    public List<NucleicAcid> getPageList(String type) {
        String Monday = getStartOrEndDayOfWeek(null,true);
        String Sunday = getStartOrEndDayOfWeek(null,false);
        return mapper.getPageList(Monday,Sunday,type);
    }
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


    @Override
    public List<NucleicAcidVo> getNucleicAcid() {
        return mapper.getNucleicAcid();
    }

    @Override
    public List<NucleicAcidVo> getAntibody() {
        return mapper.getAntibody();
    }



    @Override
    public NucleicAcidNumDTO getNucleicAcidNum() {
        NucleicAcidNumDTO nucleicAcidNumDTO = new NucleicAcidNumDTO();
        QueryWrapper<NucleicAcid> trueQueryWrapper = new QueryWrapper<>();
        trueQueryWrapper.eq("result","阳性");
        List<NucleicAcid> trueList = mapper.selectList(trueQueryWrapper);
        nucleicAcidNumDTO.setDiagnosis(trueList.size());
        QueryWrapper<NucleicAcid> waitQueryWrapper = new QueryWrapper<>();
        waitQueryWrapper.eq("clinical_diagnosis","发热");
        waitQueryWrapper.eq("result","阴性");
        List<NucleicAcid> waitList = mapper.selectList(waitQueryWrapper);
        nucleicAcidNumDTO.setFever(waitList.size());
        return nucleicAcidNumDTO;
    }

    @Override
    public NucleicAcidNumDTO getAddNucleicAcidNum() {
        NucleicAcidNumDTO nucleicAcidNumDTO = new NucleicAcidNumDTO();
        String date = LocalDate.now().toString();
        String year=date.substring(0,4);
        String month=date.substring(5,7);
        String day=date.substring(8,10);
        int days = Integer.parseInt(day)-1;
        day = String.valueOf(days);
        String testDate = year+month+day;

        QueryWrapper<NucleicAcid> trueQueryWrapper = new QueryWrapper<>();
        trueQueryWrapper.eq("result","阳性");
        trueQueryWrapper.eq("test_date",testDate);
        List<NucleicAcid> trueList = mapper.selectList(trueQueryWrapper);
        nucleicAcidNumDTO.setDiagnosis(trueList.size());

        QueryWrapper<NucleicAcid> waitQueryWrapper = new QueryWrapper<>();
        waitQueryWrapper.eq("clinical_diagnosis","发热");
        waitQueryWrapper.eq("result","阴性");
        waitQueryWrapper.eq("test_date",testDate);
        List<NucleicAcid> waitList = mapper.selectList(waitQueryWrapper);
        nucleicAcidNumDTO.setFever(waitList.size());

        //距离上次0新增天数
        QueryWrapper<NucleicAcid> lastQueryWrapper = new QueryWrapper<>();
        lastQueryWrapper.select("max(test_date) as testDate");
        NucleicAcid nucleicAcid = getOne(lastQueryWrapper);
        nucleicAcidNumDTO.setClear(Integer.parseInt(testDate) - Integer.parseInt(nucleicAcid.getTestDate()));
        return nucleicAcidNumDTO;
    }

}
