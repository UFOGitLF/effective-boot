package com.fly.modules.sys.service.impl;

import com.fly.common.utils.Constant;
import com.fly.modules.sys.entity.SysMenuEntity;
import com.fly.modules.sys.repostitory.SysMenuRepository;
import com.fly.modules.sys.service.SysMenuService;
import com.fly.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : liufei on 2018/4/10
 */
@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Resource
    private SysUserService userService;
    @Resource
    private SysMenuRepository menuRepository;

    @Override
    public List<SysMenuEntity> findUserMenuListByUserId(Long userId) {
        //admin用户
        if (userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = userService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenuEntity> findListByParentId(Long parentId) {
        return menuRepository.findAllByParentIdOrderByOrderNumAsc(parentId);
    }

    @Override
    public List<SysMenuEntity> findListByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = findListByParentId(parentId);
        if (menuIdList == null){
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menuEntity: menuList){
            if (menuIdList.contains(menuEntity.getMenuId())){
                userMenuList.add(menuEntity);
            }
        }
        return userMenuList;
    }

    /**
     * 查询所有菜单列表
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = findListByParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归获取所有子菜单
     * @param menuList
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();
        for (SysMenuEntity menuEntity : menuList){
            //目录
            if (menuEntity.getType() == Constant.MenuType.CATALOG.getValue()){
                menuEntity.setList(getMenuTreeList(findListByParentId(menuEntity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(menuEntity);
        }
        return subMenuList;
    }
}
