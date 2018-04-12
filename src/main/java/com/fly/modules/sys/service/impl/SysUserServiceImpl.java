package com.fly.modules.sys.service.impl;

import com.fly.common.exception.RrException;
import com.fly.common.utils.Constant;
import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.modules.sys.entity.SysRoleEntity;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.repostitory.SysUserRepository;
import com.fly.modules.sys.service.SysRoleService;
import com.fly.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
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
    @Resource
    private SysRoleService roleService;

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
                    list.add(criteriaBuilder.equal(root.get("createUserId").as(Long.class),createUserId));
                }
                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, PageRequest.of(pageInfo.getPage()-1,pageInfo.getLimit()));

        return new PageData(userPage);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = userRepository.findByUserId(userId);
        if (null == userEntity && !StringUtils.equals(userEntity.getPassword(),password)){
            return false;
        }
        Integer result = userRepository.updatePassword(newPassword,userId);
        return result == 0?false:true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void insert(SysUserEntity userEntity) {
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        userEntity.setSalt(salt);
        userEntity.setPassword(new Sha256Hash(userEntity.getPassword(),salt).toHex());
        userRepository.save(userEntity);
        checkRole(userEntity);

        //TODO 保存用户与角色关系

    }

    @Override
    public SysUserEntity selectById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteBatch(Long[] ids) {
        userRepository.deleteAllByIds(ids);
    }

    @Override
    public void update(SysUserEntity user) {
        userRepository.save(user);
    }

    private void checkRole(SysUserEntity userEntity) {
        if (CollectionUtils.isEmpty(userEntity.getRoleIdList())){
            return;
        }
        if (userEntity.getCreateUserId() == Constant.SUPER_ADMIN){
            return;
        }
        //查询本人创建的角色列表
        List<Long> roleIds =  roleService.selectRolesByCreateUserId(userEntity.getCreateUserId());

        //判断是否越权
        if (!roleIds.containsAll(userEntity.getRoleIdList())){
            throw new RrException("新增用户所选角色，不是本人创建");
        }
    }
}
