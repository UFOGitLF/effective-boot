package com.fly.modules.sys.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证码
 * @author liufei
 */
@Table(name = "sys_captcha")
@Entity
@Data
public class SysCaptchaEntity {
    @Id
    private String uuid;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    public Date getExpireTime() {
        return (Date)expireTime.clone();
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime != null){
            this.expireTime = (Date)expireTime.clone();
        }

    }
}
