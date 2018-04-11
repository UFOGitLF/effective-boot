package com.fly.modules.sys.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author liufei
 */
@Table(name = "sys_role_menu")
@Entity
@Data
public class SysRoleMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 角色ID
	 */
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 菜单ID
	 */
	@Column(name = "menu_id")
	private Long menuId;
}
