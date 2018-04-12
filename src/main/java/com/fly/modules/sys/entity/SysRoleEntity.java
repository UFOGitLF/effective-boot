package com.fly.modules.sys.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liufei
 */
@Table(name = "sys_role")
@Entity
@Data
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	@Column(name = "role_name")
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建者ID
	 */
	@Column(name = "create_user_id")
	private Long createUserId;

	@Transient
	private List<Long> menuIdList;
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@CreationTimestamp
	private Date createTime;
}
