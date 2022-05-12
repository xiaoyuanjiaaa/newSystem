package com.ruoyi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel("填报统计实体")
@Data
public class AppHealthReportCountDTO extends PageDomain {

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate currentDay;

    @ApiModelProperty("部门ID")
    private Long deptId;



    public LocalDate getNextDay(){
        return currentDay.plusDays(1);
    }




}
