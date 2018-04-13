package com.fly.modules.sys.service.impl;

import com.fly.modules.sys.entity.SysUserRoleEntity;
import com.fly.modules.sys.repostitory.SysUserRoleRepository;
import com.fly.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{
    @Resource
    private SysUserRoleRepository userRoleRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除
        userRoleRepository.deleteAllByUserId(userId);

        if (CollectionUtils.isEmpty(roleIdList)){
            return;
        }
        //保存用户与角色关系
        List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            list.add(sysUserRoleEntity);
        }
        userRoleRepository.saveAll(list);
    }

    @Override
    public List<Long> queryRoleIdListByUserId(Long userId) {
        List<BigInteger> list = userRoleRepository.findRoleIdsByUserId(userId);
        List<Long> longs = new ArrayList<>();
        for (BigInteger bigInteger : list) {
            longs.add(bigInteger.longValue());
        }
        return longs;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteUserRoleByRoleIds(Long[] roleIds) {
        userRoleRepository.deleteUserRoleByRoleIds(roleIds);
    }

    @Override
    public List<Long> findMenuIdsByUserId(Long userId) {
        List<BigInteger> list = userRoleRepository.findMenuIdsByUserId(userId);
        List<Long> longs = new ArrayList<>();
        for (BigInteger bigInteger : list) {
            longs.add(bigInteger.longValue());
        }
        return longs;
    }
}
