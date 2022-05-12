package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CheckLeaderStatus {
    LEADER_ALLOW(1, "可以修改"),
    LEADER_REFUSE(0, "不能修改");
    private int code;
    private String msg;
}
