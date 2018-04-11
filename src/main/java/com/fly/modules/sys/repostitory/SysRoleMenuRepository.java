package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysRoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : liufei on 2018/4/11
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenuEntity,Long>{
    /**
     * 根据menuId删除菜单与角色关联数据
     * @param menuId
     */
    void deleteAllByMenuId(Long menuId);
}
