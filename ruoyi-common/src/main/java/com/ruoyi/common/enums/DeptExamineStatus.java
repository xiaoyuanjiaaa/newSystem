package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum DeptExamineStatus {

    EXAMINE(1, "审核中"),
    EXAMINE_PASS(2, "审核通过"),
    EXAMINE_REFUSE(0, "拒绝");
    private int code;
    private String msg;
}
