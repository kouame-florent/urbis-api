/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.quarkus.panache.common.Sort;
import io.urbis.mention.domain.MentionDeces;
import io.urbis.mention.dto.MentionDecesDto;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.param.domain.OfficierEtatCivil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class MentionDecesService {
    
    @Inject
    Logger log;
    
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
        
        if(mention != null){
            
            mention.decision = dto.getDecision();
            mention.date = dto.getDate();
            mention.lieu = dto.getLieu();
            mention.localite = dto.getLocalite();
            mention.dateDressage = dto.getDateDressage();

        }else{
            mention = new MentionDeces();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
                        
            mention.decision = dto.getDecision();
            mention.date = dto.getDate();
            mention.lieu = dto.getLieu();
            mention.localite = dto.getLocalite();
            mention.dateDressage = dto.getDateDressage();
            
            mention.persist();
        }
        
        
       
 
    }
    
    public void modifierMention(@NotNull MentionDecesDto dto){
            
       
        MentionDeces mention = MentionDeces.findById(dto.getId());
        if(mention == null){
            throw new EntityNotFoundException("MentionDeces not found");
        }
             
        mention.decision = dto.getDecision();
        mention.date = dto.getDate();
        mention.lieu = dto.getLieu();
        mention.localite = dto.getLocalite();
        mention.dateDressage = dto.getDateDressage();

    }
     
    public void deleteMention(String mentionID){
        MentionDeces.deleteById(mentionID);
    }
    
    public Set<MentionDecesDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionDeces> mentions = MentionDeces.list("acteNaissance",Sort.descending("dateDressage"), acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public MentionDecesDto mapToDto(@NotNull MentionDeces mention){
        MentionDecesDto dto = new MentionDecesDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
   
}
