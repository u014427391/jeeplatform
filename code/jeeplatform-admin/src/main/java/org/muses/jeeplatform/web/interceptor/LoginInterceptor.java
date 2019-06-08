package org.muses.jeeplatform.web.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @description 基于Spring框架的拦截器，继承HandlerInterceptorAdapter，
 * 该接口是通过适配器模式进行设计的
 * @author Nicky
 * @date 2017年3月16日
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String urlpath = request.getServletPath();
		/**正则表达式过滤,不匹配该值的就拦截处理**/
		if(urlpath.matches(CommonConsts.REGEXP_PATH)){
			return true;
		}else {
			//shiro框架的会话管理，获取Session，校验用户是否通过登录验证
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			User user = (User)session.getAttribute(CommonConsts.SESSION_USER);
			if(user != null){
				/**加入权限校验，待开发...**/
				return true;
			}else{
				//重定向到登录页面
				response.sendRedirect(request.getContextPath() + CommonConsts.URL_LOGIN);
				return false;
			}
		}
	}

}
