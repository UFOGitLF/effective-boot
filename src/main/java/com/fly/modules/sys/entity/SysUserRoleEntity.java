package com.fly.modules.sys.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liufei
 */
@Table(name = "sys_user_role")
@Entity
@Data
public class SysUserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 角色ID
	 */
	@Column(name = "role_id")
	private Long roleId;
}
