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
}
