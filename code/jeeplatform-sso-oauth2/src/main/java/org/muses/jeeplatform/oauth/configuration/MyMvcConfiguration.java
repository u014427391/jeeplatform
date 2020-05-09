package org.muses.jeeplatform.oauth.configuration;

import org.muses.jeeplatform.oauth.component.MessagesLocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年11月23日  修改内容:
 * </pre>
 */
@Configuration
//@ComponentScan(basePackages = { "com.example.springboot.web" })
//@Order(0)
@EnableConfigurationProperties({ WebMvcProperties.class})
public class MyMvcConfiguration implements WebMvcConfigurer{

    //装载WebMvcProperties 属性
    @Autowired
    WebMvcProperties webMvcProperties;
    /**
     * 自定义LocalResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:45
     * @return org.springframework.web.servlet.LocaleResolver
     */
    @Bean
    public LocaleResolver  localeResolver(){
        MessagesLocalResolver localResolver = new MessagesLocalResolver();
        localResolver.setDefaultLocale(webMvcProperties.getLocale());
        return localResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");;
    }


}
