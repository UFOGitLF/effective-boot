package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
     * @param userName
     * @return
     */
    SysUserEntity findByUserName(String userName);

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
    List<String> queryPermsByUserId(Long userId);

    /**
     * 根据用户Id查询所有的菜单ID
     * @param userId
     * @return
     */
    List<Long> findMenuIdsByUserId(Long userId);
}
