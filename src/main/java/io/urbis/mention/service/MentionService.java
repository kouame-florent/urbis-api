/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.mention.domain.Mention;
import io.urbis.mention.domain.TypeMention;
import io.urbis.naissance.dto.MentionDto;
import io.urbis.naissance.dto.TypeMentionDto;
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
public class MentionService {
    
    public void creerMention(@NotNull MentionDto dto){
        /*
        Mention mention = new Mention();
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officer = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officer;
        
        mention.decision = dto.getDecision();
        mention.typeMention = TypeMention.fromString(dto.getTypeMention());
*/
    }
    
    public List<MentionDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<Mention> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public MentionDto mapToDto(@NotNull Mention mention){
        MentionDto dto = new MentionDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
       // dto.setTypeMention(mention.typeMention.name());
        
        return dto;
    }
}
