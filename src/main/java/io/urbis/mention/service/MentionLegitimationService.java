/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionLegitimation;
import io.urbis.mention.dto.MentionLegitimationDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.registre.domain.OfficierEtatCivil;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class MentionLegitimationService {
    
     public void createMention(@NotNull MentionLegitimationDto dto){
        
       
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        MentionLegitimation mention = MentionLegitimation.findById(dto.getId());
        
        if(mention != null){
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();
        
        }else{
            mention = new MentionLegitimation();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();

            mention.persist();
        }
    }
     
    public void deleteMention(String mentionID){
        MentionLegitimation.deleteById(mentionID);
    }
    
    public List<MentionLegitimationDto > findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionLegitimation> mentions = MentionLegitimation.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public MentionLegitimationDto  mapToDto(@NotNull MentionLegitimation mention){
        MentionLegitimationDto  dto = new MentionLegitimationDto ();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
}
