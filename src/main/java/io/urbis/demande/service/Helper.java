/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.demande.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class Helper {
    
    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
     public int numeroDemande(){
   
        TypedQuery<Integer> query =  em.createNamedQuery("Demande.findMaxNumero", Integer.class);   
          
        try{
            Integer num = query.getSingleResult();
            log.infof("--MAX NUM DEMANDE: %d", num);
            if(num != null){
                return num + 1;
            }
            log.infof("aucune demande trouvée...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucune demande trouvée...");
            return 1;
        }
      
      
    }
    
}
