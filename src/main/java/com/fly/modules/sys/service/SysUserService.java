package com.fly.modules.sys.service;

import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * @Author : liufei on 2018/4/8
 */
public interface SysUserService {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUserEntity queryByUsername(String username);

    /**
     * 根据用户ID,查询所有的菜单ID
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 分页查询
     * @param pageInfo
     * @param username
     * @param createUserId
     * @return
     */
    PageData queryPage(PageInfo pageInfo,String username,Long createUserId);
}
