package com.fly.modules.sys.controller;

import com.fly.common.utils.*;
import com.fly.common.validator.AbstractAssert;
import com.fly.common.validator.ValidatorUtils;
import com.fly.common.validator.group.AddGroup;
import com.fly.common.validator.group.UpdateGroup;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.form.PasswordForm;
import com.fly.modules.sys.service.SysUserRoleService;
import com.fly.modules.sys.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : liufei on 2018/4/9
 */
@RestController
@RequestMapping(value = "/sys/user")
public class SysUserController extends AbstractController{
    @Resource
    private SysUserService userService;
    @Resource
    private SysUserRoleService userRoleService;

    /**
     * 登录的用户信息
     */
    @GetMapping("/info")
    public Rr info(){
        return Rr.ok().put("user", getUser());
    }

    /**
     * 用户详情
     * @param userId
     * @return
     */
    @GetMapping("/info/{userId}")
    public Rr info(@PathVariable(value = "userId") Long userId){
        SysUserEntity user = userService.selectById(userId);

        List<Long> roleIdList = userRoleService.queryRoleIdListByUserId(userId);
        user.setRoleIdList(roleIdList);
        return Rr.ok().put("user",user);
    }
    /**
     * 用户列表
     * @return
     */
    @GetMapping("list")
    @ApiOperation("获取所有用户列表")
    @RequiresPermissions("sys:user:list")
    public Rr list(String username,
                   PageInfo pageInfo){
        Long createUserId = null;
        if (getUserId() != Constant.SUPER_ADMIN){
            createUserId = getUserId();
        }
        PageData page = userService.queryPage(pageInfo,username,createUserId);
        return Rr.ok().put("page",page);
    }
    /**
     * 用户新增
     * @param userEntity
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("sys:user:save")
    public Rr save(@RequestBody SysUserEntity userEntity){
        ValidatorUtils.validateEntity(userEntity, AddGroup.class);
        userEntity.setCreateUserId(getUserId());
        userService.insert(userEntity);
        return Rr.ok();
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
    @PostMapping("update")
    @RequiresPermissions("sys:user:update")
    public Rr update(@RequestBody SysUserEntity user){
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        userService.update(user);
        return Rr.ok();
    }
    /**
     * 删除用户
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("sys:user:delete")
    public Rr delete(@RequestBody Long[] ids){
        if (ArrayUtils.contains(ids,1L)){
            return Rr.error(ResultCodeConstants.USER_ADMIN_NOT_DELETE);
        }
        if (ArrayUtils.contains(ids,getUserId())){
            return Rr.error(ResultCodeConstants.CURRENT_USER_NOT_DELETE);
        }
        userService.deleteBatch(ids);
        return Rr.ok();
    }
}
