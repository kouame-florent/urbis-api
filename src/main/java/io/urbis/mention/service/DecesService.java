/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.common.service.DateTimeUtils;
import io.urbis.mention.domain.Deces;
import io.urbis.mention.dto.DecesDto;
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
public class DecesService {
    
     public void creerMention(@NotNull DecesDto dto){
        Deces mention = new Deces();
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;
        
        mention.decision = dto.getDecision();
       // mention.date = DateTimeUtils.fromStringToDateTime(dto.getDate());
        mention.date = dto.getDate();
        mention.lieu = dto.getLieu();
        mention.localite = dto.getLocalite();
        mention.dateDressage = DateTimeUtils.fromStringToDateTime(dto.getDateDressage());
 
    }
    
    public List<DecesDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<Deces> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public DecesDto mapToDto(@NotNull Deces mention){
        DecesDto dto = new DecesDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
   
}
