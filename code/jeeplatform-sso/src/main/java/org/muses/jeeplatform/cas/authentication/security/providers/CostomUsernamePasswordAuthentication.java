package org.muses.jeeplatform.cas.authentication.security.providers;

import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

/**
 * <pre>
 *  CAS单点登录验证
 * </pre>
 *
 * @author nicky.ma
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年05月19日  修改内容:
 * </pre>
 */
public class CostomUsernamePasswordAuthentication extends AbstractUsernamePasswordAuthenticationHandler {


    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential usernamePasswordCredential) throws GeneralSecurityException, PreventedException {
        if (usernamePasswordCredential == null) {
            throw new FailedLoginException("No user can be accepted because none is defined");
        }
        return doAuthentication(usernamePasswordCredential);
    }

    /**
     * 用户密码验证
     * @param credential
     * @return
     */
    private HandlerResult doAuthentication(UsernamePasswordCredential credential) {
        String username = credential.getUsername();
        String password = credential.getPassword();

        String fullUserName = username;
        return createHandlerResult(credential , this.principalFactory.createPrincipal(username), null);
    }
}
