/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.common.service;

import io.urbis.acte.common.domain.Acte;
import io.urbis.registre.domain.TypeRegistre;
import java.time.LocalDate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class ActeService {
    
    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    public Acte findByDemandeCreteria(int numeroActe,TypeRegistre typeRegistre, LocalDate dateOuvertureRegistre){
        TypedQuery<Acte> query =  em.createNamedQuery("Acte.findByDemandeCreteria", Acte.class);
        query.setParameter("numero",numeroActe);
        query.setParameter("dateOuvertureRegistre", dateOuvertureRegistre);
        query.setParameter("typeRegistre", typeRegistre);
        
        Acte acte = null;
        
        try{
             acte = query.getSingleResult();
        }catch(NoResultException | NonUniqueResultException ex){
            
            log.infof("cannot find Acte: %s", numeroActe);
        }
       
        
        return acte;
    }
}
