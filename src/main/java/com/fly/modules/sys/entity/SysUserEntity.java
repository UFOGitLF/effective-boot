package com.fly.modules.sys.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.common.utils.Constant;
import com.fly.common.validator.group.AddGroup;
import com.fly.common.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liufei
 */
@Table(name = "sys_user")
@Entity
@Data
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@NotNull(message = "用户ID不能我i空",groups = UpdateGroup.class)
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	
	/**
	 * 角色ID列表
	 */
	@Transient
	private List<Long> roleIdList;
	
	/**
	 * 创建者ID
	 */
	@Column(name = "create_user_id")
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@JsonFormat(pattern = Constant.DATE_SHORT)
	private Date createTime;
}
