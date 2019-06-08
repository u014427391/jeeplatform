package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.ResultVO;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.muses.jeeplatform.core.entity.admin.User;
import org.muses.jeeplatform.service.MenuService;
import org.muses.jeeplatform.service.UserService;
import org.muses.jeeplatform.util.ListSortUtils;
import org.muses.jeeplatform.util.MenuTreeUtils;
import org.muses.jeeplatform.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @description 登录操作的控制类，使用Shiro框架，做好了登录的权限安全认证，
 * getRemortIP()方法获取用户登录时的ip并保存到数据库，使用Redis实现缓存
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
    private void getRemortIP(String username)  {
        HttpServletRequest request = this.getRequest();
        Map<String,String> map = new HashMap<String,String>();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
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
    @ApiOperation(value = "跳转到登录页面",notes = "")
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
    //@LogController
    @ApiOperation(value = "登录验证接口",notes = "根据用户名、密码、验证码进行验证")
    public ResultVO loginCheck(HttpServletRequest request)throws AuthenticationException{
        String errInfo = "";//错误信息
        String logindata[] = request.getParameter("LOGINDATA").split(",");
        if(logindata != null && logindata.length == 3){
            //获取Shiro管理的Session
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String codeSession = (String)session.getAttribute(CommonConsts.SESSION_SECURITY_CODE);
            String code = logindata[2];
            /**检测页面验证码是否为空，调用工具类检测**/
            if(Tools.isEmpty(code)){
                errInfo = "nullcode";
            }else{
                String username = logindata[0];
                String password = logindata[1];
                if(Tools.isNotEmpty(codeSession)/*&&code.equalsIgnoreCase(codeSession)*/){
                    //Shiro框架SHA加密
                    String passwordsha = new SimpleHash("SHA-1",username,password).toString();
                    log.info("SHA加密密码：{}",passwordsha);
                    //System.out.println(passwordsha);
                    //检测用户名和密码是否正确
                    User user = userService.doLoginCheck(username,passwordsha);
                    if(user != null){
                        if(Boolean.TRUE.equals(user.getLocked())){
                            errInfo = "locked";
                        }else{
                            //Shiro添加会话
                            session.setAttribute("username", username);
                            session.setAttribute(CommonConsts.SESSION_USER, user);
                            //删除验证码Session
                            session.removeAttribute(CommonConsts.SESSION_SECURITY_CODE);
                            //保存登录IP
                            this.getRemortIP(username);
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
        return ResultVO.successful("result",null);
    }

    /**
     * 后台管理系统主页
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/index")
    public ModelAndView toMain() throws AuthenticationException{
        ModelAndView mv = this.getModelAndView();
        /**获取Shiro管理的Session**/
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute(CommonConsts.SESSION_USER);

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

            menuList = (List<Menu>)ListSortUtils.sortByDesc(menuList, "menuOrder");

//			List<Menu> menus = new ArrayList<Menu>();
//			/**为一级菜单添加二级菜单**/
//			for(Menu m : menuList){
//				System.out.println(m.getMenuName());
//				if(m.getParentId() == 0){
//					List<Menu> subMenu = new ArrayList<Menu>();
//					//查询二级菜单
//					subMenu = menuService.findSubMenuById(m.getMenuId());
//					if(subMenu!=null&&subMenu.size()>0){
//						m.setHasSubMenu(true);
//						m.setSubMenu(subMenu);
//						menus.add(m);
//					}
//				}
//			}
            MenuTreeUtils treeUtil = new MenuTreeUtils();
            List<Menu> treemenus= treeUtil.menuList(menuList);

            String json = JSON.toJSONString(treemenus);

//			json = json.replaceAll("menuId","id").replaceAll("parentId","pId").
//					replaceAll("menuName","name").replaceAll("hasSubMenu","checked");

            mv.addObject("menus",json);

        }else{
            //会话失效，返回登录界面
            mv.setViewName("admin/frame/login");
        }
        mv.setViewName("admin/frame/index");
        return mv;
    }

    @RequestMapping(value = "/tip")
    public String sysTip(){
        return "admin/common/sys_tip";
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
        session.removeAttribute(CommonConsts.SESSION_USER);
        session.removeAttribute(CommonConsts.SESSION_SECURITY_CODE);
        /**Shiro销毁登录**/
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        /**返回后台系统登录界面**/
        mv.setViewName("admin/frame/login");
        return mv;
    }

    @RequestMapping("/403")
    public ModelAndView to403PAge(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("admin/frame/403");
        return mv;
    }

}
