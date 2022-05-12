package com.ruoyi.common.core.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (AppPerson)实体类
 *
 * @author makejava
 * @since 2021-08-14 13:16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("app_person")
public class AppPerson implements Serializable {
    private Long personId;

    /**
     * 姓名
     */
    private String personName;


    private String idNum;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 民族
     */
    private String nation;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 公司
     */
    private String company;

    /**
     * 苏康码
     */
    private String qrcode;

    /**
     * 苏康码颜色
     */
    private String qrcodeColor;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

}
