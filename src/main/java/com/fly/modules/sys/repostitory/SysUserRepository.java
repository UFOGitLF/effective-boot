package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : liufei on 2018/4/8
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUserEntity,Long>,JpaSpecificationExecutor<SysUserEntity>{
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUserEntity findByUsername(String username);

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    SysUserEntity findByUserId(Long userId);

    /**
     * 根据用户Id查询用户权限
     * @param userId
     * @return
     */
    @Query(value = "SELECT t3.perms FROM sys_user_role t1 " +
                   "LEFT JOIN sys_role_menu t2 ON t1.role_id = t2.role_id " +
                   "LEFT JOIN sys_menu t3 ON t2.menu_id = t3.menu_id " +
                   "WHERE t1.user_id = :userId",nativeQuery = true)
    List<String> findPermsByUserId(@Param(value = "userId") Long userId);

    /**
     * 根据userId修改密码
     * @param newPassword
     * @param userId
     * @return
     */
    @Modifying
    @Query(value = "UPDATE sys_user SET password = :newPassword where user_id = :userId",nativeQuery = true)
    Integer updatePassword(@Param(value = "newPassword") String newPassword,@Param(value = "userId") Long userId);

    /**
     * ID批量删除
     * @param ids
     */
    @Modifying
    @Query(value = "DELETE FROM sys_user WHERE user_id IN :ids",nativeQuery = true)
    void deleteAllByIds(@Param(value = "ids") Long[] ids);
}
