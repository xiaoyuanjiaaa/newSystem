package com.ruoyi.system.excel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CodeScanLogExcel {

    @Excel(name = "姓名")
    @ApiModelProperty("姓名")
    private String name;

    @Excel(name = "身份证")
    @ApiModelProperty("身份证")
    private String idNum;

    @Excel(name = "手机号码")
    @ApiModelProperty("手机号")
    private String phone;

    @Excel(name = "扫码所在地")
    @ApiModelProperty("扫码所在地")
    private String door;

    @Excel(name = "所在片区")
    @ApiModelProperty(value = "所在片区" )
    private String deptName;

    @Excel(name = "苏康码状态")
    @ApiModelProperty(value = "苏康码状态" )
    private String skmRemark;

    @Excel(name = "扫码时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @ApiModelProperty("扫码时间")
    private Date createTime;

}
