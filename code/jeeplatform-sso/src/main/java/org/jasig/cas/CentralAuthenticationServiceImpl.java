//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jasig.cas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import org.jasig.cas.authentication.*;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.logout.LogoutManager;
import org.jasig.cas.logout.LogoutRequest;
import org.jasig.cas.services.*;
import org.jasig.cas.support.events.*;
import org.jasig.cas.ticket.*;
import org.jasig.cas.ticket.proxy.ProxyGrantingTicket;
import org.jasig.cas.ticket.proxy.ProxyGrantingTicketFactory;
import org.jasig.cas.ticket.proxy.ProxyTicket;
import org.jasig.cas.ticket.proxy.ProxyTicketFactory;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.validation.Assertion;
import org.jasig.cas.validation.ImmutableAssertion;
import org.jasig.inspektr.audit.annotation.Audit;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("centralAuthenticationService")
@Transactional(
        readOnly = false,
        transactionManager = "ticketTransactionManager"
)
public class CentralAuthenticationServiceImpl extends AbstractCentralAuthenticationService {
    private static final long serialVersionUID = -8943828074939533986L;

    public CentralAuthenticationServiceImpl() {
    }

    public CentralAuthenticationServiceImpl(TicketRegistry ticketRegistry, TicketFactory ticketFactory, ServicesManager servicesManager, LogoutManager logoutManager) {
        super(ticketRegistry, ticketFactory, servicesManager, logoutManager);
    }

    @Audit(
            action = "TICKET_GRANTING_TICKET_DESTROYED",
            actionResolverName = "DESTROY_TICKET_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "DESTROY_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "DESTROY_TICKET_GRANTING_TICKET_TIMER"
    )
    @Metered(
            name = "DESTROY_TICKET_GRANTING_TICKET_METER"
    )
    @Counted(
            name = "DESTROY_TICKET_GRANTING_TICKET_COUNTER",
            monotonic = true
    )
    public List<LogoutRequest> destroyTicketGrantingTicket(@NotNull String ticketGrantingTicketId) {
        try {
            this.logger.debug("Removing ticket [{}] from registry...", ticketGrantingTicketId);
            TicketGrantingTicket ticket = (TicketGrantingTicket)this.getTicket(ticketGrantingTicketId, TicketGrantingTicket.class);
            this.logger.debug("Ticket found. Processing logout requests and then deleting the ticket...");
            List<LogoutRequest> logoutRequests = this.logoutManager.performLogout(ticket);
            this.ticketRegistry.deleteTicket(ticketGrantingTicketId);
            this.doPublishEvent(new CasTicketGrantingTicketDestroyedEvent(this, ticket));
            return logoutRequests;
        } catch (InvalidTicketException var4) {
            this.logger.debug("TicketGrantingTicket [{}] cannot be found in the ticket registry.", ticketGrantingTicketId);
            return Collections.emptyList();
        }
    }

