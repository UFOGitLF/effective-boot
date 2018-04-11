package com.fly.common.utils;

/**
 * 结果状态码
 * @author liufei
 */
public class ResultCodeConstants {
    /**
     * 用户名或密码错误
     */
    public static final int USERNAME_OR_PASSWORD_WRONG = 5001;
    /**
     * 账号已被锁定,请联系管理员
     */
    public static final int ACCOUNT_LOCKED = 5002;
    /**
     * 上传文件为空
     */
    public static final int FILE_EMPTY_ERROR = 5003;
    /**
     * 验证码错误
     */
    public static final int CAPTCHA_WRONG = 5004;
    /**
     * 系统菜单不能删除
     */
    public static final int DELETE_SYSMENU_ERROR = 5005;
    /**
     * 请先删除子菜单或按钮
     */
    public static final int FIRST_DELETE_SUBMENU = 5006;
    /**
     * 原始密码不正确
     */
    public static final int OLD_PASSWORD_WRONG = 5007;
}
