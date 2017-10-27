package org.muses.jeeplatform.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static org.muses.jeeplatform.config.BaseConfig.*;


/**
 * 配置主数据源
 *
 * @author caiyuyu
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = DATA_SOURCE_PROPERTIES)
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
