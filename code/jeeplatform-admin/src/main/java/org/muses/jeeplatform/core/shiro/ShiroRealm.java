package org.muses.jeeplatform.core.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.muses.jeeplatform.core.entity.admin.User;
import org.muses.jeeplatform.service.UserService;

/**
 * @description 基于Shiro框架的权限安全认证和授权
 * @author Nicky
 * @date 2017年3月12日
 */
//登录机制代码搬到单点登录工程实现
public class ShiroRealm extends AuthorizingRealm {

	/**注解引入业务类**/
	@Resource
	UserService userService;
	
	/**
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		 String username = (String)token.getPrincipal();  				//得到用户名 
	     String password = new String((char[])token.getCredentials()); 	//得到密码
	     
	     User user = userService.findByUsername(username);

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
	    authorizationInfo.setRoles(userService.getRoles(username));
	    authorizationInfo.setStringPermissions(userService.getPermissions(username));
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

}
