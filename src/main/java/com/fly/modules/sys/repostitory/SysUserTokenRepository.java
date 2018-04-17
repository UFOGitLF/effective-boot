package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysUserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    /**
     * 修改token
     * @param token
     * @param userId
     */
    @Modifying
    @Query(value = "UPDATE sys_user_token SET token = :token WHERE user_id = :userId",nativeQuery = true)
    void updateToken(@Param(value = "token") String token,@Param(value = "userId") Long userId);

    /**
     * 退出删除token
     * @param userId
     */
    @Modifying
    @Query(value = "DELETE FROM sys_user_token WHERE user_id = :userId",nativeQuery = true)
    void deleteByUserId(@Param(value = "userId") Long userId);

    void deleteByToken(String accessToken);
}
