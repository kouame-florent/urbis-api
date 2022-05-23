/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.service;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author florent
 */
@RequestScoped
public class AuthenticationContext {
    
    
    //@Context SecurityContext securityCtx;
    
    private String userLogin;

    public String getUserLogin() {
        if (userLogin == null){
            return "anonymous";
        }
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

   
    
}
