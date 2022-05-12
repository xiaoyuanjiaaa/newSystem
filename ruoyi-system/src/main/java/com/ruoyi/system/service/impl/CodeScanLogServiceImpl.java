package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.dto.AppRouteReportQueryDTO;
import com.ruoyi.system.dto.CodeScanLogDTO;
import com.ruoyi.system.dto.CodeScanLogQueryDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.entity.vo.CodeScanLogVo;
import com.ruoyi.system.excel.CodeScanLogExcel;
import com.ruoyi.system.mapper.CodeScanLogMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.vo.AppHealthReportVO;
import com.ruoyi.system.vo.AppRouteReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description  
 * @Author  dll
 * @Date 2021-08-14 02:09 
 */
@Service
public class CodeScanLogServiceImpl extends ServiceImpl<CodeScanLogMapper, CodeScanLog> implements ICodeScanLogService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private IAppHealthReportService reportService;
    @Autowired
    private ITopicDataService topicDataService;
    @Autowired
    private ISysDeptService deptService;

    @Override
    public CodeScanLogVo saveInfo(HttpServletRequest request, CodeScanLogDTO saveDTO){
        //1.根据手机号获取该员工基本信息
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().eq("phonenumber",saveDTO.getPhone()));
        if(user == null){
            throw new CustomException(FailEnums.USER_NOT_FOUND.getMsg());
        }
        //根据dept_id获取部门信息
        SysDept sysDept = deptService.getOne(new QueryWrapper<SysDept>().eq("dept_id",user.getDeptId()));
        if(sysDept == null){
            throw new CustomException(FailEnums.DEPT_NOT_FUND.getMsg());
        }

        //2.根据姓名和身份证获取苏康码状态
        String result = systemService.getSkmInfoByIdNum(request,user.getIdNum(), user.getNickName());
