package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SuccessEnums {
    PASSWORD_CHANGE_SUCCESS(200, "密码修改成功"),
    USER_CREATE_SUCCESS(200, "用户创建成功"),
    USER_DELETE_SUCCESS(200, "用户删除成功"),
    REFRESH_TOKEN_SUCCESS(200, "令牌刷新成功"),
    USER_MENUS_SUCCESS(200, "查询用户菜单成功"),
    USER_QUERY_SUCCESS(200, "用户查询成功"),
    ROLE_QUERY_SUCCESS(200, "角色查询成功"),
    ROLE_DELETE_SUCCESS(200, "角色删除成功"),
    ROLE_SAVE_SUCCESS(200, "角色保存成功"),
    ROLE_UPDATE_SUCCESS(200, "角色修改成功"),
    MENUS_QUERY_SUCCESS(200, "菜单查询成功"),
    MENUS_DELETE_SUCCESS(200, "菜单删除成功"),
    MENU_SAVE_SUCCESS(200, "菜单保存成功"),
    ASSOCIATE_SUCCESS(200, "关联成功"),
    REMOVE_SUCCESS(200, "解绑成功"),
    MENU_UPDATE_SUCCESS(200, "菜单更新成功"),
    USER_UPDATE_SUCCESS(200, "用户修改成功"),
    DELETE_SUCCESS(200, "删除成功"),
    QUERY_SUCCESS(200, "查询成功"),
    OPERATION_SUCCESS(200, "操作成功"),
    SAVE_SUCCESS(200, "新增成功"),
    UPDATE_SUCCESS(200, "修改成功"),
    IMPORT_SUCCESS(200, "导入成功"),
    SMS_SEND_SUCCESS(200, "发送成功"),
    SMS_SYNC_SUCCESS(200, "短信模板同步平台成功"),
    UPLOAD_FILE_SUCCESS(200, "图片上传成功"),
    DELETE_FILE_SUCCESS(200, "图片删除成功"),
    APPROVE_SUCCESS(200, "审核操作成功"),
    DATA_IMPORT_SUCCESS(200, "数据导入成功");
    private int code = 200;
    private String msg = "新增成功";
}
