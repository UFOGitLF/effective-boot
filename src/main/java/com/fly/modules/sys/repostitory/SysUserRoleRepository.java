package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * 根据RoleId批量删除
     * @param roleId
     */
    @Modifying
    @Query(value = "DELETE FROM sys_user_role WHERE role_id = :roleId",nativeQuery = true)
    void deleteAllByRoleId(@Param(value = "roleId") Long roleId);

    /**
     * 根据userId查询roleIDs
     * @param userId
     * @return
     */
    @Query(value = "SELECT t.role_id FROM sys_user_role t WHERE user_Id = :userId",nativeQuery = true)
    List<Long> findRoleIdsByUserId(@Param(value = "userId") Long userId);
}
