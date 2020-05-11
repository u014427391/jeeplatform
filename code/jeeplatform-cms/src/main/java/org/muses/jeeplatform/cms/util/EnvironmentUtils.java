package org.muses.jeeplatform.cms.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * copy
 * @ https://github.com/chengjiansheng/cjs-oauth2-sso-demo/blob/master/oauth2-sso-client-member/src/main/java/com/cjs/example/util/EnvironmentUtils.java
 * @Date 2020/05/11 11:47
 */
@Component
public class EnvironmentUtils implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getActiveProfile() {
        if (environment.getActiveProfiles().length > 0) {
            return environment.getActiveProfiles()[0];
        }
        return environment.getDefaultProfiles()[0];
    }
}