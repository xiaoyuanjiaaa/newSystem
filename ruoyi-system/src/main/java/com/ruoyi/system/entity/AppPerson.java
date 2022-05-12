package com.ruoyi.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * (AppPerson)实体类
 *
 * @author makejava
 * @since 2021-08-14 15:50:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("app_person")
public class AppPerson implements Serializable {
    @TableId(value = "person_id", type = IdType.INPUT)
    private Long personId;

    private Long parentPersonId;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 身份证
     */
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private String picUrl;

}
