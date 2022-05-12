package com.ruoyi.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BasePageEntity;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@ApiModel(value = "职工扫码记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeScanLogQueryDTO extends PageDomain {

    /** 姓名 */
    @TableField("name" )
    private String name;

    /** 手机号 */
    @TableField("phone" )
    private String phone;

    /** 大门 */
    @TableField("door" )
    private String door;

    /** 扫码开始时间 */
    @TableField(exist = false)
    @ApiModelProperty("扫码开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String scanStartTime;

    /** 扫码结束时间 */
    @TableField(exist = false)
    @ApiModelProperty("扫码结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String scanEndTime;
}
