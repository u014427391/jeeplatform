package org.muses.jeeplatform.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/29 15:06  修改内容:
 * </pre>
 */
@Configuration
@EnableAuthorizationServer//开启授权服务
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;   //认证方式
    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    private static final String CLIENT_ID = "jeeplatform";
    private static final String SECRET_CHAR_SEQUENCE = "secret";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final String USER ="user";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
    private static final String GRANT_TYPE_PASSWORD = "password";   // 密码模式授权模式
    private static final String AUTHORIZATION_CODE = "authorization_code"; //授权码模式  授权码模式使用到了回调地址
    private static final String REFRESH_TOKEN = "refresh_token";  //refresh token模式
    private static final String IMPLICIT = "implicit"; //简化授权模式
    private static final String RESOURCE_ID = "resource_id";    //指定哪些资源是需要授权验证的

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用内存存储
                .withClient(CLIENT_ID) //标记客户端id
                .secret(bCryptPasswordEncoder().encode(SECRET_CHAR_SEQUENCE))//客户端安全码
                .autoApprove(true) //为true 则不会被重定向到授权的页面，也不需要手动给请求授权,直接自动授权成功返回code
                .redirectUris("http://127.0.0.1:8082/oa/login", "http://127.0.0.1:8084/cms/login") //重定向uri
                .scopes(SCOPE_READ , SCOPE_WRITE , TRUST , USER) //允许授权范围
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS) //token 时间秒
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS)//刷新token 时间 秒
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD , AUTHORIZATION_CODE , REFRESH_TOKEN , IMPLICIT);//允许授权类型
    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
//                .accessTokenConverter(accessTokenConverter())
//                .userDetailsService(userDetailsService) //必须注入userDetailsService否则根据refresh_token无法加载用户信息
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.OPTIONS)  //支持GET  POST  请求获取token
//                .reuseRefreshTokens(true) //开启刷新token
//                .tokenServices(tokenServices());
//    }

    /**
     * 认证服务器的安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //.realm(RESOURCE_ID)
                .tokenKeyAccess("permitAll()");
                //.checkTokenAccess("isAuthenticated()") //isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
                //.allowFormAuthenticationForClients(); //允许表单认证  这段代码在授权码模式下会导致无法根据code　获取token　
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter(){
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String grantType = authentication.getOAuth2Request().getGrantType();
                //只有如下两种模式才能获取到当前用户信息
                if(AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
                    String userName = authentication.getUserAuthentication().getName();
                    // 自定义一些token 信息 会在获取token返回结果中展示出来
                    Map<String, Object> additionalInformation = new HashMap<String, Object>();
                    additionalInformation.put("user_name", userName);
                    additionalInformation = Collections.unmodifiableMap(additionalInformation);
                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                }
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        converter.setSigningKey("bcrypt");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        //基于jwt实现令牌（Access Token）
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        return defaultTokenServices;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
