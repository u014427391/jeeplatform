package org.muses.jeeplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.muses.jeeplatform.config.BaseConfig.*;


/**
 * 配置 JPA
 *
 * @author caiyuyu
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = ENTITY_MANAGER_FACTORY,
        transactionManagerRef = JPA_TRANSACTION_MANAGER,
        basePackages = {REPOSITORY_PACKAGES})
public class JpaConfig {

    @Autowired
    @Qualifier(DATA_SOURCE_NAME)
    private DataSource dataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean(name = ENTITY_MANAGER)
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return this.entityManagerFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(jpaProperties.getHibernateProperties(dataSource))
                .packages(ENTITY_PACKAGES)
                .persistenceUnit(PERSISTENCE_UNIT)
                .build();
    }

    @Primary
    @Bean(name = JPA_TRANSACTION_MANAGER)
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(this.entityManagerFactory(builder).getObject());
    }

}