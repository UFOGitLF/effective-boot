package com.fly.modules.sys.service;

import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * 权限
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 根据token查询
     * @param token
     * @return
     */
    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);
}
