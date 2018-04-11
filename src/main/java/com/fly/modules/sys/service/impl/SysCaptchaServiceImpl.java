package com.fly.modules.sys.service.impl;

import com.fly.common.exception.RrException;
import com.fly.common.utils.DateUtils;
import com.fly.common.utils.ResultCodeConstants;
import com.fly.modules.sys.entity.SysCaptchaEntity;
import com.fly.modules.sys.repostitory.SysCaptchaRepository;
import com.fly.modules.sys.service.SysCaptchaService;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @Author : liufei on 2018/4/8
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService{
    @Resource
    private Producer producer;

    @Resource
    private SysCaptchaRepository captchaRepository;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isBlank(uuid)){
            throw new RrException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptchaEntity captchaEntity = new SysCaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(),5));
        captchaRepository.save(captchaEntity);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptchaEntity captchaEntity = captchaRepository.findByUuid(uuid);
        if (captchaEntity == null){
            return false;
        }
        //删除验证码
        captchaRepository.deleteById(uuid);

        if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }
        return false;
    }

    @Override
    public SysCaptchaEntity findByUuid(String uuid) {
        return captchaRepository.findByUuid(uuid);
    }
}
