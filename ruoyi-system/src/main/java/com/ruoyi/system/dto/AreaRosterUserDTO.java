package com.ruoyi.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class AreaRosterUserDTO {

    /** 人员id **/
    private Long userId;

    /** 具体排班信息 **/
    private List<RosterUserDTO> rosterUserDTOS;
}