    @Audit(
            action = "SERVICE_TICKET",
            actionResolverName = "GRANT_SERVICE_TICKET_RESOLVER",
            resourceResolverName = "GRANT_SERVICE_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "GRANT_SERVICE_TICKET_TIMER"
    )
    @Metered(
            name = "GRANT_SERVICE_TICKET_METER"
    )
    @Counted(
            name = "GRANT_SERVICE_TICKET_COUNTER",
            monotonic = true
    )
    public ServiceTicket grantServiceTicket(String ticketGrantingTicketId, Service service, AuthenticationContext context) throws AuthenticationException, AbstractTicketException {
        this.logger.debug("Attempting to get ticket id {} to create service ticket", ticketGrantingTicketId);
        TicketGrantingTicket ticketGrantingTicket = (TicketGrantingTicket)this.getTicket(ticketGrantingTicketId, TicketGrantingTicket.class);
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        this.verifyRegisteredServiceProperties(registeredService, service);
        this.evaluatePossibilityOfMixedPrincipals(context, ticketGrantingTicket);
        if(ticketGrantingTicket.getCountOfUses() > 0 && !registeredService.getAccessStrategy().isServiceAccessAllowedForSso()) {
            this.logger.warn("Service [{}] is not allowed to use SSO.", service.getId());
            throw new UnauthorizedSsoServiceException();
        } else {
            this.evaluateProxiedServiceIfNeeded(service, ticketGrantingTicket, registeredService);
            this.logger.debug("Checking for authentication policy satisfaction...");
            this.getAuthenticationSatisfiedByPolicy(ticketGrantingTicket.getRoot(), new ServiceContext(service, registeredService));
            List<Authentication> authentications = ticketGrantingTicket.getChainedAuthentications();
            Principal principal = ((Authentication)authentications.get(authentications.size() - 1)).getPrincipal();
            this.logger.debug("Located principal {} for service ticket creation", principal);
            RegisteredServiceAttributeReleasePolicy releasePolicy = registeredService.getAttributeReleasePolicy();
            Object principalAttrs;
            if(releasePolicy != null) {
                principalAttrs = releasePolicy.getAttributes(principal);
            } else {
                principalAttrs = new HashMap();
            }

            if(!registeredService.getAccessStrategy().doPrincipalAttributesAllowServiceAccess(principal.getId(), (Map)principalAttrs)) {
                this.logger.warn("Cannot grant service ticket because Service [{}] is not authorized for use by [{}].", service.getId(), principal);
                throw new UnauthorizedServiceForPrincipalException();
            } else {
                ServiceTicketFactory factory = (ServiceTicketFactory)this.ticketFactory.get(ServiceTicket.class);
                ServiceTicket serviceTicket = (ServiceTicket)factory.create(ticketGrantingTicket, service, context != null && context.isCredentialProvided());
                this.logger.info("Granted ticket [{}] for service [{}] and principal [{}]", new Object[]{serviceTicket.getId(), service.getId(), principal.getId()});

                //添加更新tgt
                this.ticketRegistry.deleteTicket(ticketGrantingTicketId);
                this.ticketRegistry.addTicket(ticketGrantingTicket);

                this.logger.info("添加更新TGT：[{}]",ticketGrantingTicket);

                this.ticketRegistry.addTicket(serviceTicket);
                this.logger.debug("Added service ticket {} to ticket registry", serviceTicket.getId());
                this.doPublishEvent(new CasServiceTicketGrantedEvent(this, ticketGrantingTicket, serviceTicket));
                return serviceTicket;
            }
        }
    }

    private Authentication evaluatePossibilityOfMixedPrincipals(AuthenticationContext context, TicketGrantingTicket ticketGrantingTicket) throws MixedPrincipalException {
        Authentication currentAuthentication = null;
        if(context != null) {
            currentAuthentication = context.getAuthentication();
            if(currentAuthentication != null) {
                Authentication original = ticketGrantingTicket.getAuthentication();
                if(!currentAuthentication.getPrincipal().equals(original.getPrincipal())) {
                    this.logger.debug("Principal associated with current authentication {} does not match  the principal {} associated with the original authentication", currentAuthentication.getPrincipal(), original.getPrincipal());
                    throw new MixedPrincipalException(currentAuthentication, currentAuthentication.getPrincipal(), original.getPrincipal());
                }

                ticketGrantingTicket.getSupplementalAuthentications().clear();
                ticketGrantingTicket.getSupplementalAuthentications().add(currentAuthentication);
                this.logger.debug("Added authentication to the collection of supplemental authentications");
            }
        }

        return currentAuthentication;
    }

