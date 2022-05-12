package com.ruoyi.system.vo;

import com.ruoyi.system.entity.AppPersonWx;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "预检分诊后台展示")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppPersonQueryWxVO extends AppPersonWx {

    private String personName;

    //地点拼接名字
    private String destinationName;

    //手机号
    private String mobile;

    //身份证号
    private String idNum;
}
