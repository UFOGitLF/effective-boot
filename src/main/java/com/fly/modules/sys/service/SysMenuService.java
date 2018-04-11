package com.fly.modules.sys.service;

import com.fly.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * @Author : liufei on 2018/4/10
 */
public interface SysMenuService {
    /**
     * 根据用户ID查询菜单列表
     * @param userId
     * @return
     */
    List<SysMenuEntity> findUserMenuListByUserId(Long userId);

    /**
     * 根据父菜单查询子菜单
     * @param parentId 父菜单
     * @return
     */
    List<SysMenuEntity> findListByParentId(Long parentId);

    /**
     * 根据父菜单查询子菜单
     * @param parentId 父菜单
     * @param menuIdList 用户菜单ID
     * @return
     */
    List<SysMenuEntity> findListByParentId(Long parentId, List<Long> menuIdList);


}
