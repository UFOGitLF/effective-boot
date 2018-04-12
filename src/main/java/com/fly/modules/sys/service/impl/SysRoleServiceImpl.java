package com.fly.modules.sys.service.impl;

import com.fly.modules.sys.entity.SysRoleEntity;
import com.fly.modules.sys.repostitory.SysRoleRepository;
import com.fly.modules.sys.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Resource
    private SysRoleRepository roleRepository;

    @Override
    public List<SysRoleEntity> selectRolesByCondition(SysRoleEntity role) {
        Specification<SysRoleEntity> spec = new Specification<SysRoleEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysRoleEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(role.getRoleName())){
                    list.add(cb.like(root.get("roleName").as(String.class),role.getRoleName()));
                }
                if (null != role.getCreateUserId()){
                    list.add(cb.equal(root.get("createUserId").as(Long.class),role.getCreateUserId()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                return cb.and(list.toArray(predicates));
            }
        };
        return roleRepository.findAll(spec);
    }

    @Override
    public List<Long> selectRolesByCreateUserId(Long createUserId) {
        return roleRepository.findRoleIdsByCreateUserId(createUserId);
    }
}
