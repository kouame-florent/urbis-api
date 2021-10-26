/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.common.service.DateTimeUtils;
import io.urbis.mention.domain.Adoption;
import io.urbis.mention.domain.Mariage;
import io.urbis.mention.domain.Mention;
import io.urbis.mention.dto.MariageDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.naissance.dto.MentionDto;
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
public class MariageService {
    
    
     public void creerMention(@NotNull MariageDto dto){
        
        Mariage mention = new Mariage();
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;
        mention.decision = dto.getDecision();
        
        mention.lieu = dto.getLieu();
        mention.date = DateTimeUtils.fromStringToDateTime(dto.getDate());
        mention.dateDressage = DateTimeUtils.fromStringToDateTime(dto.getDateDressage());
        
        mention.epouseNom = dto.getEpouseNom();
        mention.epousePrenoms = dto.getEpousePrenoms();
        mention.epouseProfession = dto.getEpouseProfession();
        mention.epouseDomicile = dto.getEpouseDomicile();
        
        mention.epouxNom = dto.getEpouxNom();
        mention.epouxPrenoms = dto.getEpouxPrenoms();
        mention.epouxProfession = dto.getEpouxProfession();
        mention.epouxDomicile = dto.getEpouxDomicile();
        

    }
    
    public List<MariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<Mariage> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public MariageDto mapToDto(@NotNull Mariage mention){
        MariageDto dto = new MariageDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
    
    
}
