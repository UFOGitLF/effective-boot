package com.fly.modules.sys.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author : liufei on 2018/4/8
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    @NotNull(message = "uuid不能为空")
    private String uuid;
}
