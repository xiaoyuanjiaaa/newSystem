package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 18:24
 */
@Data
public class AppEpidemicUserDTO extends PageDomain {

    /** 用户姓名 */
    private String userName;

    /** 用户昵称 */
    private String nickName;

    /** 用户电话 */
    private String phone;

    /** 梯队id **/
    private Long echelonId;

    /** 部门id **/
    private Long deptId;

    /** 工作状态，1：负压 2：隔离 3：正常 无则为0 */
    private Integer workStatus;

    /** 工作地址 */
    private String workAddress;

    /** 进入时间 */
    private String inTime;

    /** 建议出去时间 */
    private String suggestOutTime;

}
