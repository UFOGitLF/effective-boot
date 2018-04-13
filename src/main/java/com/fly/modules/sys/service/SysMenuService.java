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
    List<SysMenuEntity> queryUserMenuListByUserId(Long userId);

    /**
     * 根据父菜单查询子菜单
     * @param parentId 父菜单
     * @return
     */
    List<SysMenuEntity> queryListByParentId(Long parentId);

    /**
     * 根据父菜单查询子菜单
     * @param parentId 父菜单
     * @param menuIdList 用户菜单ID
     * @return
     */
    List<SysMenuEntity> queryListByParentId(Long parentId, List<Long> menuIdList);

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenuEntity> queryAll();

    /**
     * 根据ID查询
     * @param menuId
     * @return
     */
    SysMenuEntity queryByMenuId(Long menuId);

    /**
     * 保存菜单
     * @param menuEntity
     */
    void insert(SysMenuEntity menuEntity);

    /**
     * 获取不包含按钮的菜单列表
     * @return
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 根据menuId删除
     * @param menuId
     */
    void delete(Long menuId);

    /**
     * 修改菜单
     * @param menu
     */
    void updateById(SysMenuEntity menu);
}
