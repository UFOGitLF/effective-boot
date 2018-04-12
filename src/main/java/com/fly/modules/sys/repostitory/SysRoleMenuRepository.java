package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysRoleMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author : liufei on 2018/4/11
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenuEntity,Long>{
    /**
     * 根据menuId删除角色与菜单关联数据
     * @param menuId
     */
    void deleteAllByMenuId(Long menuId);

    /**
     * 根据roleId删除角色与菜单关联数据
     * @param roleId
     */
    void deleteAllByRoleId(Long roleId);

    /**
     * 根据roleId,查询List<MenuId></>
     * @param roleId
     * @return
     */
    @Query(value = "SELECT t.menu_id FROM sys_role_menu t WHERE role_id = :roleId",nativeQuery = true)
    List<BigInteger> findMenuIdsByRoleId(@Param(value = "roleId") Long roleId);
}
