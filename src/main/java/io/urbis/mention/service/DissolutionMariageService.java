/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.service;

import io.urbis.common.service.DateTimeUtils;
import io.urbis.mention.domain.DissolutionMariage;
import io.urbis.mention.domain.Legitimation;
import io.urbis.mention.domain.Mention;
import io.urbis.mention.domain.TypeDissolutionMariage;
import io.urbis.mention.dto.DissolutionMariageDto;
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
public class DissolutionMariageService {
    
     public void creerMention(@NotNull DissolutionMariageDto dto){
        
        DissolutionMariage mention = new DissolutionMariage();
        ActeNaissance acte = ActeNaissance.findById(dto.getActeNaissanceID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        
        mention.acteNaissance = acte;
        mention.officierEtatCivil = officier;
        
        mention.decision = dto.getDecision();
        mention.nom = dto.getNom();
        mention.prenoms = dto.getPrenoms();
        mention.profession = dto.getProfession();
        mention.domicile = dto.getDomicile();
        mention.lieu = dto.getLieu();
        mention.dateMariage = dto.getDateMariage();
        
        mention.numeroJugement = dto.getNumeroJugement();
        mention.dateJugement = DateTimeUtils.fromStringToDateTime(dto.getDateJugement());
        mention.typeDissolution = TypeDissolutionMariage.fromString(dto.getTypeDissolution());
        mention.dateDressage = DateTimeUtils.fromStringToDateTime(dto.getDateDressage());
        

    }
    
    public List<DissolutionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
        ActeNaissance acte = ActeNaissance.findById(acteNaissanceID);
        List<DissolutionMariage> mentions = ActeNaissance.list("acteNaissance", acte);
        return mentions.stream().map(this::mapToDto).collect(Collectors.toList());
                
    }
    
    public DissolutionMariageDto mapToDto(@NotNull DissolutionMariage mention){
        DissolutionMariageDto dto = new DissolutionMariageDto();
        
        dto.setActeNaissanceID(mention.officierEtatCivil.id);
        dto.setActeNaissanceID(mention.acteNaissance.id);
        dto.setDecision(mention.decision);
        
        return dto;
    }
    
    /*
    public String nom;
    public String prenom;
    public String domicile;
    public String profession;
    public String lieu;
    public String dateMariage;
    
    public String numeroJugement;
    public LocalDateTime dateJugement;
    
    @Enumerated(EnumType.STRING)
    public TypeDissolutionMariage typeDissolution;
    
    @ManyToOne
    public Tribunal tribunal;
    public LocalDateTime dateDressage;
    */
}
