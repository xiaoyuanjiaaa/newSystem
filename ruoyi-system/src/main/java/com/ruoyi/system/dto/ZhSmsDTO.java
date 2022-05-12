package com.ruoyi.system.dto;

import com.ruoyi.common.utils.StringUtils;
import lombok.Data;

import java.util.List;

/*
*  移动短信发送实体
* */
@Data
public class ZhSmsDTO {

    private String uid;
    private String userpwd;
    private String mobile;
    private String message;
    private String ext;

}
