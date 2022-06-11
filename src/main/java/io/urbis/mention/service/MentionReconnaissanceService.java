/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionReconnaissance;
import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.param.domain.OfficierEtatCivil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class MentionReconnaissanceService {
    
    public void createMention(@NotNull MentionReconnaissanceDto dto){
        
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }
        
        MentionReconnaissance mention = new MentionReconnaissance();

        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;

        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();

        mention.persist();
        
    }
    
    public void modifierMention(@NotNull MentionReconnaissanceDto dto){
         
        MentionReconnaissance mention = MentionReconnaissance.findById(dto.getId());
        if(mention == null){
            throw new EntityNotFoundException("MentionReconnaissance not found");
        }
        
        mention.dateDressage = dto.getDateDressage();
        mention.decision = dto.getDecision();
        
    }
     
    public void deleteMention(String mentionID){
        MentionReconnaissance.deleteById(mentionID);
    }
    
    public Set<MentionReconnaissanceDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionReconnaissance> mentions = MentionReconnaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public MentionReconnaissanceDto mapToDto(@NotNull MentionReconnaissance mention){
        MentionReconnaissanceDto dto = new MentionReconnaissanceDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        dto.setDateDressage(mention.dateDressage);
        
        return dto;
    }
}
