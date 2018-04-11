package com.fly.modules.sys.controller;

import com.fly.common.exception.RrException;
import com.fly.common.utils.Constant;
import com.fly.common.utils.ResultCodeConstants;
import com.fly.common.utils.Rr;
import com.fly.modules.sys.entity.SysMenuEntity;
import com.fly.modules.sys.service.ShiroService;
import com.fly.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
        List<SysMenuEntity> menuList = menuService.queryUserMenuListByUserId(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return Rr.ok().put("menuList",menuList).put("permissions",permissions);
    }

    /**
     * 所有菜单列表
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("sys:menu:list ")
    public List<SysMenuEntity> list(){
        List<SysMenuEntity> menuList = menuService.queryAll();
        for (SysMenuEntity menuEntity : menuList) {
            SysMenuEntity parentMenuEntity = menuService.queryByMenuId(menuEntity.getParentId());
            if (null != parentMenuEntity){
                menuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Rr select(){
        //查询列表数据
        List<SysMenuEntity> menuList = menuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return Rr.ok().put("menuList", menuList);
    }

    /**
     * 保存
     * @param menuEntity
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("sys:menu:save")
    public Rr save(@RequestBody SysMenuEntity menuEntity){
        verifyForm(menuEntity);
        menuService.insert(menuEntity);
        return Rr.ok();
    }

    /**
     * 菜单信息
     * @param menuId
     * @return
     */
    @GetMapping("info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Rr info(@PathVariable(name = "menuId") Long menuId){
        SysMenuEntity menu = menuService.queryByMenuId(menuId);
        return Rr.ok().put("menu",menu);
    }

    @PostMapping("delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public Rr delete(@PathVariable(name = "menuId") Long menuId){
        if (menuId <= 26){
            return Rr.error(ResultCodeConstants.DELETE_SYSMENU_ERROR);
        }
        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = menuService.queryListByParentId(menuId);
        if (!CollectionUtils.isEmpty(menuList)){
            return Rr.error(ResultCodeConstants.FIRST_DELETE_SUBMENU);
        }
        menuService.delete(menuId);
        return Rr.ok();
    }


    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new RrException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new RrException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new RrException("菜单URL不能为空");
            }
        }

        //上级类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenuEntity parentMenu = menuService.queryByMenuId(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //菜单的上级只能为目录
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new RrException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮的上级只能为菜单
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new RrException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }

}
