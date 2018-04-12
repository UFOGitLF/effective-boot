package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRoleEntity,Long>,JpaSpecificationExecutor<SysRoleEntity>{


    /**
     * 根据创建者ID查询List<roleId></>
     * @param createUserId
     * @return
     */
    @Query(value = "SELECT t.role_id FROM sys_role t where create_user_id = :createUserId",nativeQuery = true)
    List<Long> findRoleIdsByCreateUserId(@Param(value = "createUserId") Long createUserId);
}
