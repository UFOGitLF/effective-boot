package com.fly.modules.sys.service.impl;

import com.fly.common.exception.RrException;
import com.fly.common.utils.Constant;
import com.fly.common.utils.PageInfo;
import com.fly.modules.sys.entity.SysRoleEntity;
import com.fly.modules.sys.repostitory.SysRoleRepository;
import com.fly.modules.sys.service.SysRoleMenuService;
import com.fly.modules.sys.service.SysRoleService;
import com.fly.modules.sys.service.SysUserRoleService;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Resource
    private SysRoleRepository roleRepository;
    @Resource
    private SysRoleMenuService roleMenuService;
    @Resource
    private SysUserService userService;
    @Resource
    private SysUserRoleService userRoleService;

    @Override
    public Page<SysRoleEntity> selectRolesByCondition(SysRoleEntity role,PageInfo pageInfo) {
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
        if (null == pageInfo){
            return roleRepository.findAll(spec,PageRequest.of(0,Integer.MAX_VALUE));
        }
        return roleRepository.findAll(spec,PageRequest.of(pageInfo.getPage()-1,pageInfo.getLimit()));
    }

    @Override
    public List<Long> selectRolesByCreateUserId(Long createUserId) {
        return roleRepository.findRoleIdsByCreateUserId(createUserId);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void insert(SysRoleEntity role) {
        roleRepository.save(role);
        //检查是否越权
        checkPerms(role);
        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getMenuIdList(),role.getRoleId());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(SysRoleEntity role) {
        roleRepository.save(role);
        //检查是否越权
        checkPerms(role);
        //更新角色与菜单关系
        roleMenuService.saveOrUpdate(role.getMenuIdList(),role.getRoleId());
    }

    @Override
    public void deleteBatch(Long[] ids) {
        //删除角色
        roleRepository.deleteBatch(ids);
        //删除角色与菜单关联关系
        roleMenuService.deleteRoleMenuByRoleId(ids);
        //删除角色与用户关联关系
        userRoleService.deleteUserRoleByRoleId(ids);

    }

    private void checkPerms(SysRoleEntity role) {
        if (role.getCreateUserId() == Constant.SUPER_ADMIN){
            return;
        }
        //如果不是超级管理员,需要判断角色的权限是否超过自己的权限
        List<Long> menuIdList = userService.queryAllMenuId(role.getCreateUserId());
        if (!menuIdList.containsAll(role.getMenuIdList())){
            throw new RrException("新增角色的权限，已超出你的权限范围");
        }
    }
}
