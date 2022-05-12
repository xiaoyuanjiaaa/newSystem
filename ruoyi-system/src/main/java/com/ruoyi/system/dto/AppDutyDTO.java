package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:43
 */
@Data
public class AppDutyDTO extends PageDomain {

    /** 职责名称 */
    private String dutyName;
}
