package com.fly.modules.sys.service;

import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
public interface SysUserRoleService {

    /**
     * 修改用户于角色绑定关系
     * @param userId
     * @param roleIdList
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据userID查询角色Ids
     * @param userId
     * @return
     */
    List<Long> queryRoleIdListByUserId(Long userId);

    /**
     * 根据roleIds删除关联关系
     * @param ids
     */
    void deleteUserRoleByRoleIds(Long[] ids);

    /**
     * 根据userId查询所有的菜单IDs
     * @param userId
     * @return
     */
    List<Long> findMenuIdsByUserId(Long userId);
}
