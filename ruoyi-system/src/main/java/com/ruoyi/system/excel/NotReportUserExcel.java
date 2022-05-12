package com.ruoyi.system.excel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NotReportUserExcel {
    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
//            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    @TableField(exist = false)
    private SysDept dept;

    /** 用户账号 */
    @Excel(name = "姓名")
    @ApiModelProperty("登录名称")
    private String nickName;


    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号")
    private String phonenumber;

    /** 身份证号 */
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号" )
    private String idNum;

    /** 工号 */
    @Excel(name = "工号")
    @ApiModelProperty("工号")
    private String jobNumber;

    /**
     * 工作场所
     */
    @Excel(name = "工作场所",prompt = "发热门诊、板房、负压病房 | 普通诊疗区 | 其它(不接触患者或特殊感染环境)")
    @ApiModelProperty(value = "工作场所",
            example = "A.发热门诊、板房、负压病房  B.普通诊疗区  C.其它(不接触患者或特殊感染环境)")
    private String workPlace;
}
