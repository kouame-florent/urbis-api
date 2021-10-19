/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */

@ApplicationScoped
public class AppLifecycleBean {
    
    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {               
        LOGGER.info("Urbis is starting...");
        
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The application is stopping...");
    }
    
    @Transactional
    public void loadUsers() {
        // reset and load all test users
       // Utilisateur.add("lisa", "lisa", Roles.CHEF_ETAT_CIVIL.name());
       // Utilisateur.add("bart", "bart", Roles.AGENT_ETAT_CIVIL.name());
       
    }
    
    
}
