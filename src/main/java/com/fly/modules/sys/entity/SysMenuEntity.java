package com.fly.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author liufei
 */
@Table(name = "sys_menu")
@Entity
@Data
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column(name = "parent_id")
	private Long parentId;
	
	/**
	 * 父菜单名称
	 */
	@Column(name = "parent_name")
	private String parentName;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private Integer type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	@Column(name = "order_num")
	private Integer orderNum;
	
	/**
	 * ztree属性
	 */
	private Boolean open;

	@Transient
	private List<?> list;
}
