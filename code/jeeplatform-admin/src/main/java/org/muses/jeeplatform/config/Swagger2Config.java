package org.muses.jeeplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 *  Swagger2配置类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本: V1.0.1    修改人：  修改日期: 2019年05月12日  修改内容:
 * </pre>
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.muses.jeeplatform.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger2")
                .description("SpringBoot集成Swagger2构建RESTful API接口")
                .termsOfServiceUrl("https://smilenicky.blog.csdn.net")
                .contact("Nicky.Ma")
                .version("1.0.1")
                .build();
    }
}
