package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RemindObjectEnums {
    YOURSELF(1, "本人"),
    LEADER(2, "部门负责人"),
    APPOINT(3, "指定人员");
    private int code;
    private String msg;
    public static RemindObjectEnums getPaymentType(int code) {
        for (RemindObjectEnums remindObjectEnums : RemindObjectEnums.values()) {
            if (code == remindObjectEnums.getCode()) {
                return remindObjectEnums;
            }
        }
        return null;
    }
    public static String getMsg(int code) {
        for (RemindObjectEnums remindObjectEnums : RemindObjectEnums.values()) {
            if (code == remindObjectEnums.getCode()) {
                return remindObjectEnums.getMsg();
            }
        }
        return null;
    }
}
