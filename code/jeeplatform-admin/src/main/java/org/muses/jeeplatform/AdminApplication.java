package org.muses.jeeplatform;


import org.muses.jeeplatform.common.cache.redis.RedisClient;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caiyuyu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, MybatisAutoConfiguration.class})
@ServletComponentScan
@EnableScheduling
@EnableTransactionManagement
//@EnableCaching
@EnableAsync
@Controller
public class AdminApplication {

    @Autowired
    RedisClient redisClient;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @RequestMapping("/set")
    public String set(String key, String value) throws Exception{
        redisClient.setValue(key, value);
        return "success";
    }

    @RequestMapping("/get")
    public String get(String key) throws Exception {
        return redisClient.getValue(key);
    }
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//        c.setIgnoreUnresolvablePlaceholders(true);
//        return c;
//    }

}
