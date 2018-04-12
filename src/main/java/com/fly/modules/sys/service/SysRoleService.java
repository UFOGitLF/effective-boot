package com.fly.modules.sys.service;

import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.modules.sys.entity.SysRoleEntity;
import org.springframework.data.domain.Page;

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
    Page<SysRoleEntity> selectRolesByCondition(SysRoleEntity role, PageInfo pageInfo);

    /**
     * 根据创建者ID查询角色列表
     * @param createUserId
     * @return
     */
    List<Long> selectRolesByCreateUserId(Long createUserId);

    /**
     * 角色新增
     * @param role
     */
    void insert(SysRoleEntity role);

    /**
     * 更新角色
     * @param role
     */
    void update(SysRoleEntity role);

    /**
     * 删除角色
     * @param ids
     */
    void deleteBatch(Long[] ids);
}
