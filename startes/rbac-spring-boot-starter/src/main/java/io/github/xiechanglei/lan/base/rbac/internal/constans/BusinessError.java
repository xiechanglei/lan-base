package io.github.xiechanglei.lan.base.rbac.internal.constans;

import io.github.xiechanglei.lan.beans.exception.BusinessException;

/**
 * 业务异常处理
 */
public class BusinessError {
    /**
     * 10开头：用户相关
     */
    public static class USER {
        public static final BusinessException USER_LOGIN_FAILED = BusinessException.of(1000001, "用户名或密码错误");
        public static final BusinessException USER_NOT_FOUND = BusinessException.of(1000002, "用户不存在");
        public static final BusinessException USER_DISABLED = BusinessException.of(1000003, "用户已禁用");
        public static final BusinessException USER_LOGIN_EXPIRED = BusinessException.of(1000004, "登陆过期");
        public static final BusinessException USER_NO_PERMISSION = BusinessException.of(1000005, "用户无权限");
        public static final BusinessException USER_PASSWORD_ERROR = BusinessException.of(1000006, "密码错误");
        public static final BusinessException USER_EXISTS = BusinessException.of(1000007, "用户已存在");
        public static final BusinessException USER_NOT_LOGIN = BusinessException.of(1000008, "用户未登陆");
        public static final BusinessException USER_CAN_NOT_OPERATE = BusinessException.of(1000009, "超级管理员无法禁用");
        public static final BusinessException USER_ADMIN_ROLE = BusinessException.of(1000010, "系统至少存在一个用户拥有管理员角色");
        public static final BusinessException USER_NAME_IS_EMPTY = BusinessException.of(1000011, "用户名不能为空");
        public static final BusinessException USER_PASS_IS_EMPTY = BusinessException.of(1000012, "用户密码不能为空");
    }

    /**
     * 11开头：角色相关
     */
    public static class ROLE {
        public static final BusinessException ROLE_EXISTS = BusinessException.of(1200001, "角色名称已存在");
        public static final BusinessException ROLE_CAN_NOT_DELETE = BusinessException.of(1200002, "角色下有用户，不能删除");
        public static final BusinessException ROLE_NOT_EXISTS = BusinessException.of(1200003, "角色不存在");
        public static final BusinessException ROLE_CAN_NOT_OPERATE = BusinessException.of(1200004, "系统角色，无法操作");
    }

    /**
     * 13开头：菜单相关
     */
    public static class MENU {
        public static final BusinessException MENU_NOT_FOUND = BusinessException.of(1300001, "菜单未找到");
        public static final BusinessException MENU_TITLE_EXISTS = BusinessException.of(1300002, "菜单标题已存在");
    }
}
