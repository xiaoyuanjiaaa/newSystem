package com.ruoyi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SmsOracleConfigDTO {
    //短信业务名
    private String usemod="yqfk";
    //手机号
    private String mobile;
    //短信内容
    private String message;
    //所属公司
    private String operid="开拓";
    //创建时间
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp createDate;
    //短信收到时间
    private Date sentDate;
    //短信状态
    private String statusFlag;
    //
    private Date receiveDate;
}
