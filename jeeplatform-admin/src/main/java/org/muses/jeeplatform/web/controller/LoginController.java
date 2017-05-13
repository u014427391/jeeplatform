package org.muses.jeeplatform.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.entity.Menu;
import org.muses.jeeplatform.entity.Permission;
import org.muses.jeeplatform.entity.Role;
import org.muses.jeeplatform.entity.User;
import org.muses.jeeplatform.service.MenuService;
import org.muses.jeeplatform.service.UserService;
import org.muses.jeeplatform.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description 登录操作的控制类，使用Shiro框架，做好了登录的权限安全认证，
 * getRemortIP()方法获取用户登录时的ip并保存到数据库
 * @author Nicky
 * @date 2017年3月15日
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	UserService userService;
	@Autowired
	MenuService menuService;
	
	/**
	 * 获取登录用户的IP
	 * @throws Exception 
	 */
	public void getRemortIP(String username)  {  
		HttpServletRequest request = this.getRequest();
		Map<String,String> map = new HashMap<String,String>();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		map.put("username", username);
		map.put("loginIp", ip);
		userService.saveIP(map);
	}  
	
	/**
	 * 访问后台登录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login",produces="text/html;charset=UTF-8")
	public ModelAndView toLogin()throws ClassNotFoundException{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("admin/frame/login");
		return mv;
	}
	
	/**
	 * 基于Shiro框架的登录验证,页面发送JSON请求数据，
	 * 服务端进行登录验证之后，返回Json响应数据，"success"表示验证成功
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/logincheck", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String loginCheck(HttpServletRequest request)throws AuthenticationException{
		JSONObject obj = new JSONObject();
		String errInfo = "";//错误信息
		String logindata[] = request.getParameter("LOGINDATA").split(",");
		if(logindata != null && logindata.length == 3){
			//获取Shiro管理的Session
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			String codeSession = (String)session.getAttribute(Constants.SESSION_SECURITY_CODE);
			String code = logindata[2]; 
			/**检测页面验证码是否为空，调用工具类检测**/
			if(Tools.isEmpty(code)){
				errInfo = "nullcode";
			}else{
				String username = logindata[0];
				String password = logindata[1];
				if(Tools.isNotEmpty(codeSession) && codeSession.equalsIgnoreCase(code)){
					//Shiro框架SHA加密
					String passwordsha = new SimpleHash("SHA-1",username,password).toString();
					//检测用户名和密码是否正确
					User user = userService.doLoginCheck(username,passwordsha);
					if(user != null){
						if(Boolean.TRUE.equals(user.getLocked())){
							errInfo = "locked";
						}else{
							//Shiro添加会话
							session.setAttribute("username", username);
							session.setAttribute(Constants.SESSION_USER, user);
							//删除验证码Session
							session.removeAttribute(Constants.SESSION_SECURITY_CODE);
							//保存登录IP
							getRemortIP(username);
							/**Shiro加入身份验证**/
							Subject sub = SecurityUtils.getSubject();
							UsernamePasswordToken token = new UsernamePasswordToken(username,password);
							sub.login(token);
						}
					}else{
						//账号或者密码错误
						errInfo = "uerror";
					}
					if(Tools.isEmpty(errInfo)){
						errInfo = "success";
					}
				}else{
					//缺少参数
					errInfo="codeerror";
				}
			}
		}
		obj.put("result", errInfo);
		return obj.toString();
	}
		
	/**
	 * 后台管理系统主页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/index")
	public ModelAndView toMain() throws AuthenticationException{
		ModelAndView mv = this.getModelAndView();
		/**获取Shiro管理的Session**/
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		
		if(user != null){
			Set<Role> roles = user.getRoles();
			Set<Permission> permissions = new HashSet<Permission>();
			for(Role r : roles){
				permissions.addAll(r.getPermissions());
			}
			
			/**获取用户可以查看的菜单**/
			List<Menu> menuList = new ArrayList<Menu>();
			for(Permission p : permissions){
				menuList.add(p.getMenu());
			}
			
			List<Menu> menus = new ArrayList<Menu>();
			
			/**为一级菜单添加二级菜单**/
			for(Menu m : menuList){
				if(m.getMenuType().equals("1")){
					List<Menu> subMenu = new ArrayList<Menu>();
					//查询二级菜单
					subMenu = menuService.findSubMenuById(m.getMenuId());
					if(subMenu!=null&&subMenu.size()>0){
						m.setHasMenu(Boolean.TRUE);
						m.setSubMenu(subMenu);
						menus.add(m);
					}
				}
			}
			mv.addObject("menus",menus);
		}else{
			//会话失效，返回登录界面
			mv.setViewName("admin/frame/login");
		}
		mv.setViewName("admin/frame/index");
		return mv;
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		ModelAndView mv = this.getModelAndView();
		/**Shiro管理Session**/
		Subject sub = SecurityUtils.getSubject();
		Session session = sub.getSession();
		session.removeAttribute(Constants.SESSION_USER);
		session.removeAttribute(Constants.SESSION_SECURITY_CODE);
		/**Shiro销毁登录**/
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		/**返回后台系统登录界面**/
		mv.setViewName("admin/frame/login");
		return mv;
	}


}
