/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionRectification;
import io.urbis.mention.dto.MentionRectificationDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.param.domain.OfficierEtatCivil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class MentionRectificationService {
    
     public void createMention(@NotNull MentionRectificationDto dto){
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        MentionRectification mention = MentionRectification.findById(dto.getId());
        
        if(mention != null){
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();
        
        }else{
            mention = new MentionRectification();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();

            mention.persist();
        }
    }
     
    public void deleteMention(String mentionID){
        MentionRectification.deleteById(mentionID);
    }
    
    public Set<MentionRectificationDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionRectification> mentions = MentionRectification.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public MentionRectificationDto mapToDto(@NotNull MentionRectification mention){
        MentionRectificationDto dto = new MentionRectificationDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
}
