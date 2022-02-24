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
        log.infof("-- MENTION DECES OFFICIER ID: %s",dto.getOfficierEtatCivilID());
        log.infof("-- MENTION DECES LIEU: %s",dto.getLieu());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
       
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
