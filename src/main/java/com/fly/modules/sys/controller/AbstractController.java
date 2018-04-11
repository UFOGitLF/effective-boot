package com.fly.modules.sys.controller;

import com.fly.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * Controller 公共组件
 * @Author : liufei on 2018/4/8
 */
public abstract class AbstractController {

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }
}
