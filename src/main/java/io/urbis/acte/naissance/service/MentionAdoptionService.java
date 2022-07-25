/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.urbis.acte.naissance.domain.MentionAdoption;
import io.urbis.acte.naissance.dto.MentionAdoptionDto;
import io.urbis.acte.naissance.domain.ActeNaissance;
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
public class MentionAdoptionService {
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    EntityManager em;
   
    
    public void createMention(@NotNull MentionAdoptionDto dto){
       
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
           
        var mention = new MentionAdoption();
            
        mention.updatedBy = authenticationContext.userLogin();
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;

        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();
   
        
    }
    
    public void modifierMention(@NotNull MentionAdoptionDto dto){
        
        MentionAdoption mention = MentionAdoption.findById(dto.getId());
        
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
        MentionAdoption.deleteById(mentionID);
    }
    
    public Set<MentionAdoptionDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionAdoption> mentions = MentionAdoption.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeNaissance acte){
        
        TypedQuery<MentionAdoption> query =  em.createNamedQuery("MentionAdoption.findMostRecent", MentionAdoption.class);
        query.setParameter("acteNaissance",acte);
          
        try{
            MentionAdoption mention = query.getSingleResult();

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
    
    
    public MentionAdoptionDto mapToDto(@NotNull MentionAdoption mention){
        MentionAdoptionDto dto = new MentionAdoptionDto();
        
        dto.setId(mention.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
}
