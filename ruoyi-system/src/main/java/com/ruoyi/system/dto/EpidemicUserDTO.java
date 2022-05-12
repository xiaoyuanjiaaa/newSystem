package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 10:28
 */
@Data
public class EpidemicUserDTO extends PageDomain {

    /** 用户姓名 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户电话 */
    private String phone;

    /** 梯队名称 */
    private String echelonName;

    /** 工作状态，1：负压 2：隔离 3：正常 无则为0 */
    private Integer workStatus;

    /** 工作地址 */
    private String workAddress;

    //人员id user_id
    private List<Long> userIds;
}
