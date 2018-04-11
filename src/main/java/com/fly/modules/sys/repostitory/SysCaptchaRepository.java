package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysCaptchaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : liufei on 2018/4/8
 */
@Repository
public interface SysCaptchaRepository extends JpaRepository<SysCaptchaEntity,String>{
    /**
     * 根据uuid查询
     * @param uuid
     * @return
     */
    SysCaptchaEntity findByUuid(String uuid);
}
