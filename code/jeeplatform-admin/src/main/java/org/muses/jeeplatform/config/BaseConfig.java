package org.muses.jeeplatform.config;

/**
 * @author caiyuyu
 */
public class BaseConfig {

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
    public static final String REPOSITORY_PACKAGES = "org.muses.jeeplatform.core.dao.repository.admin";

    /**
     * mapper 所在包
     */
    public static final String MAPPER_PACKAGES = "org.muses.jeeplatform.core.dao.mapper.admin";

    /**
     * 实体类 所在包
     */
    public static final String ENTITY_PACKAGES = "org.muses.jeeplatform.core.entity.admin";

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

    /**
     * Jedis连接池
     */
    public static final String JEDIS_POOL = "jedisPool";

    /**
     * Jedis连接池配置
     */
    public static final String JEDIS_POOL_CONFIG = "jedisPoolConfig";
}
