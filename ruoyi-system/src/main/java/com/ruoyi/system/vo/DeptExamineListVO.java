package com.ruoyi.system.vo;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@ApiModel("片区审核列表返回对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptExamineListVO{
    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "当前片区")
    private String deptName;

    @ApiModelProperty(value = "变更片区")
    private String deptChange;


    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime examineTime;

    @ApiModelProperty(value = "片区变更id")
    private Integer deptChangeId;

}
