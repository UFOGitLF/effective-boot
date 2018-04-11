package com.fly.modules.sys.service.impl;

import com.fly.modules.sys.repostitory.SysRoleMenuRepository;
import com.fly.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author : liufei on 2018/4/11
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService{
    @Resource
    private SysRoleMenuRepository roleMenuRepository;

    @Override
    public void deleteRoleMenuByMenuId(Long menuId) {
        roleMenuRepository.deleteAllByMenuId(menuId);
    }
}
