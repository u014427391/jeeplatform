package org.muses.jeeplatform.cas.authentication.config;


import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.muses.jeeplatform.cas.authentication.handler.UsernamePasswordAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration("UsernamePasswordAuthConfig")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class UsernamePasswordAuthConfig implements AuthenticationEventExecutionPlanConfigurer {

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;


    @Bean
    public PrePostAuthenticationHandler myAuthenticationHandler() {
        // 定义为优先使用它进行认证
//        return new UsernamePasswordAuthenticationHandler(UsernamePasswordAuthenticationHandler.class.getName(),
//                servicesManager, new DefaultPrincipalFactory(), 1);

        return new UsernamePasswordAuthenticationHandler(UsernamePasswordAuthenticationHandler.class.getName(),
                servicesManager, new DefaultPrincipalFactory(), 1);
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(myAuthenticationHandler());
    }
}
