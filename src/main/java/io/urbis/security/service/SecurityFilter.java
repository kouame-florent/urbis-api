/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.service;

import io.quarkus.oidc.OidcConfigurationMetadata;
import io.quarkus.security.identity.SecurityIdentity;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter{
    
    @Inject
    Logger log;
    
    @Inject
    OidcConfigurationMetadata configMetadata;   
    
    @Inject
    JsonWebToken jwt;    
    
    @Inject
    SecurityIdentity identity;
    
    @Inject
    AuthenticationContext authCtx;
    
   
      
    @Override
    public void filter(ContainerRequestContext reqCtx) throws IOException {
       // KeycloakPrincipal principal = (KeycloakPrincipal)securityCtx.getUserPrincipal();
       
       // log.infof("--- PRINCIPAL FROM FILTER : %s",authCtx.getSecurityCtx().getUserPrincipal().getName());
       // authCtx.setUserLogin(securityCtx.getUserPrincipal().getName());
       log.infof("--- USER IS ANONYME : %s",identity.isAnonymous());
       log.infof("--- PRINCIPAL NAME : %s",identity.getPrincipal().getName());
       
       authCtx.setUserLogin(identity.getPrincipal().getName());
       
       
    }

}
