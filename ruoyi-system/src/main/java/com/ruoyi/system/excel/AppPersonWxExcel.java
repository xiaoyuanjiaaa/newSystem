package com.ruoyi.system.excel;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AppPersonWxExcel {

    @Excel(name = "姓名")
    private String personName;

    @Excel(name = "手机号")
    private String mobile;

    @Excel(name = "身份证号")
    private String idNum;

    @Excel(name = "座机号")
    private String telphone;

    @Excel(name = "目的地")
    private String destination;

    @Excel(name = "具体部门")
    private String destinationDeptName;

    @Excel(name = "体温")
    private String symptoms;

    @Excel(name = "车牌号")
    private String carNum;

    @Excel(name = "是否有十大临床症状")
    private String content;

    @Excel(name = "是否有流行病史")
    private String epidemicHistory;

    @Excel(name = "是否有接触史")
    private String contactHistory;

    @Excel(name = "是否就职高风险岗位")
    private String riskPosition;

    @Excel(name = "核酸检测频次")
    private String frequency;

    @Excel(name = "最近核酸检测时间")
    private String lastTestTime;

    @Excel(name = "接种了几针")
    private String needle;

    @Excel(name = "共几针")
    private String totalNeedle;

    @Excel(name = "最后接种时间")
    private String lastNeedleTime;

    @Excel(name = "操作人")
    private String createBy;

    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("提交时间")
    private Date createTime;

    @Excel(name = "填报来源")
    private String source;
}
