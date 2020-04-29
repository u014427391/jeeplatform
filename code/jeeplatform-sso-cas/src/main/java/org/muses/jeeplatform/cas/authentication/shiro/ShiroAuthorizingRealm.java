package org.muses.jeeplatform.cas.authentication.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.cas.user.model.User;
import org.muses.jeeplatform.cas.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/26 11:33  修改内容:
 * </pre>
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {

    Logger LOG = LoggerFactory.getLogger(ShiroAuthorizingRealm.class);

    /**注解引入业务类**/
    //@Autowired
    //UserService userService;

    /**
     * 登录信息和用户验证信息验证(non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();  				//得到用户名
        String password = new String((char[])token.getCredentials()); 	//得到密码

        LOG.info("Shiro doGetAuthenticationInfo>> username:{},password:{}",username,password);

        //User user = userService.findByUsername(username);
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

        User user = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));
        Subject subject = getCurrentExecutingSubject();
        //获取Shiro管理的Session
        Session session = getShiroSession(subject);
        //Shiro添加会话
        session.setAttribute("username", username);
        session.setAttribute(ShiroConsts.SESSION_USER, user);

        /**检测是否有此用户 **/
        if(user == null){
            throw new UnknownAccountException();//没有找到账号异常
        }
        /**检验账号是否被锁定 **/
        if(Boolean.TRUE.equals(user.getLocked())){
            throw new LockedAccountException();//抛出账号锁定异常
        }
        /**AuthenticatingRealm使用CredentialsMatcher进行密码匹配**/
        if(null != username && null != password){
            return new SimpleAuthenticationInfo(username, password, getName());
        }else{
            return null;
        }

    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        String username = (String)pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(userService.getRoles(username));
//        authorizationInfo.setStringPermissions(userService.getPermissions(username));
        System.out.println("Shiro授权");
        return authorizationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    protected Subject getCurrentExecutingSubject(){
        return SecurityUtils.getSubject();
    }

    protected Session getShiroSession(Subject subject){
        return subject.getSession();
    }

}
