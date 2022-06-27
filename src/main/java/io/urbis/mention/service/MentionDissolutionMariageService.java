/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.quarkus.panache.common.Sort;
import io.urbis.mention.domain.MentionDissolutionMariage;
import io.urbis.mention.dto.MentionDissolutionMariageDto;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class MentionDissolutionMariageService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    public void createMention(@NotNull MentionDissolutionMariageDto dto){
       
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
        
        MentionDissolutionMariage mention = new MentionDissolutionMariage();
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;
        
        mention.decision = dto.getDecision();
        mention.dateJugement = dto.getDateJugement();
        mention.dateDressage = dto.getDateDressage();
        mention.updatedBy = authenticationContext.userLogin();
        
        mention.persist();
    }
    
    public void modifierMention(@NotNull MentionDissolutionMariageDto dto){
        
        MentionDissolutionMariage mention = MentionDissolutionMariage.findById(dto.getId());
        if(mention == null){ 
            //creer les mentions rajoutées à la modification de l'acte
            createMention(dto);
        }else{
            mention.decision = dto.getDecision();
            mention.dateJugement = dto.getDateJugement();
            mention.dateDressage = dto.getDateDressage();
            mention.updatedBy = authenticationContext.userLogin();
        }
    
       
    }
     
    public void deleteMention(String mentionID){
        MentionDissolutionMariage.deleteById(mentionID);
    }
    
    public Set<MentionDissolutionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionDissolutionMariage> mentions = 
                MentionDissolutionMariage.list("acteNaissance",Sort.descending("dateDressage"), acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeNaissance acte){
        
        TypedQuery<MentionDissolutionMariage> query =  em.createNamedQuery("MentionDissolutionMariage.findMostRecent",
                MentionDissolutionMariage.class);
        query.setParameter("acteNaissance",acte);
          
        try{
            MentionDissolutionMariage mention = query.getSingleResult();

            if(mention != null){
                return mention.decision;
            }
            log.infof("aucun registre trouvé...");
            return "";
        }catch(NoResultException ex){
            log.infof("aucun registre trouvé...");
            return "";
        }
        
    }
    
    public MentionDissolutionMariageDto mapToDto(@NotNull MentionDissolutionMariage mention){
        MentionDissolutionMariageDto dto = new MentionDissolutionMariageDto();
        
        dto.setId(mention.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
    
}
