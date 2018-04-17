package com.fly.modules.sys.service.impl;

import com.fly.common.utils.Rr;
import com.fly.modules.sys.entity.SysUserTokenEntity;
import com.fly.modules.sys.oauth2.TokenGenerator;
import com.fly.modules.sys.repostitory.SysUserTokenRepository;
import com.fly.modules.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @Author : liufei on 2018/4/9
 */
@Service
public class SysUserTokenServiceImpl implements SysUserTokenService{
    /**
     * 12小时过期
     */
    private static final int EXPIRE = 3600 * 12;

    @Resource
    private SysUserTokenRepository tokenRepository;


    @Override
    public Rr createToken(Long userId) {
        //生成token
        String token = TokenGenerator.generateValue();
        //当前时间
        Date curDate = new Date();
        Date expireDate = new Date(curDate.getTime() + EXPIRE*1000);
        //判断是否生成过token
        SysUserTokenEntity tokenEntity = tokenRepository.findByUserId(userId);
        if (tokenEntity == null){
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setExpireTime(expireDate);
            tokenEntity.setUpdateTime(curDate);
        }else {
            tokenEntity.setExpireTime(expireDate);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(curDate);
        }
        //保存和更新共用
        tokenRepository.save(tokenEntity);
        Rr rr = Rr.ok().put("token",token).put("expire",expireDate);
        return rr;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void logout(Long userId) {
        tokenRepository.deleteByUserId(userId);
    }
}
