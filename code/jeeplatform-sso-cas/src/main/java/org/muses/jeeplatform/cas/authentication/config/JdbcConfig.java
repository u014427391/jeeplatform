package org.muses.jeeplatform.cas.authentication.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * <pre>
 *  jdbc配置类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/26 15:36  修改内容:
 * </pre>
 */
@Configuration
public class JdbcConfig {

    /**
     * jdbc驱动连接池
     * @Author mazq
     * @Date 2020/04/26 15:39
     * @Param []
     * @return org.springframework.jdbc.datasource.DriverManagerDataSource
     */
    @Bean("driverManagerDataSource")
    public DriverManagerDataSource driverManagerDataSource(){
        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.152:33306/jeeplatform");
        dataSource.setUsername("root");
        dataSource.setPassword("minstone");
        return dataSource;
    }

    /**
     *  创建JDBC模板
     * @Author mazq
     * @Date 2020/04/26 15:39
     * @Param []
     * @return org.springframework.jdbc.core.JdbcTemplate
     */
    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(driverManagerDataSource());
        return jdbcTemplate;
    }
}
