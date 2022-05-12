package com.ruoyi.common.core.domain.model;

import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ResultVO<T> {
    private Integer code;

    private String msg;

    private T data;

    public ResultVO() {
    }

    public ResultVO(FailEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public ResultVO(SuccessEnums enums, T obj) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = obj;
    }

    public ResultVO(String msg) {
        this.code = 500;
        this.msg = msg;
    }
    public ResultVO(Integer code , String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(FailEnums enums, String msg) {
        this.code = enums.getCode();
        this.msg = msg;
    }

}