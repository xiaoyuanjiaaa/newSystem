package com.ruoyi.system.excel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserExcel {

    /** 用户账号 */
    @Excel(name = "姓名")
    @ApiModelProperty("登录名称")
    private String userName;

    /** 工号 */
    @Excel(name = "工号")
    @ApiModelProperty("工号")
    private String jobNumber;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号")
    private String phonenumber;

    /** 身份证号 */
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号" )
    private String idNum;

    /** 最后登录IP */
    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT)
    @ApiModelProperty("最后一次登录IP")
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    @TableField(exist = false)
    private SysDept dept;

    /** 是否是自有0：本院，1：非本院 */
    @ApiModelProperty("是否是自有0：本院，1：非本院")
    @Excel(name = "本院/非本院", readConverterExp = "0=本院,1=非本院")
    private String isPrivate;

    /** 0：临床，1：非临床 */
    @ApiModelProperty("0：临床，1：非临床")
    @Excel(name = "临床/非临床", readConverterExp = "0=临床,1=非临床")
    private String isClinical;

    /** 0：临时，1：长期 */
    @ApiModelProperty("0：临时，1：长期")
    @Excel(name = "临时人员/长期员工", readConverterExp = "0=临时人员,1=长期员工")
    private String isTemporary;

    /** A、B、C风险等级 */
    @ApiModelProperty("风险等级")
    @Excel(name = "风险等级", dictType = "sys_risk_level")
    private String riskLevel;

    /** 职务等级 */
    @ApiModelProperty("职务等级")
    @Excel(name = "职务等级")
    private String postLevel;

    /** 是否是梯队人员(0，否；1，是) */
    @ApiModelProperty("是否是梯队人员(0，否；1，是)")
    @Excel(name = "是否是梯队人员(0，否；1，是)")
    private int isEchelon;

    /** 最后登录时间 */
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @ApiModelProperty("新增时间")
    private Date createTime;

    /** 最后登录时间 */
    @Excel(name = "停用时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @ApiModelProperty("停用时间")
    private Date updateTime;

    /**
     * 工作场所
     */
    @Excel(name = "工作场所",prompt = "发热门诊、板房、负压病房 | 普通诊疗区 | 其它(不接触患者或特殊感染环境)")
    @ApiModelProperty(value = "工作场所",
            example = "A.发热门诊、板房、负压病房  B.普通诊疗区  C.其它(不接触患者或特殊感染环境)")
    private String workPlace;
}
