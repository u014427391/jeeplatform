package org.muses.jeeplatform.core;

/**
 * <pre>
 *  CAS配置环境类
 * </pre>
 *
 * @author nicky.ma
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年05月25日  修改内容:
 * </pre>
 */
public class CASConsts {

    /* CAS单点登录配置 */
    //Cas server地址
    public static final String CAS_SERVER_URL_PREFIX = "http://localhost:8080/cas";
    //Cas单点登录地址
    public static final String CAS_LOGIN_URL = CAS_SERVER_URL_PREFIX +"/login";
    //CAS单点登出地址
    public static final String CAS_LOGOUT_URL = CAS_SERVER_URL_PREFIX + "/logout";
    //对外提供的服务地址
    public static final String SERVER_URL_PREFIX = "http://localhost:8081";
    //Cas过滤器的urlPattern
    public static final String CAS_FILTER_URL_PATTERN = "/jeeplatform";
    //CAS客户端单点登录跳转地址
    public static final String CAS_CLIENT_LOGIN_URL = CAS_LOGIN_URL + "?service="+SERVER_URL_PREFIX+CAS_FILTER_URL_PATTERN;
    //CAS客户端单点登出
    public static final String CAS_CLIENT_LOGOUT_URL = CAS_LOGOUT_URL + "?service="+SERVER_URL_PREFIX+CAS_FILTER_URL_PATTERN;
    //登录成功地址
    public static final String LOGIN_SUCCESS_URL = "/index";
    //无权访问页面403
    public static final String LOGIN_UNAUTHORIZED_URL = "/403";

}
