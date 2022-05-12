package com.ruoyi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel("行程查询实体")
public class AppRouteReportDTO {


    @ApiModelProperty("当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime currentDay;

    public LocalDateTime getNextDay(){
        return currentDay.plusDays(1);
    }

}
