package org.muses.jeeplatform.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
//@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class JeeplatformCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeeplatformCmsApplication.class, args);
    }

}
