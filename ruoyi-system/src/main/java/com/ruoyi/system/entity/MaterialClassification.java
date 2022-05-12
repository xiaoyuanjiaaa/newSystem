package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("material_classification")
public class MaterialClassification  {

    private static final long serialVersionUID = 5036326675027540547L;

    @ApiModelProperty(value = "物品科目编码")
    private String kindCode;

    @ApiModelProperty(value = "上级编码")
    private String preCode;

    @ApiModelProperty(value = "仓库代码")
    private String storageCode;

    @ApiModelProperty(value = "分类名称")
    private String kindName;

    @ApiModelProperty(value = "拼音码")
    private String spellCode;

    @ApiModelProperty(value = "五笔码")
    private String webCode;

    @ApiModelProperty(value = "自定义码")
    private String customCode;

    @ApiModelProperty(value = "国家编码")
    private String gbCode;

    @ApiModelProperty(value = "有效范围")
    private String effectArea;

    @ApiModelProperty(value = "有效科室")
    private String effectDept;

    @ApiModelProperty(value = "是否按批次管理")
    private String batchFlag;

    @ApiModelProperty(value = "是否有效期管理")
    private String validdateFlag;

    @ApiModelProperty(value = "停用标记")
    private String validFlag;

    @ApiModelProperty(value = "财务科目编码")
    private String accountCode;

    @ApiModelProperty(value = "财务科目名称")
    private String accountName;

    @ApiModelProperty(value = "排列序号")
    private String orderNo;

    @ApiModelProperty(value = "财务收费标识")
    private String financeFlag;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "操作员")
    private String operCode;

    @ApiModelProperty(value = "操作日期")
    private String operDate;

    @ApiModelProperty(value = "是否勾选")
    @TableField(exist = false)
    private Integer status;

}
