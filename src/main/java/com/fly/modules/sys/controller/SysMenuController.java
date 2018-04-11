package com.fly.modules.sys.controller;

import com.fly.common.utils.Rr;
import com.fly.modules.sys.entity.SysMenuEntity;
import com.fly.modules.sys.service.ShiroService;
import com.fly.modules.sys.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Author : liufei on 2018/4/10
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController{
    @Resource
    private SysMenuService menuService;
    @Resource
    private ShiroService shiroService;

    /**
     * 左侧导航菜单
     * @return
     */
    @GetMapping("nav")
    public Rr nav(){
        List<SysMenuEntity> menuList = menuService.findUserMenuListByUserId(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return Rr.ok().put("menuList",menuList).put("permissions",permissions);
    }

}
