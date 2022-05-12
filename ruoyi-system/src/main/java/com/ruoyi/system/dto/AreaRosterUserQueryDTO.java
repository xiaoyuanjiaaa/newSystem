package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

@Data
public class AreaRosterUserQueryDTO extends PageDomain {

    private String rosterTime;

    private Long rosterId;

    private Long deptId;

    private Long workPlaceId;

    private String nickName;
}
