package org.muses.jeeplatform.cas.authentication.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.exceptions.AccountDisabledException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/26 11:03  修改内容:
 * </pre>
 */
public class ShiroAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    public ShiroAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(credential.getUsername(), credential.getPassword());

            if (credential instanceof RememberMeUsernamePasswordCredential) {
                token.setRememberMe(RememberMeUsernamePasswordCredential.class.cast(credential).isRememberMe());
            }

            Subject subject = getCurrentExecutingSubject();
            subject.login(token);

            //获取Shiro管理的Session
            //Session session = getShiroSession(subject);

            final String username = subject.getPrincipal().toString();
            return createHandlerResult(credential, this.principalFactory.createPrincipal(username));
        } catch (final UnknownAccountException uae) {
            throw new AccountNotFoundException(uae.getMessage());
        } catch (final IncorrectCredentialsException ice) {
            throw new FailedLoginException(ice.getMessage());
        } catch (final LockedAccountException | ExcessiveAttemptsException lae) {
            throw new AccountLockedException(lae.getMessage());
        } catch (final ExpiredCredentialsException eae) {
            throw new CredentialExpiredException(eae.getMessage());
        } catch (final DisabledAccountException eae) {
            throw new AccountDisabledException(eae.getMessage());
        } catch (final AuthenticationException e) {
            throw new FailedLoginException(e.getMessage());
        }
    }

    protected Subject getCurrentExecutingSubject(){
        return SecurityUtils.getSubject();
    }

    protected Session getShiroSession(Subject subject){
        return subject.getSession();
    }


    @Override
    public boolean supports(Credential credential) {
        return false;
    }
}
