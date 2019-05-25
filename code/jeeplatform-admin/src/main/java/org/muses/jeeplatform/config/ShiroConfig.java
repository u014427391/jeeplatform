package org.muses.jeeplatform.config;

import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.muses.jeeplatform.core.shiro.ShiroRealm;
import org.muses.jeeplatform.web.filter.SysAccessControllerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.muses.jeeplatform.core.CASConsts.*;

/**
 * @author caiyuyu
 */
@Configuration
public class ShiroConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     *  单点登出监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean singleSignOutHttpSeessionListener(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }

    /**
     * 注册单点登出的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        return bean;
    }


    /**
     * CAS过滤器
     * @return
     */
    @Bean
    public CasFilter getCasFilter(){
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        casFilter.setFailureUrl(CAS_CLIENT_LOGIN_URL);
        casFilter.setSuccessUrl(LOGIN_SUCCESS_URL);
        return casFilter;
    }

    /**
     * 定义ShrioRealm
     * @return
     */
    @Bean
    public ShiroRealm myShiroRealm(){
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    /**
     * Shiro Security Manager
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSubjectFactory(new CasSubjectFactory());
        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,CasFilter casFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注册Shrio Security Manager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl(CAS_CLIENT_LOGIN_URL);
        shiroFilterFactoryBean.setSuccessUrl(LOGIN_SUCCESS_URL);
        shiroFilterFactoryBean.setUnauthorizedUrl(LOGIN_UNAUTHORIZED_URL);

        //添加CasFilter到ShiroFilter
        Map<String,Filter> filters = new HashMap<String,Filter>();
        filters.put("casFilter",casFilter);
        shiroFilterFactoryBean.setFilters(filters);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //Shiro集成CAS后需要添加该规则
        filterChainDefinitionMap.put(CAS_FILTER_URL_PATTERN,"casFilter");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/code", "anon");
        //filterChainDefinitionMap.put("/login", "anon");
        //filterChainDefinitionMap.put("/logincheck", "anon");
        //filterChainDefinitionMap.put("/logout","anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     * @return
     */
    public SysAccessControllerFilter kickoutSessionControlFilter(){
        SysAccessControllerFilter filter = new SysAccessControllerFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
//        filter.setCacheManager(cacheManager());
//        //用于根据会话ID，获取会话进行踢出操作的；
//        filter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        filter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        filter.setMaxSession(1);
        //被踢出后重定向到的地址；
        filter.setUrl("/login");
        return filter;
    }
}
