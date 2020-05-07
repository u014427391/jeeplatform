package org.muses.jeeplatform.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class JeeplatformSsoOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(JeeplatformSsoOauth2Application.class, args);
    }

}
