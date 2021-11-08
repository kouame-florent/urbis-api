/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.MentionMariage;
import io.urbis.mention.dto.MentionMariageDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.registre.domain.OfficierEtatCivil;
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
public class MentionMariageService {
  
    public void createMention(@NotNull MentionMariageDto dto){
        
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        if(acte == null){
            throw new EntityNotFoundException("ActeNaissance not found");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw new EntityNotFoundException("OfficierEtatCivil not found");
        }

        MentionMariage mention = MentionMariage.findById(dto.getId());
        
        if(mention != null){
            mention.decision = dto.getDecision();

            mention.lieu = dto.getLieu();
            mention.date = dto.getDate();
            mention.dateDressage = dto.getDateDressage();

            mention.conjointNom = dto.getConjointNom();
            mention.conjointPrenoms = dto.getConjointPrenoms();
            mention.conjointProfession = dto.getConjointProfession();
            mention.conjointDomicile = dto.getConjointDomicile();

            
        }else{
            mention = new MentionMariage();
            
            mention.acteNaissance = acte;
            mention.officierEtatCivil = officier;
            
            mention.decision = dto.getDecision();
            mention.lieu = dto.getLieu();
            mention.date = dto.getDate();
            mention.dateDressage = dto.getDateDressage();

            mention.conjointNom = dto.getConjointNom();
            mention.conjointPrenoms = dto.getConjointPrenoms();
            mention.conjointProfession = dto.getConjointProfession();
            mention.conjointDomicile = dto.getConjointDomicile();
            
            mention.persist();

        }
     
    }
     
    public void deleteMention(String mentionID){
        MentionMariage.deleteById(mentionID);
    }
    
    public Set<MentionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<MentionMariage> mentions = MentionMariage.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toSet());
                
    }
    
    public MentionMariageDto mapToDto(@NotNull MentionMariage mention){
        MentionMariageDto dto = new MentionMariageDto();
        
        dto.setId(mention.id);
        dto.setDate(mention.date);
        dto.setDateDressage(mention.dateDressage);
        dto.setOfficierEtatCivilID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        dto.setLieu(mention.lieu);
        dto.setConjointNom(mention.conjointNom);
        dto.setConjointPrenoms(mention.conjointPrenoms);
        dto.setConjointProfession(mention.conjointProfession);
        dto.setConjointDomicile(mention.conjointDomicile);
        
        return dto;
    }
    
    
}
