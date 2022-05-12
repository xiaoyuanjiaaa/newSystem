package com.ruoyi.system.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 片区更换审核表
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("片区更换审核表")
public class DeptChangeExamine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 变更片区
     */
    private String deptChange;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;

    /**
     * 审核时间
     */
    private LocalDateTime examineTime;

    /**
     * 审核状态 1审核中 2通过 0未通过
     */
    private Integer flag;
    /**
     * 变更地区id
     */
    private Integer deptChangeId;
    /**
     * 当前片区名称
     */
    private String deptName;
    /**
     * 个人门铃码图片地址
     */
    private String qrcodeUrl;
}
