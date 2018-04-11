package com.fly.modules.sys.service;

import com.fly.common.utils.Rr;

/**
 * @Author : liufei on 2018/4/9
 */
public interface SysUserTokenService {
    /**
     * 生成token
     * @param userId
     * @return
     */
    Rr createToken(Long userId);
}