//        String result = "green";
        //绿色
        Integer colour = 0;
        if(result.equals("green") || result.equals("未知")){
            colour = -16744448;
        }else if(result.equals("yellow")){
            colour = 16768605;
        }else if(result.equals("red")){
            colour = -65536;
        }
        //生成二维码返回base64
        String qrcodeType = "3";
        String str = systemService.outQrCode(null,qrcodeType,user.getNickName(), user.getIdNum(), user.getPhonenumber(), colour);

        //3.获取每日填报信息
        AppHealthReportVO report = reportService.getInfoByPersonId(user.getPersonId());
        if(report == null || report.getReportId() == null){
            throw new CustomException(FailEnums.REPORT_NOT_FUND.getMsg());
        }
        //4.根据reportId找到中间表
        List<TopicData> topicDataList = topicDataService.list(new QueryWrapper<TopicData>().eq("report_id",report.getReportId()));
        JSONArray array = new JSONArray();
        topicDataList.stream().forEach(t->{
            if(StringUtils.isEmpty(t.getChineseName())){
                return;
            }
            JSONObject json = new JSONObject();
            //体温检测情况
            if(t.getChineseName().equals("体温检测情况") && !t.getChineseValue().equals("正常")){
                json.put("chinessName",t.getChineseName());
                json.put("chinessValue",t.getChineseValue());
            }
            //昨日核酸检测情况
            if(t.getDetailId() != null && t.getDetailId().longValue() == 16L && t.getChineseValue().equals("阳性")){
                json.put("chinessName",t.getChineseName());
                json.put("chinessValue",t.getChineseValue());
            }
            //近28天内，家庭成员或同行人员是否有流行病学史或密接史？
            if(t.getDetailId() != null && t.getDetailId().longValue() == 8L && t.getChineseValue().contains("有")){
                json.put("chinessName",t.getChineseName());
                json.put("chinessValue",StringUtils.isNotEmpty(report.getRelationEpidemicRemark())?report.getRelationEpidemicRemark():"有");
            }
            //近28天内，本人是否有流行病学史或密接史？
            if(t.getDetailId() != null && t.getDetailId().longValue() == 7L && t.getChineseValue().contains("有")){
                json.put("chinessName",t.getChineseName());
                json.put("chinessValue",StringUtils.isNotEmpty(report.getEpidemicRemark())?report.getEpidemicRemark():"有");
            }
            //有无其他如：干咳、鼻塞、流涕、咽痛等呼吸道症状、腹泻等消化道症状、乏力、肌肉痛、结膜炎、嗅觉味觉减退等症状
            if(t.getDetailId() != null && t.getDetailId().longValue() == 6L && t.getChineseValue().contains("有")){
                json.put("chinessName",t.getChineseName());
                json.put("chinessValue",StringUtils.isNotEmpty(report.getSymptomRemark())?report.getSymptomRemark():"有");
            }
            if(ObjectUtil.isNotEmpty(json))array.add(json);
        });
        //保存数据到数据库
        CodeScanLogVo vo = CodeScanLogVo.builder()
                .name(user.getNickName())
                .phone(user.getPhonenumber())
                .door(saveDTO.getDoor())
                .idNum(user.getIdNum())
                .deptId(user.getDeptId())
                .deptName(sysDept.getDeptName())
                .skmQrcode(str)
                .skmRemark(StringUtils.isNotEmpty(str) && colour == -16744448?"正常":result)
                .reportRemark(ObjectUtil.isNotEmpty(array)?array.toJSONString():"")
                .build();
        CodeScanLog codeScanLog = new CodeScanLog();
        BeanUtils.copyProperties(vo,codeScanLog);
        this.save(codeScanLog);
        return vo;
    }


    @Override
    public IPage<CodeScanLogVo> getPageInfo(CodeScanLogQueryDTO queryDTO) {
        QueryWrapper<CodeScanLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryDTO.getName())) {
            queryWrapper.like("name", queryDTO.getName());
        }
        if (StringUtils.isNotEmpty(queryDTO.getPhone())) {
            queryWrapper.like("phone",queryDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(queryDTO.getDoor())) {
            queryWrapper.like("door",queryDTO.getDoor());
        }
        if(queryDTO.getScanStartTime()!=null){
            queryWrapper.ge("create_time",queryDTO.getScanStartTime());
        }
        if(queryDTO.getScanEndTime()!=null){
            queryWrapper.le("create_time",queryDTO.getScanEndTime());
        }
        queryWrapper.select(CodeScanLog.class, info -> !info.getColumn().equals("skm_qrcode"));
        queryWrapper.orderByDesc("create_time");
        IPage<CodeScanLog> pageInfo = new Page<>();
        pageInfo.setCurrent(queryDTO.getPageNum());
        pageInfo.setSize(queryDTO.getPageSize());
        IPage<CodeScanLog> result = page(pageInfo, queryWrapper);
        IPage<CodeScanLogVo> res = result.convert(t -> {
            CodeScanLogVo vo = new CodeScanLogVo();
            BeanUtils.copyProperties(t, vo);
            return vo;
        });
        return res;
    }

    @Override
    public List<CodeScanLogExcel> export(CodeScanLogQueryDTO queryDTO){
        QueryWrapper<CodeScanLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryDTO.getName())) {
            queryWrapper.like("name", queryDTO.getName());
        }
        if (StringUtils.isNotEmpty(queryDTO.getPhone())) {
            queryWrapper.like("phone",queryDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(queryDTO.getDoor())) {
            queryWrapper.like("door",queryDTO.getDoor());
        }
        if(queryDTO.getScanStartTime()!=null){
            queryWrapper.ge("create_time",queryDTO.getScanStartTime());
        }
        if(queryDTO.getScanEndTime()!=null){
            queryWrapper.le("create_time",queryDTO.getScanEndTime());
        }
        queryWrapper.select(CodeScanLog.class, info -> !info.getColumn().equals("skm_qrcode"));
        queryWrapper.orderByDesc("create_time");
        List<CodeScanLog> list = this.list(queryWrapper);
        List<CodeScanLogExcel> excelList = new ArrayList();
        list.stream().forEach(t->{
            CodeScanLogExcel excel = new CodeScanLogExcel();
            BeanUtils.copyProperties(t,excel);
            excelList.add(excel);
        });
        return excelList;
    }
}
