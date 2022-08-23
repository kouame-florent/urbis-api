/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.service;

import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.deces.domain.MentionRectificationDeces;
import io.urbis.acte.deces.dto.MentionRectificationDecesDto;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.security.service.AuthenticationContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class MentionRectificationDecesService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em; 
    
    public void createMention(@NotNull MentionRectificationDecesDto dto){
        
        ActeDeces acte = ActeDeces.findById(dto.getActeMariageID());
        if(acte == null){
            throw new EntityNotFoundException("ActeDeces not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
       
        MentionRectificationDeces mention = new MentionRectificationDeces();

        mention.acteDeces = acte;
        mention.officierEtatCivil = officier;

        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();
        
    }
    
    public void modifierMention(@NotNull MentionRectificationDecesDto dto){
         
        MentionRectificationDeces mention = MentionRectificationDeces.findById(dto.getId());
        if(mention == null){
            //creer les mentions rajoutées à la modification de l'acte
            createMention(dto);
        }else{
                 
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();
            mention.updatedBy = authenticationContext.userLogin();
        
        }
       
        
    }
     
    public void deleteMention(String mentionID){
        MentionRectificationDeces.deleteById(mentionID);
    }
    
    public Set<MentionRectificationDecesDto> findByActeMariage(@NotBlank String acteDecesID){
        ActeDeces acte = ActeDeces.findById(acteDecesID);
        List<MentionRectificationDeces> mentions = MentionRectificationDeces.list("acteDeces", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeDeces acte){
        
        TypedQuery<MentionRectificationDeces> query =  em.createNamedQuery("MentionRectificationDeces.findMostRecent",
                MentionRectificationDeces.class);
        query.setParameter("acteDeces",acte);
          
        try{
            MentionRectificationDeces mention = query.getSingleResult();

            if(mention != null){
                return mention.decision;
            }
            log.infof("aucune mention trouvée...");
            return "";
        }catch(NoResultException ex){
            log.infof("aucune mention trouvée...");
            return "";
        }
        
    }
    
    public MentionRectificationDecesDto  mapToDto(@NotNull MentionRectificationDeces mention){
        MentionRectificationDecesDto  dto = new MentionRectificationDecesDto ();
        
        dto.setId(mention.id);
        dto.setActeMariageID(mention.acteDeces.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
}
