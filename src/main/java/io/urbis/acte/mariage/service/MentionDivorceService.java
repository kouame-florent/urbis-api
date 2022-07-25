/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.mariage.service;

import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.mariage.domain.MentionDivorce;
import io.urbis.acte.mariage.dto.MentionDivorceDto;
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
public class MentionDivorceService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    public void createMention(@NotNull MentionDivorceDto dto){
        
        ActeMariage acte = ActeMariage.findById(dto.getActeMariageID());
        if(acte == null){
            throw new EntityNotFoundException("ActeMariage not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
       
        MentionDivorce mention = new MentionDivorce();

        mention.acteMariage = acte;
        mention.officierEtatCivil = officier;

        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();
        
    }
    
    public void modifierMention(@NotNull MentionDivorceDto dto){
         
        MentionDivorce mention = MentionDivorce.findById(dto.getId());
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
        MentionDivorce.deleteById(mentionID);
    }
    
    public Set<MentionDivorceDto> findByActeMariage(@NotBlank String acteMariageID){
        ActeMariage acte = ActeMariage.findById(acteMariageID);
        List<MentionDivorce> mentions = MentionDivorce.list("acteMariage", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeMariage acte){
        
        TypedQuery<MentionDivorce> query =  em.createNamedQuery("MentionDivorce.findMostRecent", MentionDivorce.class);
        query.setParameter("acteMariage",acte);
          
        try{
            MentionDivorce mention = query.getSingleResult();

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
    
    public MentionDivorceDto  mapToDto(@NotNull MentionDivorce mention){
        MentionDivorceDto  dto = new MentionDivorceDto ();
        
        dto.setId(mention.id);
        dto.setActeMariageID(mention.acteMariage.id);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
    
}
