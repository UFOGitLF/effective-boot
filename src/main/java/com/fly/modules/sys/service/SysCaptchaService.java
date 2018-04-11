package com.fly.modules.sys.service;

import com.fly.modules.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * @Author : liufei on 2018/4/8
 */
public interface SysCaptchaService {
    /**
     * 获取图片验证码
     * @param uuid
     * @return
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码校验
     * @param uuid
     * @param code
     * @return
     */
    boolean validate(String uuid,String code);

    /**
     * 根据uuid查询
     * @param uuid
     * @return
     */
    SysCaptchaEntity findByUuid(String uuid);
}
