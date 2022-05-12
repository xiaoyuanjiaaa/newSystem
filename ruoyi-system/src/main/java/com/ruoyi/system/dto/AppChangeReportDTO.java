package com.ruoyi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppChangeReportDTO extends PageDomain {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("工作区域")
    private String deptName;

    @ApiModelProperty("工作区域")
    private Long deptId;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 增加减少状态(0，全部；1，增加；2，减少) */
    @ApiModelProperty("增加状态")
    private Integer changeType;
}
