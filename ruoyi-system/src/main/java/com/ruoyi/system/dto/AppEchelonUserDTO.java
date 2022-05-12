package com.ruoyi.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 19:57
 */
@Data
public class AppEchelonUserDTO implements Serializable {

    /** 梯队id  **/
    private Long echelonId;

    /** 人员id集合 **/
    private List<Long> userIds;
}
