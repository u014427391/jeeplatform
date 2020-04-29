package org.muses.jeeplatform.config;

import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  TODO 接入CAS 单点登录
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020年04月11日  修改内容:
 * </pre>
 */
//@Configuration
//@EnableCasClient
//@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CASConfig extends CasClientConfigurerAdapter {

//    @Autowired
//    private CasConfigurationProperties casProperties;

    private static final String CAS_SERVER_URL_LOGIN = "http://127.0.0.1:8080/cas/login";
    private static final String SERVER_NAME = "http://127.0.0.1:8081/";

    private static final String AUTHENTICATION_REDIRECT_STRATEGY_CLASS  = "org.muses.jeeplatform.authentication.cas.CustomAuthticationRedirectStrategy";


    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        super.configureAuthenticationFilter(authenticationFilter);
        authenticationFilter.getInitParameters().put("authenticationRedirectStrategyClass",AUTHENTICATION_REDIRECT_STRATEGY_CLASS);
    }

    @Override
    public void configureValidationFilter(FilterRegistrationBean validationFilter) {
        Map<String, String> initParameters = validationFilter.getInitParameters();
        initParameters.put("encodeServiceUrl", "false");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String,String>(4);
        initParameters.put("casServerLoginUrl",CAS_SERVER_URL_LOGIN);
        initParameters.put("serverName",SERVER_NAME);
        initParameters.put("ignorePattern","/logoutSuccess/*");
        // 自定义重定向策略
        initParameters.put("authenticationRedirectStrategyClass", AUTHENTICATION_REDIRECT_STRATEGY_CLASS);
        registrationBean.setInitParameters(initParameters);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /*@Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<CorsFilter>();
        registrationBean.setFilter(new CorsFilter(source));
        registrationBean.setOrder(1);
        return registrationBean;
    }*/



}
