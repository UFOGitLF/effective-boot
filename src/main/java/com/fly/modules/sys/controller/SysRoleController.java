package com.fly.modules.sys.controller;

import com.fly.common.utils.Constant;
import com.fly.common.utils.Rr;
import com.fly.modules.sys.entity.SysRoleEntity;
import com.fly.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<SysRoleEntity> roleList = roleService.selectRolesByCondition(role);
        return Rr.ok().put("list",roleList);
    }
}
