package org.muses.jeeplatform.core;

import org.springframework.boot.SpringApplication;

/**
 * @author caiyuyu
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, MybatisAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
