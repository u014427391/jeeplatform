package org.muses.jeeplatform.web.filter;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.muses.jeeplatform.core.entity.admin.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * TODO 功能还没实现
 * @Author mazq
 * @Date 2020/04/26 17:17
 * @Param
 * @return
 */
public class SysAccessControllerFilter extends AccessControlFilter{

    private String url;//被提出后，重定向的url
    private boolean isKickoutAfter = false;//
    private int maxSession = 1;

    private SessionManager sessionManager;
    private Cache<String,Deque<Serializable>> cache;

    public void setUrl(String url){
        this.url = url;
    }

    public void setKickoutAfter(boolean isKickoutAfter){
        this.isKickoutAfter = isKickoutAfter;
    }

    public void setMaxSession(int maxSession){
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager){
        this.cache = cacheManager.getCache("");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        if(!subject.isAuthenticated() && ! subject.isRemembered()){
            return true;
        }
        Session session = subject.getSession();
        User user = (User)subject.getPrincipal();
        String username = user.getUsername();
        Serializable sessionId = session.getId();

        //读取缓存
        Deque<Serializable> deque = cache.get(username);

        //new一个空队列
        if(deque == null){
            deque = new LinkedList<Serializable>();
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            //将sessionId存入队列
            deque.push(sessionId);
            //将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(isKickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
                //踢出后再更新下缓存队列
                cache.put(username, deque);
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
                //踢出后再更新下缓存队列
                cache.put(username, deque);
            }



            try {
                //获取被踢出的sessionId的session对象
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if ((Boolean)session.getAttribute("kickout")!=null&&(Boolean)session.getAttribute("kickout") == true) {
            //会话被踢出了
            try {
                //退出登录
                subject.logout();
            } catch (Exception e) { //ignore
            }
            saveRequest(servletRequest);

            Map<String, String> resultMap = new HashMap<String, String>();
            //判断是不是Ajax请求
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) servletRequest).getHeader("X-Requested-With"))) {
                resultMap.put("user_status", "300");
                resultMap.put("message", "您已经在其他地方登录，请重新登录！");
                //输出json串
                out(servletResponse, resultMap);
            }else{
                //重定向
                WebUtils.issueRedirect(servletRequest, servletResponse, url);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSON.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }
    }
}