    @Audit(
            action = "PROXY_TICKET",
            actionResolverName = "GRANT_PROXY_TICKET_RESOLVER",
            resourceResolverName = "GRANT_PROXY_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "GRANT_PROXY_TICKET_TIMER"
    )
    @Metered(
            name = "GRANT_PROXY_TICKET_METER"
    )
    @Counted(
            name = "GRANT_PROXY_TICKET_COUNTER",
            monotonic = true
    )
    public ProxyTicket grantProxyTicket(String proxyGrantingTicket, Service service) throws AbstractTicketException {
        ProxyGrantingTicket proxyGrantingTicketObject = (ProxyGrantingTicket)this.getTicket(proxyGrantingTicket, ProxyGrantingTicket.class);
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        this.verifyRegisteredServiceProperties(registeredService, service);
        if(!registeredService.getAccessStrategy().isServiceAccessAllowedForSso()) {
            this.logger.warn("Service [{}] is not allowed to use SSO.", service.getId());
            throw new UnauthorizedSsoServiceException();
        } else {
            this.evaluateProxiedServiceIfNeeded(service, proxyGrantingTicketObject, registeredService);
            this.getAuthenticationSatisfiedByPolicy(proxyGrantingTicketObject.getRoot(), new ServiceContext(service, registeredService));
            List<Authentication> authentications = proxyGrantingTicketObject.getChainedAuthentications();
            Principal principal = ((Authentication)authentications.get(authentications.size() - 1)).getPrincipal();
            RegisteredServiceAttributeReleasePolicy releasePolicy = registeredService.getAttributeReleasePolicy();
            Object principalAttrs;
            if(releasePolicy != null) {
                principalAttrs = releasePolicy.getAttributes(principal);
            } else {
                principalAttrs = new HashMap();
            }

            if(!registeredService.getAccessStrategy().doPrincipalAttributesAllowServiceAccess(principal.getId(), (Map)principalAttrs)) {
                this.logger.warn("Cannot grant proxy ticket because Service [{}] is not authorized for use by [{}].", service.getId(), principal);
                throw new UnauthorizedServiceForPrincipalException();
            } else {
                ProxyTicketFactory factory = (ProxyTicketFactory)this.ticketFactory.get(ProxyTicket.class);
                ProxyTicket proxyTicket = (ProxyTicket)factory.create(proxyGrantingTicketObject, service);
                this.ticketRegistry.addTicket(proxyTicket);
                this.logger.info("Granted ticket [{}] for service [{}] for user [{}]", new Object[]{proxyTicket.getId(), service.getId(), principal.getId()});
                this.doPublishEvent(new CasProxyTicketGrantedEvent(this, proxyGrantingTicketObject, proxyTicket));
                return proxyTicket;
            }
        }
    }

    @Audit(
            action = "PROXY_GRANTING_TICKET",
            actionResolverName = "CREATE_PROXY_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "CREATE_PROXY_GRANTING_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "CREATE_PROXY_GRANTING_TICKET_TIMER"
    )
    @Metered(
            name = "CREATE_PROXY_GRANTING_TICKET_METER"
    )
    @Counted(
            name = "CREATE_PROXY_GRANTING_TICKET_COUNTER",
            monotonic = true
    )
    public ProxyGrantingTicket createProxyGrantingTicket(String serviceTicketId, AuthenticationContext context) throws AuthenticationException, AbstractTicketException {
        ServiceTicket serviceTicket = (ServiceTicket)this.ticketRegistry.getTicket(serviceTicketId, ServiceTicket.class);
        if(serviceTicket != null && !serviceTicket.isExpired()) {
            RegisteredService registeredService = this.servicesManager.findServiceBy(serviceTicket.getService());
            this.verifyRegisteredServiceProperties(registeredService, serviceTicket.getService());
            if(!registeredService.getProxyPolicy().isAllowedToProxy()) {
                this.logger.warn("ServiceManagement: Service [{}] attempted to proxy, but is not allowed.", serviceTicket.getService().getId());
                throw new UnauthorizedProxyingException();
            } else {
                Authentication authentication = context.getAuthentication();
                ProxyGrantingTicketFactory factory = (ProxyGrantingTicketFactory)this.ticketFactory.get(ProxyGrantingTicket.class);
                ProxyGrantingTicket proxyGrantingTicket = factory.create(serviceTicket, authentication);
                this.logger.debug("Generated proxy granting ticket [{}] based off of [{}]", proxyGrantingTicket, serviceTicketId);
                this.ticketRegistry.addTicket(proxyGrantingTicket);
                this.doPublishEvent(new CasProxyGrantingTicketCreatedEvent(this, proxyGrantingTicket));
                return proxyGrantingTicket;
            }
        } else {
            this.logger.debug("ServiceTicket [{}] has expired or cannot be found in the ticket registry", serviceTicketId);
            throw new InvalidTicketException(serviceTicketId);
        }
    }

