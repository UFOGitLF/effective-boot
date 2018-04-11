package com.fly.modules.sys.service.impl;

import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.repostitory.SysUserRepository;
import com.fly.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : liufei on 2018/4/8
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    @Resource
    private SysUserRepository userRepository;

    @Override
    public SysUserEntity queryByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return userRepository.findMenuIdsByUserId(userId);
    }

    @Override
    public PageData queryPage(PageInfo pageInfo,String username,Long createUserId) {
        Page<SysUserEntity> userPage = userRepository.findAll(new Specification<SysUserEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(username)){
                    list.add(criteriaBuilder.like(root.get("username").as(String.class),username));
                }
                if (null != createUserId){
                    list.add(criteriaBuilder.equal(root.get("createUserId").as(String.class),createUserId));
                }
                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, PageRequest.of(pageInfo.getPageNo()-1,pageInfo.getPageSize()));

        return new PageData(userPage);
    }
}
