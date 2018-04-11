package com.fly.modules.sys.service.impl;

import com.fly.common.utils.PageData;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.repostitory.SysUserRepository;
import com.fly.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.*;
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
import java.util.Map;

/**
 * @Author : liufei on 2018/4/8
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    @Resource
    private SysUserRepository userRepository;

    @Override
    public SysUserEntity queryByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return userRepository.findMenuIdsByUserId(userId);
    }

    @Override
    public PageData queryPage(Map<String,Object> params) {
        Integer pageNo = Integer.valueOf(params.get("page").toString()) -1;
        Integer pageSize = Integer.valueOf(params.get("limit").toString());
        String username = params.get("username").toString();
        Long createUserId = Long.valueOf(params.get("createUserId") != null ? params.get("createUserId").toString() : "-1");
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,"user_id");

        Page<SysUserEntity> page = userRepository.findAll(new Specification<SysUserEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<SysUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(username)){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class),username));
                }
                if (createUserId != -1){
                    list.add(criteriaBuilder.equal(root.get("createUserId").as(Long.class),createUserId));
                }
                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        },pageable);

        return new PageData(page);
    }
}
