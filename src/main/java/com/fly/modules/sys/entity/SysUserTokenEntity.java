package com.fly.modules.sys.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liufei
 */
@Table(name = "sys_user_token")
@Entity
@Data
public class SysUserTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	/**
	 * token
	 */
	private String token;
	/**
	 * 过期时间
	 */
	@Column(name = "expire_time")
	private Date expireTime;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;
}
