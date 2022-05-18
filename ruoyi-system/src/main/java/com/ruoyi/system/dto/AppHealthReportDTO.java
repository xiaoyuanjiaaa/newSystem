package com.ruoyi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel(value = "健康填报")
@Data
public class AppHealthReportDTO extends PageDomain {

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("部门")
    private Long deptId;

    @ApiModelProperty("工作区域")
    private String workPlace;

    @ApiModelProperty("工号")
    private String jobNum;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("最低体温")
    private String minTemperature;

    @ApiModelProperty("最高体温")
    private String maxTemperature;

    @ApiModelProperty("人员ID")
    private Long personId;

    @ApiModelProperty("在岗状态 0在岗 1休息  默认为空")
    private Integer dutyStatus;


}
