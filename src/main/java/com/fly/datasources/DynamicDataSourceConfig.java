package com.fly.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : liufei on 2018/4/8
 */
@Configuration
public class DynamicDataSourceConfig {
    /**
     * 数据源个数限制
     */
    private static final int DATASOURCE_NUM = 5;

    @Bean
    @ConfigurationProperties(value = "spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource,DataSource secondDataSource){
        Map<Object,Object> targetDataSources = new HashMap<>(DATASOURCE_NUM);
        targetDataSources.put(DataSourceNames.FIRST,firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND,secondDataSource);
        return new DynamicDataSource(firstDataSource,targetDataSources);
    }




}
