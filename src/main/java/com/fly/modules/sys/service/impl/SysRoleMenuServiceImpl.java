package com.fly.modules.sys.service.impl;

import com.fly.modules.sys.entity.SysRoleMenuEntity;
import com.fly.modules.sys.repostitory.SysRoleMenuRepository;
import com.fly.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void saveOrUpdate(List<Long> menuIdList, Long roleId) {
        //先删除角色与菜单关系
        roleMenuRepository.deleteAllByRoleId(roleId);

        if (CollectionUtils.isEmpty(menuIdList)){
            return;
        }
        //保存角色与菜单关系
        List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);
            list.add(roleMenuEntity);
        }
        roleMenuRepository.saveAll(list);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        List<BigInteger> list = roleMenuRepository.findMenuIdsByRoleId(roleId);
        List<Long> longs = new ArrayList<>();
        for (BigInteger bigInteger : list) {
            longs.add(bigInteger.longValue());
        }
        return longs;
    }

    @Override
    public void deleteRoleMenuByRoleId(Long[] ids) {
        List<SysRoleMenuEntity> entities = new ArrayList<>();
        for (Long roleId : ids) {
            SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            entities.add(roleMenuEntity);
        }
        roleMenuRepository.deleteAll(entities);
    }
}
