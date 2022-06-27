/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.quarkus.panache.common.Sort;
import io.urbis.mention.domain.MentionMariage;
import io.urbis.mention.dto.MentionMariageDto;
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
public class MentionMariageService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
  
    public void createMention(@NotNull MentionMariageDto dto){
        
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
  
        
        MentionMariage mention = new MentionMariage();
            
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;

        mention.decision = dto.getDecision();
        mention.lieuMariage = dto.getLieu();
        mention.dateMariage = dto.getDate();
        mention.dateDressage = dto.getDateDressage();

        mention.conjointNom = dto.getConjointNom();
        mention.conjointPrenoms = dto.getConjointPrenoms();
        mention.conjointProfession = dto.getConjointProfession();
        mention.conjointDomicile = dto.getConjointDomicile();
        mention.updatedBy = authenticationContext.userLogin();

        mention.persist();

    }
    
    public void modifierMention(@NotNull MentionMariageDto dto){
      
        MentionMariage mention = MentionMariage.findById(dto.getId());
        if(mention == null){ 
            //creer les mentions rajoutées à la modification de l'acte
            createMention(dto);
            
        }else{
            mention.decision = dto.getDecision();

            mention.lieuMariage = dto.getLieu();
            mention.dateMariage = dto.getDate();
            mention.dateDressage = dto.getDateDressage();

            mention.conjointNom = dto.getConjointNom();
            mention.conjointPrenoms = dto.getConjointPrenoms();
            mention.conjointProfession = dto.getConjointProfession();
            mention.conjointDomicile = dto.getConjointDomicile();
            mention.updatedBy = authenticationContext.userLogin();
        }
    
     
    }
     
    public void deleteMention(String mentionID){
        MentionMariage.deleteById(mentionID);
    }
    
    public Set<MentionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionMariage> mentions = MentionMariage.list("acteNaissance",Sort.descending("dateDressage"), acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public String mentionRecenteTexte(ActeNaissance acte){
        
        TypedQuery<MentionMariage> query =  em.createNamedQuery("MentionMariage.findMostRecent", MentionMariage.class);
        query.setParameter("acteNaissance",acte);
          
        try{
            MentionMariage mention = query.getSingleResult();

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
    
    public MentionMariageDto mapToDto(@NotNull MentionMariage mention){
        MentionMariageDto dto = new MentionMariageDto();
        
        dto.setId(mention.id);
        dto.setDate(mention.dateMariage);
        dto.setDateDressage(mention.dateDressage);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        dto.setLieu(mention.lieuMariage);
        dto.setConjointNom(mention.conjointNom);
        dto.setConjointPrenoms(mention.conjointPrenoms);
        dto.setConjointProfession(mention.conjointProfession);
        dto.setConjointDomicile(mention.conjointDomicile);
        
        return dto;
    }
    
    
}
