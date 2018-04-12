package com.fly.modules.sys.service;

import java.util.List;

/**
 * @Author : liufei on 2018/4/11
 */
public interface SysRoleMenuService {
    /**
     * 根据menuId 删除关联关系
     * @param menuId
     */
    void deleteRoleMenuByMenuId(Long menuId);

    /**
     * 添加或修改关联关系
     * @param menuIdList
     * @param roleId
     */
    void saveOrUpdate(List<Long> menuIdList, Long roleId);

    /**
     * 根据roleId查询菜单IDS
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据roleIds批量删除角色菜单关联关系
     * @param ids
     */
    void deleteRoleMenuByRoleId(Long[] ids);
}
