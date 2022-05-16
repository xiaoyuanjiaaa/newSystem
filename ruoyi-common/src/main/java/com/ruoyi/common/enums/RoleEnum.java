package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * <p>
 * 角色对应枚举
 * </p>
 *
 * @author xiaoyj
 * @since 2022-05-012
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {
    SUPER_ADMIN_ROLE(1, "超级管理员"),
    PDA_ROLE(108, "PDA-访客登记"),
    VISIST_CONFIRM(109, "小程序-访客确认"),
    TRAFFIC_REGISTRATION(110, "小程序-通行登记"),
    DEPT_ADMIN(112, "部门管理员"),
    USER_ADMIN(113, "人员管理员"),
    VISITOR(114, "患者确认"),
    SYS_ADMIN(115, "系统管理员");
    private Integer roleId;
    private String roleName;
}
