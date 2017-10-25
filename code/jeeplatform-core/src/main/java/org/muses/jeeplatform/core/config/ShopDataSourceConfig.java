package org.muses.jeeplatform.core.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static org.muses.jeeplatform.core.config.ShopBaseConfig.*;


/**
 * 配置主数据源
 *
 * @author caiyuyu
 */
@Configuration
public class ShopDataSourceConfig {

    @Primary
    @Bean(name = DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = DATA_SOURCE_PROPERTIES)
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
