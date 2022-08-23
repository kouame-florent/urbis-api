/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.naissance.service;

import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.MentionAdoption;
import io.urbis.acte.naissance.domain.MentionAnnulation;
import io.urbis.acte.naissance.dto.MentionAnnulationDto;
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
public class MentionAnnulationService {
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    EntityManager em;
   
    
    public void createMention(@NotNull MentionAnnulationDto dto){
       
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
           
        var mention = new MentionAnnulation();
            
        mention.updatedBy = authenticationContext.userLogin();
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;

        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();
   
        
    }
    
    public void modifierMention(@NotNull MentionAnnulationDto dto){
        
        MentionAnnulation mention = MentionAdoption.findById(dto.getId());
        
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
    
    public Set<MentionAnnulationDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionAnnulation> mentions = MentionAnnulation.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeNaissance acte){
        
        TypedQuery<MentionAnnulation> query =  em.createNamedQuery("MentionAnnulation.findMostRecent", MentionAnnulation.class);
        query.setParameter("acteNaissance",acte);
          
        try{
           MentionAnnulation mention = query.getSingleResult();

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
    
    
    public MentionAnnulationDto mapToDto(@NotNull MentionAnnulation mention){
        MentionAnnulationDto dto = new MentionAnnulationDto();
        
        dto.setId(mention.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
}
