package com.ruoyi.system.excel;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

@Data
public class AppVisitExcel extends PageDomain {

    @Excel(name = "姓名")
    private String personName;

    @Excel(name = "姓名")
    private String personId;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "手机号")
    private String mobile;

    @Excel(name = "身份证号")
    private String idNum;

    @Excel(name = "核验地点")
    private String destinationName;

    @Excel(name = "核验地点")
    private String destinationId;

    @Excel(name = "核验时间")
    private String createTime;

    @Excel(name = "填报时间")
    private String reportTime;

    @Excel(name = "苏康码状态")
    private String qrcodeStatus;

    @Excel(name = "预检分诊码状态")
    private String yjfzStatus;

    private String qrcodecolor;

    private String startTime;
    private String endTime;
    private Integer pageSize;
    private Integer pageNum;

    private Boolean flag;

}
