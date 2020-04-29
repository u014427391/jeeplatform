package org.muses.jeeplatform.authentication.cas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.jasig.cas.client.authentication.AuthenticationRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/27 13:37  修改内容:
 * </pre>
 */
public class CustomAuthticationRedirectStrategy implements AuthenticationRedirectStrategy {

    @Override
    public void redirect(HttpServletRequest request, HttpServletResponse response, String potentialRedirectUrl) throws IOException {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter out = response.getWriter();
//        out.write("401");
        //response重定向
        response.sendRedirect(potentialRedirectUrl);
    }
}
