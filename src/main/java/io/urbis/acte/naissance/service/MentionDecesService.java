/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.quarkus.panache.common.Sort;
import io.urbis.acte.naissance.domain.MentionDeces;
import io.urbis.acte.naissance.dto.MentionDecesDto;
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
public class MentionDecesService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
   
    
    public void createMention(@NotNull MentionDecesDto dto){
        
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
       
        MentionDeces mention = MentionDeces.findById(dto.getId());
       
        mention = new MentionDeces();

        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;

        mention.decision = dto.getDecision();
        mention.date = dto.getDate();
        mention.lieu = dto.getLieu();
        mention.localite = dto.getLocalite();
        mention.dateDressage = dto.getDateDressage();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();
      
    }
    
    public void modifierMention(@NotNull MentionDecesDto dto){
            
       
        MentionDeces mention = MentionDeces.findById(dto.getId());
        if(mention == null){ 
            //creer les mentions rajoutées à la modification de l'acte
            createMention(dto);
        }else{
            mention.decision = dto.getDecision();
            mention.date = dto.getDate();
            mention.lieu = dto.getLieu();
            mention.localite = dto.getLocalite();
            mention.dateDressage = dto.getDateDressage();
            mention.updatedBy = authenticationContext.userLogin();
        }
             
        

    }
     
    public void deleteMention(String mentionID){
        MentionDeces.deleteById(mentionID);
    }
    
    public Set<MentionDecesDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionDeces> mentions = MentionDeces.list("acteNaissance",Sort.descending("dateDressage"), acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    
    public String mentionRecenteTexte(ActeNaissance acte){
        
        TypedQuery<MentionDeces> query =  em.createNamedQuery("MentionDeces.findMostRecent", MentionDeces.class);
        query.setParameter("acteNaissance",acte);
          
        try{
            MentionDeces mention = query.getSingleResult();

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
    
    public MentionDecesDto mapToDto(@NotNull MentionDeces mention){
        MentionDecesDto dto = new MentionDecesDto();
        
        dto.setId(mention.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
   
}
