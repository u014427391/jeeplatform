package org.muses.jeeplatform.core.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.muses.jeeplatform.core.config.ShopBaseConfig.*;


/**
 *
 * @author caiyuyu
 *
 */
@Configuration
@MapperScan(
		basePackages = MAPPER_PACKAGES,
		sqlSessionFactoryRef = SQL_SESSION_FACTORY)
@EnableTransactionManagement
public class ShopMybatisConfig
{
    @Autowired
    @Qualifier(DATA_SOURCE_NAME)
    private DataSource dataSource;

    @Primary
	@Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return factoryBean.getObject();
	}

	@Primary
	@Bean(name = MYBATIS_TRANSACTION_MANAGER)
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}


}