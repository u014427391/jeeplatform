package org.muses.jeeplatform.core.config;

/**
 * @author caiyuyu
 */
public class ShopBaseConfig {

    /**
     * 设置主数据源名称
     */
    public static final String DATA_SOURCE_NAME = "shopDataSource";

    /**
     * 加载配置文件信息
     */
    public static final String DATA_SOURCE_PROPERTIES = "spring.datasource.shop";

    /**
     * repository 所在包
     */
    public static final String REPOSITORY_PACKAGES = "com.muses.jeeplatform.core.dao.repository";

    /**
     * mapper 所在包
     */
    public static final String MAPPER_PACKAGES = "com.com.muses.jeeplatform.core.dao.mapper";

    /**
     * 实体类 所在包
     */
    public static final String ENTITY_PACKAGES = "com.com.muses.jeeplatform.core.entity";

    /**
     * JPA 实体管理器
     */
    public static final String ENTITY_MANAGER = "shopEntityManager";

    /**
     * JPA 实体管理器工厂
     */
    public static final String ENTITY_MANAGER_FACTORY = "shopEntityManagerFactory";

    /**
     * JPA 事务管理器
     */
    public static final String JPA_TRANSACTION_MANAGER = "shopJpaTransactionManager";

    /**
     * JPA 持久化单元
     */
    public static final String PERSISTENCE_UNIT = "shopPersistenceUnit";

    /**
     * Mybatis session 工厂
     */
    public static final String SQL_SESSION_FACTORY = "shopSqlSessionFactory";

    /**
     * Mybatis 事务管理器
     */
    public static final String MYBATIS_TRANSACTION_MANAGER = "shopMybatisTransactionManager";
}
