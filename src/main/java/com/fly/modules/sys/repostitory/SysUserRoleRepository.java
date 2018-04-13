package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRoleEntity,Long>{

    /**
     * 根据userId删除用户与角色绑定关系
     * @param userId
     */
    @Modifying
    @Query(value = "DELETE FROM sys_user_role WHERE user_id = :userId",nativeQuery = true)
    void deleteAllByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据userId查询roleIDs
     * @param userId
     * @return
     */
    @Query(value = "SELECT t.role_id FROM sys_user_role t WHERE user_Id = :userId",nativeQuery = true)
    List<BigInteger> findRoleIdsByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据roleIds 删除用户与角色关联关系
     * @param roleIds
     */
    @Modifying
    @Query(value = "DELETE FROM sys_user_role WHERE role_id IN :roleIds",nativeQuery = true)
    void deleteUserRoleByRoleIds(@Param(value = "roleIds") Long[] roleIds);

    /**
     * 根据用户Id查询所有的菜单ID
     * @param userId
     * @return
     */
    @Query(value = "SELECT t2.menu_id FROM sys_user_role t1 " +
            "LEFT JOIN sys_role_menu t2 ON t1.role_id = t2.role_id " +
            "WHERE t1.user_id = :userId",nativeQuery = true)
    List<BigInteger> findMenuIdsByUserId(@Param(value = "userId") Long userId);
}
