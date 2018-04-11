package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : liufei on 2018/4/9
 */
@Repository
public interface SysUserTokenRepository extends JpaRepository<SysUserTokenEntity,Long>{
    /**
     * 根据用户Id查询
     * @param userId
     * @return
     */
    SysUserTokenEntity findByUserId(Long userId);

    /**
     * 根据token查询
     */
    SysUserTokenEntity findByToken(String token);
}
