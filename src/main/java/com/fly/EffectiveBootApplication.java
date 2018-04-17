package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * @author liufei
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})//解决多数据源启动报错
public class EffectiveBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(EffectiveBootApplication.class, args);
	}
}
