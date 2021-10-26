/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.mention.domain.Adoption;
import io.urbis.mention.domain.Mention;
import io.urbis.mention.domain.Reconnaissance;
import io.urbis.mention.dto.ReconnaissanceDto;
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
public class ReconnaissanceService {
    
     public void creerMention(@NotNull ReconnaissanceDto dto){
        
        Reconnaissance mention = new Reconnaissance();
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;
        
        mention.decision = dto.getDecision();
        

    }
    
    public List<ReconnaissanceDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<Reconnaissance> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public ReconnaissanceDto mapToDto(@NotNull Reconnaissance mention){
        ReconnaissanceDto dto = new ReconnaissanceDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
}
