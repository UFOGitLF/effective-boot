package com.fly.modules.sys.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @Author : liufei on 2018/4/9
 */
@Data
@Table(name = "sys_config")
@Entity
public class SysConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="参数名不能为空")
    @Column(name = "config_key")
    private String configKey;
    @NotBlank(message="参数值不能为空")
    @Column(name = "config_value")
    private String configValue;
    private String remark;

}
