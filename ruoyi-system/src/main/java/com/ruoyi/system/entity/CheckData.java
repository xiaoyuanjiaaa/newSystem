package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("预检分诊json数据中间表")
@TableName("check_data")
public class CheckData {

    private static final long serialVersionUID =  5812627873079028226L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type= IdType.INPUT)
    private Long id;

    /**
     * 报告id
     */
    private Long checkId;

    /**
     * 填报项目
     */
    private String chineseName;

    /**
     * 填报值
     */
    private String chineseValue;

    /**
     * 数据id
     */
    private Long detailId;

    /**
     * 模板id
     */
    private Long templateId;


    private Date createTime;

    private Date checkTime;

    /**
     * 未填报说明
     */
    private String remark;

    /**
     * 父级id
     */
    private Long parentDetailId;

    private Integer sort;

    @TableField(exist = false)
    private List<CheckData> checkDataList = new ArrayList<>();
}
