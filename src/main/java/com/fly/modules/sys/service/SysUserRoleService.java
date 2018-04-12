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
}
