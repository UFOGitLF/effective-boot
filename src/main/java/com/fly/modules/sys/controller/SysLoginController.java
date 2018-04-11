package com.fly.modules.sys.controller;

import com.fly.common.utils.Rr;
import com.fly.common.utils.ResultCodeConstants;
import com.fly.common.validator.ValidatorUtils;
import com.fly.modules.sys.entity.SysUserEntity;
import com.fly.modules.sys.form.SysLoginForm;
import com.fly.modules.sys.service.SysCaptchaService;
import com.fly.modules.sys.service.SysUserService;
import com.fly.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @Author : liufei on 2018/4/8
 */
@RestController
public class SysLoginController {
    @Resource
    private SysCaptchaService captchaService;
    @Resource
    private SysUserService userService;
    @Resource
    private SysUserTokenService tokenService;

    @ApiOperation("获取验证码")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response,String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @ApiOperation("登录")
    @PostMapping("/sys/login")
    public Map<String,Object> login(@RequestBody SysLoginForm loginForm){
        ValidatorUtils.validateEntity(loginForm);
        boolean validateResult = captchaService.validate(loginForm.getUuid(),loginForm.getCaptcha());

        //验证码
        if (!validateResult){
            return Rr.error(ResultCodeConstants.CAPTCHA_WRONG);
        }
        SysUserEntity userEntity = userService.queryByUserName(loginForm.getUsername());
        //账号与密码
        if (userEntity == null || !userEntity.getPassword()
                .equals(new Sha256Hash(loginForm.getPassword(),userEntity.getSalt()).toHex()) ){
            return Rr.error(ResultCodeConstants.USERNAME_OR_PASSWORD_WRONG);
        }
        if (userEntity.getStatus() == 0){
            return Rr.error(ResultCodeConstants.ACCOUNT_LOCKED);
        }
        //保存token
        Rr rr = tokenService.createToken(userEntity.getUserId());

        return rr;
    }
}
