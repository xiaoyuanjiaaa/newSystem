package com.ruoyi.system.excel;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class AppRosterUserExcel {

    @Excel(name = "姓名")
    private String nikeName;

    @Excel(name = "部门")
    private String deptName;

    @Excel(name = "手机号")
    private String phoneNumber;

    @Excel(name = "梯队名称")
    private String echelonName;

    @Excel(name = "排班名称")
    private String rosterName;

    @Excel(name = "排班时间")
    private String rosterTime;

    @Excel(name = "职责")
    private String dutyName;
}
