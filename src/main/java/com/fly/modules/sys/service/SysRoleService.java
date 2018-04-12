package com.fly.modules.sys.service;

import com.fly.modules.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
public interface SysRoleService {

    /**
     * 根据条件查询角色
     * @param role
     * @return
     */
    List<SysRoleEntity> selectRolesByCondition(SysRoleEntity role);

    /**
     * 根据创建者ID查询角色列表
     * @param createUserId
     * @return
     */
    List<Long> selectRolesByCreateUserId(Long createUserId);
}
