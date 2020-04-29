package org.muses.jeeplatform.cas.authentication.handler;


import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.muses.jeeplatform.cas.user.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.*;

public class UsernamePasswordAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationHandler.class);

    public UsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

        String username = usernamePasswordCredential.getUsername();
        String password = usernamePasswordCredential.getPassword();

        System.out.println("username : " + username);
        System.out.println("password : " + password);

        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.0.152:33306/jeeplatform");
        dataSource.setUsername("root");
        dataSource.setPassword("minstone");

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql = "SELECT * FROM sys_user WHERE username = ?";

        User info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));

        System.out.println("database username : "+ info.getUsername());
        System.out.println("database password : "+ info.getPassword());


        if (info == null) {
            throw new AccountException("Sorry, username not found!");
        }

        if (!info.getPassword().equals(password)) {
            throw new FailedLoginException("Sorry, password not correct!");
        } else {

            final List<MessageDescriptor> list = new ArrayList<>();

            return createHandlerResult(usernamePasswordCredential,
                    this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }
    }


    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }
}
