package com.ruoyi.system.dto;

import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/9/29 14:38
 */
@Data
public class RosterDTO {

    /** 排班日期 **/
    private String rosterTime;

    /** 班次id **/
    private Long rosterId;

    /** 职责id **/
    private Long dutyId;

}
