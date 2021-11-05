/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionAdoption;
import io.urbis.mention.dto.AdoptionDto;
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
public class MentionAdoptionService {
    
    public void createMention(@NotNull AdoptionDto dto){
        
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        MentionAdoption mention = MentionAdoption.findById(dto.getId());
        
        if(mention != null){
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();
        
        }else{
            mention = new MentionAdoption();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.dateDressage = dto.getDateDressage();
            mention.decision = dto.getDecision();

            mention.persist();
        }
        
        
    }
    
    public void deleteMention(String mentionID){
        MentionAdoption.deleteById(mentionID);
    }
    
    public List<AdoptionDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionAdoption> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public AdoptionDto mapToDto(@NotNull MentionAdoption mention){
        AdoptionDto dto = new AdoptionDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
}
