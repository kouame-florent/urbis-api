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
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */

@ApplicationScoped
public class AppLifecycleBean {
    
    private static final Logger LOG = Logger.getLogger("AppLifecycleBean");

    void onStart(@Observes StartupEvent ev) {               
        LOG.info("Urbis is starting...");
        //loadUsers();
        
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOG.info("The application is stopping...");
    }
    
    /*
    @Transactional
    public void loadUsers() {
        // reset and load all test users
        Utilisateur.find("username = ?1", "lisa")
                .firstResultOptional().ifPresentOrElse(u -> {LOG.infof("User {0} exists", u);},
                        () ->{Utilisateur.add("lisa", "lisa", Roles.USER.name(),StatutUtilisateur.ACTIF);
                });
        
       // Utilisateur.add("bart", "bart", Roles.AGENT_ETAT_CIVIL.name());
       
    }
    
    */
}
