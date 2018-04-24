package com.fly.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.common.utils.Constant;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liufei
 */
@Table(name = "sys_log")
@Entity
@Data
public class SysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 执行时长(毫秒)
	 */
	private Long time;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@JsonFormat(pattern = Constant.DATE_TIMESTAMP)
	private Date createDate;

	public Date getCreateDate() {
		if (createDate != null){
			return (Date)createDate.clone();
		}
		return null;
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null){
			this.createDate = (Date)createDate.clone();
		}
	}
}
