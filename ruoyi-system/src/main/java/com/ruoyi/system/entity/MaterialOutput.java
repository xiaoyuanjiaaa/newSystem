package com.ruoyi.system.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MaterialOutput {

    private static final long serialVersionUID = 6628788713944319029L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 出库单号
     */
    private String outputNo;

    /**
     * 出库日期
     */
    private String outputDate;

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
     * 出库数量
     */
    private double number;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal money;

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
     * 领用科室
     */
    private String requisitionDept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutputNo() {
        return outputNo;
    }

    public void setOutputNo(String outputNo) {
        this.outputNo = outputNo;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getFinanceCategory() {
        return financeCategory;
    }

    public void setFinanceCategory(String financeCategory) {
        this.financeCategory = financeCategory;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(Integer isCharge) {
        this.isCharge = isCharge;
    }

    public Integer getIsHighvalue() {
        return isHighvalue;
    }

    public void setIsHighvalue(Integer isHighvalue) {
        this.isHighvalue = isHighvalue;
    }

    public Integer getIsOnce() {
        return isOnce;
    }

    public void setIsOnce(Integer isOnce) {
        this.isOnce = isOnce;
    }

    public String getRequisitionDept() {
        return requisitionDept;
    }

    public void setRequisitionDept(String requisitionDept) {
        this.requisitionDept = requisitionDept;
    }
}
