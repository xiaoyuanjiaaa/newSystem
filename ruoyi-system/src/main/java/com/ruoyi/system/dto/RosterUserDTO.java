package com.ruoyi.system.dto;

import lombok.Data;

@Data
public class RosterUserDTO {
    /** 排班日期 **/
    private String rosterTime;

    /** 班次id **/
    private Long rosterId;

    /** 场地id **/
    private Long workplaceId;
}
