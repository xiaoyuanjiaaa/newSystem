package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("negative_pressure_ward")
public class NegativePressureWard extends BaseEntity {

    private static final long serialVersionUID = 7414889636804480965L;

    private Long id;

    /**
     * 住院流水号
     */
    private String inpatientNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别  F女
     *      M男
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 健康状态
     */
    private String healthStatus;

    /**
     * 出入时间
     */
    private Date accessTime;

    /**
     * 出入类型 1进、2出
     */
    private int accessStatus;

    /** 创建时间 */
    private Date createTime;

    /** 创建人 */
    private String createBy;

    /** 更新时间 */
    private Date updateTime;

    /** 更新人 */
    private String updateBy;

    /** 删除状态 */
    private Integer isDeleted;

}
