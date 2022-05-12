package com.ruoyi.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/29 14:03
 */
@Data
public class UserRosterDTO {

    /** 人员id **/
    private Long userId;

    /** 具体排班信息 **/
    private List<RosterDTO> rosterDTOS;

}
