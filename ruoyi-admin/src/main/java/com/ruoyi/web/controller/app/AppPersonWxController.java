package com.ruoyi.web.controller.app;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.easyExcel.EasyExcelUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.dto.*;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.entity.AppPersonWx;
import com.ruoyi.system.entity.AppPersonWxVisit;
import com.ruoyi.system.service.AppPersonService;
import com.ruoyi.system.service.AppPersonWxService;
import com.ruoyi.system.service.AppPersonWxVisitService;
import com.ruoyi.system.service.SystemService;
import com.ruoyi.system.vo.AppPersonQueryWxVO;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import com.ruoyi.system.vo.AppPersonWxVisitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("api/appPersonWx")
@Api(tags = {"????????????????????????"})
public class AppPersonWxController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AppPersonWxController.class);

    @Value("${uploadUrl}")
    private String uploadUri;

    @Autowired
    private AppPersonWxService appPersonWxService;

    @Autowired
    private AppPersonWxVisitService appPersonWxVisitService;


    @Autowired
    private AppPersonService appPersonService;

    @Autowired
    private SystemService systemService;

    @ApiOperation("????????????????????????")
    @PostMapping("/saveInfo")
    public ResultVO<String> saveInfo(@RequestBody AppPersonWxSaveDTO saveDTO) {
        String result = appPersonWxService.saveInfo(saveDTO);
        return new ResultVO<String>(SuccessEnums.SAVE_SUCCESS, result);
    }

    @ApiOperation("????????????????????????")
    @PostMapping("/updateInfo")
    public ResultVO<AppPersonWxVO> updateInfo(@RequestBody AppPersonWxUpdateDTO updateDTO) {
        AppPersonWxVO result = appPersonWxService.updateInfo(updateDTO);
        return new ResultVO<AppPersonWxVO>(SuccessEnums.UPDATE_SUCCESS, result);
    }

    @ApiOperation("?????????????????????personId?????????????????????????????????")
    @GetMapping("/getInfo")
    public ResultVO<AppPersonWxVO> getInfo(AppPersonWxQueryDTO queryDTO) {
        log.info(queryDTO.getOpenId()+"===????????????==="+DateUtils.getTime());
        AppPersonWxVO result = appPersonWxService.getInfo(queryDTO);
        log.info(queryDTO.getOpenId()+"===????????????????????????==="+DateUtils.getTime());
        if(result!=null && result.getPersonId()!=null){
            //???????????????
            Integer colour = -16744448;
            boolean flag = false;
            String content = StringUtils.isNotEmpty(result.getContent())?result.getContent():"";
            String symptoms = StringUtils.isNotEmpty(result.getSymptoms())?result.getSymptoms():"0";
            Double number = 0d;
            try{
                number = Double.parseDouble(symptoms);
            }catch (Exception e){
                e.printStackTrace();
            }
//            BigDecimal b1 = new BigDecimal(String.valueOf(number));
//            BigDecimal b2 = new BigDecimal(String.valueOf(37.3));
//
//            String contactHistory = StringUtils.isNotEmpty(result.getContactHistory())?result.getContactHistory():"";
//            String epidemicHistory = StringUtils.isNotEmpty(result.getEpidemicHistory())?result.getEpidemicHistory():"";
//            String riskPosition = StringUtils.isNotEmpty(result.getRiskPosition())?result.getRiskPosition():"";
//            if(content.length()>1 || b1.subtract(b2).doubleValue()>0 || contactHistory.length()>0 ||
//                    epidemicHistory.length()>0 || riskPosition.length()>0){
//                flag = true;
//            }
            if(result.getFlag()){
                colour = 16768605;
            }
            //?????????????????????base64,1???????????????
            String qrcodeType = "1";
            log.info(queryDTO.getOpenId()+"===outQrCode??????==="+DateUtils.getTime());
            String str = systemService.outQrCode(null,qrcodeType,result.getPersonName(), result.getIdNum(), result.getMobile(), colour);
            log.info(queryDTO.getOpenId()+"===outQrCode??????==="+DateUtils.getTime());
            result.setYjfzQrCode(str);
        }
        return new ResultVO<AppPersonWxVO>(SuccessEnums.QUERY_SUCCESS, result);
    }

    @ApiOperation("????????????????????????????????????")
    @GetMapping("/getFamilyInfo")
    public ResultVO<List<AppPersonVO>> getFamilyInfo(Long parentPersonId) {
        List<AppPerson> result = appPersonService.list(new QueryWrapper<AppPerson>().eq("person_id",parentPersonId).or().eq("parent_person_id",parentPersonId));
        List<AppPersonVO> list = new ArrayList<>();
        result.stream().forEach(t->{
            AppPersonVO vo = new AppPersonVO();
            BeanUtils.copyProperties(t, vo);
            list.add(vo);
        });
        return new ResultVO<List<AppPersonVO>>(SuccessEnums.QUERY_SUCCESS, list);
    }

    @GetMapping("/list")
    @ApiOperation("??????????????????")
    public TableDataInfo queryList(AppPersonWxYuJianDTO queryDTO){
        PageInfo pageInfo = appPersonWxService.queryList1(queryDTO);
        //List<AppPersonQueryWxVO> queryTotal= appPersonWxService.queryTotal(queryDTO);
        //long total = queryTotal.size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("????????????");
        rspData.setRows(pageInfo.getList());
        rspData.setTotal(pageInfo.getTotal());
        return rspData;
    }

    @GetMapping("/tableHead")
    @ApiOperation("????????????")
    public List<cn.hutool.json.JSONObject> tableHead(){
       return appPersonWxService.tableHead();
    }

    @GetMapping("/export")
    public AjaxResult export(AppPersonWxYuJianDTO queryDTO){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/"+"test.xlsx");
        AjaxResult ajaxResult =  appPersonWxService.exportAppPersonWx(queryDTO,inputStream,uploadUri);
        return ajaxResult;
    }

    @GetMapping("/exportList")
    public AjaxResult exportList(AppPersonWxYuJianDTO queryDTO){
        EasyExcelUtil util = new EasyExcelUtil();
        // excel ?????????
        List<List<String>> bodyList = new ArrayList<>();
        List<cn.hutool.json.JSONObject> bodies = appPersonWxService.queryList(queryDTO);

        // excel ??????
        List<List<String>> headList = new ArrayList<>();
        List<cn.hutool.json.JSONObject> heads = appPersonWxService.tableHead();
        heads.add(0,new cn.hutool.json.JSONObject(){{
            put("prop","personName");
            put("label","??????");
        }});
        heads.add(1,new cn.hutool.json.JSONObject(){{
            put("prop","mobile");
            put("label","?????????");
        }});
        heads.add(2,new cn.hutool.json.JSONObject(){{
            put("prop","idNum");
            put("label","????????????");
        }});
        heads.add(3,new cn.hutool.json.JSONObject(){{
            put("prop","telphone");
            put("label","?????????");
        }});
        heads.add(4,new cn.hutool.json.JSONObject(){{
            put("prop","destinationName");
            put("label","?????????");
        }});
        heads.add(5,new cn.hutool.json.JSONObject(){{
            put("prop","destinationDeptName");
            put("label","????????????");
        }});
        heads.add(6,new cn.hutool.json.JSONObject(){{
            put("prop","symptoms");
            put("label","??????");
        }});
        heads.add(new cn.hutool.json.JSONObject(){{
            put("prop","source");
            put("label","????????????");
        }});
        heads.add(new cn.hutool.json.JSONObject(){{
            put("prop","createBy");
            put("label","?????????");
        }});
        heads.add(new cn.hutool.json.JSONObject(){{
            put("prop","createTime");
            put("label","????????????");
        }});

        heads.stream().forEach(t->{
            List<String> list = new ArrayList<>();
            list.add(t.getStr("label"));
            headList.add(list);
        });

        // excel ??????
        bodies.stream().forEach(t->{
            List<String> list = new ArrayList<>();
            heads.stream().forEach(h->{
                String value= t.containsKey(h.getStr("prop"))?t.getStr(h.getStr("prop")):"";
                if(h.getStr("prop").equals("createTime")){
                    value = DateUtils.parseDateToStr("yyyy-MM-dd hh:mm:ss",t.get(h.getStr("prop"), Date.class));
                }
                list.add(value);
            });
            bodyList.add(list);
        });
        return AjaxResult.success("????????????????????????", JSON.toJSONString(bodyList));
    }


    @GetMapping("/listAppointment")
    @ApiOperation("???????????????????????????")
    public TableDataInfo queryListlistAppointment(AppPersonWxYuJianDTO queryDTO){
        queryDTO.setDestination("147");

        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (StringUtils.isNull(currentUser))
            {
                TableDataInfo rspData = new TableDataInfo();
                rspData.setCode(HttpStatus.SUCCESS);
                rspData.setMsg("????????????");
                rspData.setRows(new ArrayList<>());
                rspData.setTotal(0);
                return rspData;
            }else{
                queryDTO.setPhonenumber(currentUser.getPhonenumber());
            }
        }
        PageInfo list = appPersonWxService.weChatQueryList(queryDTO);
        //List<AppPersonQueryWxVO> queryTotal= appPersonWxService.queryTotal(queryDTO);
        //long total = queryTotal.size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("????????????");
        rspData.setRows(list.getList());
        rspData.setTotal(list.getTotal());
        return rspData;
    }

    @ApiOperation("????????????????????????")
    @PostMapping("/updateStatus")
    public ResultVO<AppPersonWxVO> updateStatus(Long id,Integer appointmentStatus) {
        AppPersonWx domain = new AppPersonWx();
        domain.setId(id);
        domain.setAppointmentStatus(appointmentStatus);
        appPersonWxService.updateById(domain);
        return new ResultVO<AppPersonWxVO>(SuccessEnums.UPDATE_SUCCESS, null);
    }

    @GetMapping("/listAll")
    @ApiOperation("??????????????????")
    public TableDataInfo listAll(AppPersonWxYuJianDTO queryDTO){
//        List<cn.hutool.json.JSONObject> listOne = appPersonWxService.queryList(queryDTO);
        startPage();
        List<cn.hutool.json.JSONObject> list = appPersonWxService.queryList(queryDTO);
        List<AppPersonQueryWxVO> queryTotal= appPersonWxService.queryTotal(queryDTO);
        long total = queryTotal.size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("????????????");
        rspData.setRows(list);
        rspData.setTotal(total);
        log.info("??????????????????----------total:{}",list.size());
        return rspData;
    }


    @GetMapping("/listForPerson")
    @ApiOperation("??????????????????")
    public TableDataInfo listForPerson(AppPersonWxYuJianDTO queryDTO){
        startPage();
        queryDTO.setDestination("147");
        List<cn.hutool.json.JSONObject> list = appPersonWxService.queryList(queryDTO);
        List<AppPersonQueryWxVO> queryTotal= appPersonWxService.queryTotal(queryDTO);
        long total = queryTotal.size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("????????????");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    @GetMapping("/listForHistory")
    @ApiOperation("??????????????????")
    public TableDataInfo listForHistory(AppPersonWxYuJianVisitDTO queryDTO){
        startPage();
        List<cn.hutool.json.JSONObject> list = appPersonWxVisitService.queryList(queryDTO);
        List<AppPersonQueryWxVO> queryTotal= appPersonWxVisitService.queryTotal(queryDTO);
        long total = queryTotal.size();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("????????????");
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }


    @ApiOperation("????????????????????????")
    @PostMapping("/updateVisitStatus")
    public ResultVO<AppPersonWxVO> updateVisitStatus(Long id,@RequestBody AppPersonWxUpdateDTO updateDTO) {
        AppPersonWxVO result = appPersonWxService.updateVisitStatus(updateDTO);
        return new ResultVO<AppPersonWxVO>(SuccessEnums.UPDATE_SUCCESS, result);
    }

//    @ApiOperation("????????????????????????")
//    @PostMapping("/updateVisitStatus")
//    public ResultVO<AppPersonWxVO> updateVisitStatus(Long id,Integer isVisit) {
//        AppPersonWx domain = new AppPersonWx();
//        domain.setId(id);
//        domain.setIsVisit(isVisit);
//        domain.setVisitTime(new Date().toString());
//        appPersonWxService.updateById(domain);
//
//        AppPersonWx wx = appPersonWxService.getById(domain.getId());
//        AppPersonWxVisit visit = new AppPersonWxVisit();
//        BeanUtils.copyProperties(wx, visit);
//        visit.setAppPersonWxId(visit.getId());
//        visit.setId(IdWorker.getId()).setCreateTime(new Date());
//        visit.setUpdateTime(new Date());
//        try {
//            if(ObjectUtils.isNotEmpty(SecurityUtils.getLoginUser())){
//                visit.setUpdateUserName(SecurityUtils.getLoginUser().getUser().getUserName());
//                visit.setUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
//                visit.setUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
//            }
//        } catch (Exception e) {
//        }
//        visit.setIsVisit(2);
//        appPersonWxVisitService.saveInfo(visit);
//        return new ResultVO<AppPersonWxVO>(SuccessEnums.UPDATE_SUCCESS, null);
//    }


    @ApiOperation("?????????????????????personId?????????????????????????????????")
    @GetMapping("/getWxInfoById")
    public ResultVO<AppPersonWx> getWxInfoById(AppPersonWxQueryDTO queryDTO) {
        AppPersonWx result = appPersonWxService.getById(queryDTO.getId());
        return new ResultVO<AppPersonWx>(SuccessEnums.QUERY_SUCCESS, result);
    }

    @ApiOperation("?????????????????????personId?????????????????????????????????")
    @GetMapping("/getInfoById")
    public ResultVO<AppPersonWxVisit> getInfoById(AppPersonWxQueryDTO queryDTO) {
        return appPersonWxVisitService.detail(queryDTO);
    }
}
