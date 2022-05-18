package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DutyStatusEnums {
    ON_DUTY(0, "在岗"),
    OUT_DUTY(1, "休息");
    private int code;
    private String msg;
}
