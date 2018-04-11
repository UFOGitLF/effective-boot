package com.fly.modules.sys.service.impl;

import com.fly.common.utils.Constant;
import com.fly.modules.sys.entity.SysMenuEntity;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.entity.SysUserTokenEntity;
import com.fly.modules.sys.repostitory.SysMenuRepository;
import com.fly.modules.sys.repostitory.SysUserRepository;
import com.fly.modules.sys.repostitory.SysUserTokenRepository;
import com.fly.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author : liufei on 2018/4/9
 */
@Service
public class ShiroServiceImpl implements ShiroService{
    @Resource
    private SysMenuRepository menuRepository;
    @Resource
    private SysUserRepository userRepository;
    @Resource
    private SysUserTokenRepository tokenRepository;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = menuRepository.findAll();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = userRepository.findPermsByUserId(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList){
            if (StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
