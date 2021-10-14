/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.urbis.naissance.domain.TypeMention;
import io.urbis.naissance.domain.TypeMentionVariante;
import java.util.List;
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
        LOGGER.info("Loading 'Type mention'...");
        loadTypeMention();
        LOGGER.info("Loading 'Type mention variante'...");
        loadTypeMentionVariante();
        
        
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
    
    @Transactional
    public void loadTypeMention(){
        
      var types =  List.of(
              new TypeMention("01", "Reconnaissance"),
              new TypeMention("02", "Adoption"),
              new TypeMention("03", "Légitimation"),
              new TypeMention("04", "Mariage"),
              new TypeMention("05", "Décès"),
              new TypeMention("06", "Dissolution de mariage"),
              new TypeMention("07", "Rectification"));
      
      var count = TypeMention.count();
      if(count == 0){
          TypeMention.persist(types);
      }
    }
    
    @Transactional
    public void loadTypeMentionVariante(){
       TypeMention reconnaissance = TypeMention.findById("01");
       TypeMention adoption = TypeMention.findById("02");
       TypeMention legitimation = TypeMention.findById("03");
       TypeMention mariage = TypeMention.findById("04");
       TypeMention deces = TypeMention.findById("05");
       TypeMention dissolution = TypeMention.findById("06");
       TypeMention rectification = TypeMention.findById("07");
       
       
       var variantes = List.of(
               new TypeMentionVariante("01.1", "Reconnaissance enfant naturel", reconnaissance),
               new TypeMentionVariante("01.2", "Reconnaissance enfant adulterin", reconnaissance),
               new TypeMentionVariante("01.3", "Consentement à reconnaissance", reconnaissance),
               new TypeMentionVariante("02.1", "Adoption simple", adoption),
               new TypeMentionVariante("02.2", "Adoption plenière", adoption),
               new TypeMentionVariante("03.1", "Légitimation", legitimation),
               new TypeMentionVariante("04.1", "Mariage", mariage),
               new TypeMentionVariante("05.1", "Décès", deces),
               new TypeMentionVariante("06.1", "Séparation de corps", dissolution),
               new TypeMentionVariante("06.2", "Divorce", dissolution),
               new TypeMentionVariante("06.3", "Dissolution de mariage/Décès", dissolution),
               new TypeMentionVariante("07.1", "Rectification administrative", rectification),
               new TypeMentionVariante("07.2", "Rectification judiciaire", rectification));
       
       var count = TypeMentionVariante.count();
       if(count == 0){
           TypeMentionVariante.persist(variantes);
       }
    }
    
    
}
