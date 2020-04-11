package org.muses.jeeplatform.config;

import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020年04月11日  修改内容:
 * </pre>
 */
@Configuration
@EnableCasClient
public class CASConfig extends CasClientConfigurerAdapter {

    private static final String CAS_SERVER_URL_LOGIN = "http://localhost:8080/cas/login";
    private static final String SERVER_NAME = "http://localhost:8081/";


//    @Override
//    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
//        super.configureAuthenticationFilter(authenticationFilter);
//        //authenticationFilter.getInitParameters().put("authenticationRedirectStrategyClass","com.patterncat.CustomAuthRedirectStrategy");
//    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String,String>(16);
        initParameters.put("casServerLoginUrl",CAS_SERVER_URL_LOGIN);
        initParameters.put("serverName",SERVER_NAME);
        initParameters.put("ignorePattern","/logoutSuccess/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
