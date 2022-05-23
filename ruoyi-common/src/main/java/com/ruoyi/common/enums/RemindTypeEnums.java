package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RemindTypeEnums {
    NOT_FILL_REMIND(1, "日常未填报到期提醒"),
    EXCEPTION_REMIND(2, "日常填报异常提醒");
    private int code;
    private String msg;
    public static RemindTypeEnums getPaymentType(int code) {
        for (RemindTypeEnums remindTypeEnums : RemindTypeEnums.values()) {
            if (code == remindTypeEnums.getCode()) {
                return remindTypeEnums;
            }
        }
        return null;
    }
    public static String getMsg(int code) {
        for (RemindTypeEnums remindTypeEnums : RemindTypeEnums.values()) {
            if (code == remindTypeEnums.getCode()) {
                return remindTypeEnums.getMsg();
            }
        }
        return null;
    }
}
