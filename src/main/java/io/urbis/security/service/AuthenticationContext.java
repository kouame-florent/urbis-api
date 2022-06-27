/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.service;

import io.quarkus.security.identity.SecurityIdentity;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author florent
 */
@RequestScoped
public class AuthenticationContext implements Serializable{
    
    @Inject
    SecurityIdentity securityIdentity;
    
    public String userLogin(){
        
        if(securityIdentity != null){
            return securityIdentity.getPrincipal().getName();
        }
        
        return "anonymous";
    }
    
}
