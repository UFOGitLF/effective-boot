package com.fly.modules.sys.controller;

import com.fly.common.utils.*;
import com.fly.common.validator.AbstractAssert;
import com.fly.modules.sys.form.PasswordForm;
import com.fly.modules.sys.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 修改密码
     * @param form
     * @return
     */
    @PostMapping("password")
    public Rr password(@RequestBody PasswordForm form){
        AbstractAssert.isBlank(form.getNewPassword(),"新密码不能为空");
        //原密码
        String password = new Sha256Hash(form.getPassword(),getUser().getSalt()).toHex();
        //新密码
        String newPassword = new Sha256Hash(form.getNewPassword(),getUser().getSalt()).toHex();
        //更新密码
        boolean flag = userService.updatePassword(getUserId(),password,newPassword);

        if (!flag){
            return Rr.error(ResultCodeConstants.OLD_PASSWORD_WRONG);
        }
        return Rr.ok();
    }

}
