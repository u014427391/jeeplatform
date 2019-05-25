package org.muses.jeeplatform.core;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年05月25日  修改内容:
 * </pre>
 */
public class CASConsts {

    /* CAS单点登录配置 */
    //客户端URL PREFIX
    public static final String CLIENT_URL_PREFIX = "http://localhost:8081/jeeplatform";
    //客户端登录地址
    public static final String CLIENT_LOGIN_URL = CLIENT_URL_PREFIX +"/login";
    //客户端登出地址
    public static final String CLIENT_LOGOUT_URL = CLIENT_URL_PREFIX + "/logout";
    //CAS服务端URL PREFIX
    public static final String CAS_SERVER_URL_PREFIX = "http://localhost:8080";
    //Cas过滤器UrlPattern
    public static final String CAS_FILTER_URL_PATTERN = "/cas";
    //CAS客户端单点登录
    public static final String CAS_CLIENT_LOGIN_URL = CLIENT_LOGIN_URL + "?service="+CAS_SERVER_URL_PREFIX+CAS_FILTER_URL_PATTERN;
    //CAS客户端单点登出
    public static final String CAS_CLIENT_LOGOUT_URL = CLIENT_LOGOUT_URL + "?service="+CAS_SERVER_URL_PREFIX+CAS_FILTER_URL_PATTERN;
    //登录成功地址
    public static final String LOGIN_SUCCESS_URL = "/index";

}
