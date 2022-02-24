/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.ActeNaissanceEtat;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeNaissanceEtatService {
    
       
    public ActeNaissanceEtat findByActeNaissance(ActeNaissance acteNaissance){
        PanacheQuery<ActeNaissanceEtat> pq = ActeNaissanceEtat.find("acteNaissance", acteNaissance);
        ActeNaissanceEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeNaissanceEtat not found");
        }
        
        return etat;
    }
    
   public void modifier(@NotBlank String id,@NotNull ActeNaissanceEtatDto dto){
   
   }
    
    
}
