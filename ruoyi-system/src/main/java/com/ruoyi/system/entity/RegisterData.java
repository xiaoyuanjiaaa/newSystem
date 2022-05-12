package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("注册json数据中间表")
@TableName("register_data")
public class RegisterData {

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
    private Long registerId;

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

    private Date registerTime;

    /**
     * 未填报说明
     */
    private String remark;

    /**
     * 父级id
     */
    private Long parentDetailId;

    private Integer sort;
}
