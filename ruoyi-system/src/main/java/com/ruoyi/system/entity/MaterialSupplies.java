package com.ruoyi.system.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("material_supplies")
public class MaterialSupplies {
    private static final long serialVersionUID = 2365823188591402442L;

    /** 主键id */
    private Long id;

    /** 物资类别 */
    @Excel(name = "物资类别")
    private String materialCategory;

    /** 仪器名称 */
    @Excel(name = "仪器名称")
    private String instrumentName;

    /** 方法 */
    @Excel(name = "方法")
    private String method;

    /** 高值耗材 */
    @Excel(name = "高值耗材")
    private Integer isHighvalue;

    /** 一次性耗材 */
    @Excel(name = "一次性耗材")
    private Integer isOnce;

    /** 物资名称 */
    @Excel(name = "物资名称")
    private String materialName;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 数量 */
    @Excel(name = "数量")
    private Integer number;

    /** 1：已开启，2：未开启 */
    @Excel(name = "1：已开启，2：未开启")
    private Integer monitorState;

    /** 创建时间 */
    private Date createTime;

    /** 创建人 */
    private String createBy;

    /** 更新时间 */
    private Date updateTime;

    /** 更新人 */
    private String updateBy;

    /** 备注 */
    private String remark;

    /** 删除状态 */
    private Integer isDeleted;

    /** 生产厂家 */
    private String manufacturer;

    /** 供货公司 */
    private String shippingCompany;

    /** 是否方面短信 */
    private Integer isSend;

}
