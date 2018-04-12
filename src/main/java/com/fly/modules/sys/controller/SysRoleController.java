package com.fly.modules.sys.controller;

import com.fly.common.utils.Constant;
import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.common.utils.Rr;
import com.fly.common.validator.ValidatorUtils;
import com.fly.modules.sys.entity.SysRoleEntity;
import com.fly.modules.sys.service.SysRoleMenuService;
import com.fly.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : liufei on 2018/4/12
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController{
    @Resource
    private SysRoleService roleService;
    @Resource
    private SysRoleMenuService roleMenuService;

    /**
     * 角色列表
     * @return
     */
    @GetMapping("select")
    @RequiresPermissions("sys:role:select")
    public Rr select(){
        SysRoleEntity role = new SysRoleEntity();
        if (getUserId() != Constant.SUPER_ADMIN){
            role.setCreateUserId(getUserId());
        }
        List<SysRoleEntity> roleList = roleService.selectRolesByCondition(role,null).getContent();
        return Rr.ok().put("list",roleList);
    }

    /**
     * 角色详情
     * @param roleId
     * @return
     */
    @GetMapping("info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Rr info(@PathVariable(value = "roleId") Long roleId){
        SysRoleEntity role = new SysRoleEntity();
        role.setRoleId(roleId);
        //角色基本信息
        SysRoleEntity r = roleService.selectRolesByCondition(role,null).getContent().get(0);
        //角色对应的菜单列表
        List<Long> menIdList = roleMenuService.queryMenuIdList(roleId);
        r.setMenuIdList(menIdList);

        return Rr.ok().put("role",r);
    }

    /**
     * 角色列表
     * @param pageInfo
     * @param roleName
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("sys:role:list")
    public Rr list(PageInfo pageInfo,
                   String roleName){
        Long createUserId = null;
        if (getUserId() != Constant.SUPER_ADMIN){
            createUserId = getUserId();
        }
        SysRoleEntity roleEntity = new SysRoleEntity();
        roleEntity.setRoleName(roleName);
        roleEntity.setCreateUserId(createUserId);
        PageData page = new PageData(roleService.selectRolesByCondition(roleEntity,pageInfo));
        return Rr.ok().put("page",page);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Rr save(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        roleService.insert(role);
        return Rr.ok();
    }
    /**
     * 角色修改
     */
    @PostMapping("update")
    @RequiresPermissions("sys:role:update")
    public Rr update(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        roleService.update(role);
        return Rr.ok();
    }

    @PostMapping("delete")
    @RequiresPermissions("sys:role:delete")
    public Rr delete(@RequestBody Long[] ids){
        roleService.deleteBatch(ids);
        return Rr.ok();
    }
}