    @Audit(
            action = "SERVICE_TICKET_VALIDATE",
            actionResolverName = "VALIDATE_SERVICE_TICKET_RESOLVER",
            resourceResolverName = "VALIDATE_SERVICE_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "VALIDATE_SERVICE_TICKET_TIMER"
    )
    @Metered(
            name = "VALIDATE_SERVICE_TICKET_METER"
    )
    @Counted(
            name = "VALIDATE_SERVICE_TICKET_COUNTER",
            monotonic = true
    )
    public Assertion validateServiceTicket(String serviceTicketId, Service service) throws AbstractTicketException {
        RegisteredService registeredService = this.servicesManager.findServiceBy(service);
        this.verifyRegisteredServiceProperties(registeredService, service);
        ServiceTicket serviceTicket = (ServiceTicket)this.ticketRegistry.getTicket(serviceTicketId, ServiceTicket.class);
        if(serviceTicket == null) {
            this.logger.info("Service ticket [{}] does not exist.", serviceTicketId);
            throw new InvalidTicketException(serviceTicketId);
        } else {
            ImmutableAssertion var15;
            try {
                synchronized(serviceTicket) {
                    if(serviceTicket.isExpired()) {
                        this.logger.info("ServiceTicket [{}] has expired.", serviceTicketId);
                        throw new InvalidTicketException(serviceTicketId);
                    }

                    if(!serviceTicket.isValidFor(service)) {
                        this.logger.error("Service ticket [{}] with service [{}] does not match supplied service [{}]", new Object[]{serviceTicketId, serviceTicket.getService().getId(), service});
                        throw new UnrecognizableServiceForServiceTicketValidationException(serviceTicket.getService());
                    }
                }

                TicketGrantingTicket root = serviceTicket.getGrantingTicket().getRoot();
                Authentication authentication = this.getAuthenticationSatisfiedByPolicy(root, new ServiceContext(serviceTicket.getService(), registeredService));
                Principal principal = authentication.getPrincipal();
                RegisteredServiceAttributeReleasePolicy attributePolicy = registeredService.getAttributeReleasePolicy();
                this.logger.debug("Attribute policy [{}] is associated with service [{}]", attributePolicy, registeredService);
                Map<String, Object> attributesToRelease = attributePolicy != null?attributePolicy.getAttributes(principal):Collections.EMPTY_MAP;
                String principalId = registeredService.getUsernameAttributeProvider().resolveUsername(principal, service);
                Principal modifiedPrincipal = this.principalFactory.createPrincipal(principalId, attributesToRelease);
                AuthenticationBuilder builder = DefaultAuthenticationBuilder.newInstance(authentication);
                builder.setPrincipal(modifiedPrincipal);
                Assertion assertion = new ImmutableAssertion(builder.build(), serviceTicket.getGrantingTicket().getChainedAuthentications(), serviceTicket.getService(), serviceTicket.isFromNewLogin());
                this.doPublishEvent(new CasServiceTicketValidatedEvent(this, serviceTicket, assertion));
                var15 = (ImmutableAssertion)assertion;
            } finally {
                if(serviceTicket.isExpired()) {
                    this.ticketRegistry.deleteTicket(serviceTicketId);
                }

            }

            return var15;
        }
    }

    @Audit(
            action = "TICKET_GRANTING_TICKET",
            actionResolverName = "CREATE_TICKET_GRANTING_TICKET_RESOLVER",
            resourceResolverName = "CREATE_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER"
    )
    @Timed(
            name = "CREATE_TICKET_GRANTING_TICKET_TIMER"
    )
    @Metered(
            name = "CREATE_TICKET_GRANTING_TICKET_METER"
    )
    @Counted(
            name = "CREATE_TICKET_GRANTING_TICKET_COUNTER",
            monotonic = true
    )
    public TicketGrantingTicket createTicketGrantingTicket(AuthenticationContext context) throws AuthenticationException, AbstractTicketException {
        Authentication authentication = context.getAuthentication();
        TicketGrantingTicketFactory factory = (TicketGrantingTicketFactory)this.ticketFactory.get(TicketGrantingTicket.class);
        TicketGrantingTicket ticketGrantingTicket = factory.create(authentication);
        this.ticketRegistry.addTicket(ticketGrantingTicket);
        this.doPublishEvent(new CasTicketGrantingTicketCreatedEvent(this, ticketGrantingTicket));
        return ticketGrantingTicket;
    }
}