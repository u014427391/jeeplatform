package org.muses.jeeplatform.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.muses.jeeplatform.core.entity.admin.User;
import org.muses.jeeplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.muses.jeeplatform.core.CASConsts.CAS_FILTER_URL_PATTERN;
import static org.muses.jeeplatform.core.CASConsts.CAS_SERVER_URL_PREFIX;

/**
 * @description 基于Shiro框架的权限安全认证和授权
 * @author Nicky
 * @date 2017年3月12日
 */
public class ShiroRealm extends CasRealm {

	Logger LOG = LoggerFactory.getLogger(ShiroRealm.class);

	/**注解引入业务类**/
	@Resource
	UserService userService;

	@PostConstruct
	public void initProperty(){
		setCasServerUrlPrefix(CAS_SERVER_URL_PREFIX);
		//客户端回调地址
		setCasService(CAS_SERVER_URL_PREFIX + CAS_FILTER_URL_PATTERN);
	}
	
	/**
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		if(LOG.isInfoEnabled()) {
			LOG.info("=>执行Shiro权限认证");
		}

		String username = (String)token.getPrincipal();  				//得到用户名
		String password = new String((char[])token.getCredentials()); 	//得到密码
	     
		User user = userService.findByUsername(username);

		/* 检测是否有此用户 */
		if(user == null){
			throw new UnknownAccountException();//没有找到账号异常
		}
		/* 检验账号是否被锁定 */
		if(Boolean.TRUE.equals(user.getLocked())){
			throw new LockedAccountException();//抛出账号锁定异常
		}
		/* AuthenticatingRealm使用CredentialsMatcher进行密码匹配*/
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
		if(LOG.isInfoEnabled()) {
			LOG.info("=>执行Shiro授权");
		}
		String username = (String)pc.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	    authorizationInfo.setRoles(userService.getRoles(username));
	    authorizationInfo.setStringPermissions(userService.getPermissions(username));
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

}
