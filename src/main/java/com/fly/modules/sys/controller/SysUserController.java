package com.fly.modules.sys.controller;

import com.fly.common.utils.Constant;
import com.fly.common.utils.PageData;
import com.fly.common.utils.PageInfo;
import com.fly.common.utils.Rr;
import com.fly.modules.sys.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : liufei on 2018/4/9
 */
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController extends AbstractController{
    @Resource
    private SysUserService userService;
    /**
     * 登录的用户信息
     */
    @GetMapping("/info")
    public Rr info(){
        return Rr.ok().put("user", getUser());
    }

    /**
     * 用户列表
     * @return
     */
    @GetMapping("list")
    @ApiOperation("获取所有用户列表")
    @RequiresPermissions("sys:user:list")
    public Rr list(String username,
                   Long createUserId,
                   PageInfo pageInfo){
        if (getUserId() != Constant.SUPER_ADMIN){
            createUserId = getUserId();
        }
        PageData page = userService.queryPage(pageInfo,username,createUserId);
        return Rr.ok().put("page",page);
    }

}
