/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionReconnaissance;
import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.param.domain.OfficierEtatCivil;
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
public class MentionReconnaissanceService {
    
     public void createMention(@NotNull MentionReconnaissanceDto dto){
        
         ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        MentionReconnaissance mention = MentionReconnaissance.findById(dto.getId());
        
        if(mention != null){
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();
        
        }else{
            mention = new MentionReconnaissance();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();

            mention.persist();
        }
    }
     
    public void deleteMention(String mentionID){
        MentionReconnaissance.deleteById(mentionID);
    }
    
    public List<MentionReconnaissanceDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionReconnaissance> mentions = MentionReconnaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public MentionReconnaissanceDto mapToDto(@NotNull MentionReconnaissance mention){
        MentionReconnaissanceDto dto = new MentionReconnaissanceDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
}
