package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 9:46
 */
@Data
public class AppRosterQueryDTO extends PageDomain {

    /** 班次名称 */
    private String rosterName;

    private Long rosterId;

    /** 班次时间段 */
    private String rosterTime;

    private String nickName;

    /** 创建者*/
    private String createBy;
    
    /** 数据隔离*/
    private Integer type;
}
