package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("material_input")
public class MaterialInput {

    private static final long serialVersionUID = 2062967595707256568L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 入库单号
     */
    private String inputNo;

    /**
     * 入库日期
     */
    private String inputDate;

    /**
     * 物资类别
     */
    private String materialCategory;

    /**
     * 仪器名称
     */
    private String instrumentName;

    /**
     * 方法
     */
    private String method;

    /**
     * 财务收费
     */
    private Integer isCharge;

    /**
     * 高值耗材
     */
    private Integer isHighvalue;

    /**
     * 一次性耗材
     */
    private Integer isOnce;

    /**
     * 财务类别
     */
    private String financeCategory;

    /**
     * 物资名称
     */
    private String materialName;

    /**
     * 规格
     */
    private String specifications;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 供货公司
     */
    private String shippingCompany;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 生产厂家
     */
    private String manufacturer;


}
