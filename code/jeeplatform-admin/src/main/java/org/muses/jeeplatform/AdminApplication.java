package org.muses.jeeplatform;


import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.validation.Assertion;
import org.muses.jeeplatform.cache.redis.RedisClient;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    @GetMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Assertion assertion = (Assertion) session.getAttribute("_const_cas_assertion_");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(200);
        if (assertion != null) {
            String redirectUrl= request.getParameter("redirectUrl");
            try {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.sendRedirect(redirectUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().print("401");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
