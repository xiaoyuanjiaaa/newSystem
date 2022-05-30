package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FailEnums {
    PASSWORD_CHANGE_FAIL(500, "密码修改失败"),
    REMOTE_SYSTEM_NO_RESPONSE(500, "服务未响应"),
    INPUT_QUERY_PARAM(500, "请输入查询参数"),
    USER_CREATE_FAIL(500, "用户创建失败"),
    USER_CREATE_FAIL_BY_OPENID(500, "用户openId不得为空"),
    USER_DELETE_FAIL(500, "用户删除失败"),
    USER_DELETE_FAIL_ID_NOT_NULL(500, "用户删除失败，用户id不能为空"),
    CHECK_PARAM(500, "请输入参数"),
    REFRESH_TOKEN_FAIL(500, "令牌刷新失败"),
    USER_MENUS_FAIL(500, "查询用户菜单失败"),
    USER_QUERY_FAIL(500, "用户查询失败"),
    USER_NOT_FOUND(500, "用户不存在"),
    USER_NOT_REGIST(500, "用户尚未注册"),
    USER_NOT_ADMIN(500, "非管理员账号"),
    ROLE_QUERY_FAIL(500, "角色查询失败"),
    ROLE_DELETE_FAIL(500, "角色删除失败"),
    ROLE_SAVE_FAIL(500, "角色保存失败"),
    ROLE_UPDATE_FAIL(500, "角色更新失败"),
    MENU_DELETE_FAIL(500, "菜单删除失败"),
    MENU_SAVE_FAIL(500, "菜单保存失败"),
    ASSOCIATE_FAIL(500, "关联失败"),
    MENU_UPDATE_FAIL(500, "菜单更新失败"),
    QUERY_FAIL(500, "查询失败"),
    UPDATE_FAIL(500, "更新失败"),
    DELETE_FAIL(500, "删除失败"),
    SAVE_FAIL(500, "新增失败"),
    REPEATED_REQUESTS(500, "请勿重复请求"),
    USER_UPDATE_FAIL(500, "用户修改失败"),
    IMPORT_FAIL(500, "导入失败"),
    DELETE_APP_FAIL(500, "有租户在使用该应用,不可删除"),
    UPDATE_USER_PWD_FAIL(500, "不允许操作超级管理员用户"),
    ORG_DELETE_FAIL(500, "存在子节点不允许删除"),
    ORG_SAVE_FAIL_NAMESAME(500, "名称相同不允许保存"),
    ORG_UPDATE_FAIL_NAMESAME(500, "名称相同不允许更新"),
    PERMISSION_SAVE_FAIL(500, "权限标识已存在"),
    PERMISSION_PERCODE_SAVE_FAIL(500, "权限标识不能为空"),
    PERMISSION_TYPE_SAVE_FAIL(500, "权限不能跨级添加"),
    HK_FAIL(500,"HK服务调用异常"),
    BUSINESS_FAIL(500,"业务异常"),
    EVENT_CHECK_IMAGE(500, "图片格式有问腿"),
    APPEVENTRECORD_QUERY_FAIL(500, "事件查询失败"),
    APPEVENTRECORD_UPDATE_FAIL(500, "事件修改失败"),
    SMS_CHECK_RECORD(500, "短信验证码不匹配"),
    SMS_SEND_RECORD(500, "短信验证码发送失败"),
    SMS_SYNC_RECORD(500, "短信模板同步平台失败"),
    UNKNOWN_EXCEPTION(500, "未知异常"),
    REPORT_NOT_FUND(500, "未找到每日填报信息"),
    REPEAT_TIME_ERROR(500, "不能添加同一时间"),
    DEPT_NOT_FUND(500, "片区不存在"),

    REFUSE_UPDATE(500,"审核当中无法申请"),
    DEPT_NOT_EXIST(500,"该片区不存在"),
    LEADER_NOT_OPTION(500,"负责人无法申请"),
    NOT_RESET_PASWORD(500,"无法重置管理员的密码");

    private int code;
    private String msg;
}
