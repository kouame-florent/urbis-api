/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
public class ActeMariageDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    @NotBlank
    private String operation;
   
    @NotBlank
    private String id;
    
    @NotBlank
    private String registreID;
    
    @Min(1)
    private int numero;
    
    @NotBlank
    private LocalDateTime dateMariage;
    @NotBlank
    private String lieuMariage;
    @NotBlank
    private String regime;
    
    private int registreAnnee;
    private int registreNumero;
    
    private String statut;
   
    @NotBlank
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilTitre;
    
    private String epouxConjointNom;
    private String epouxConjointPrenoms;
    private String epouxConjointProfession;
    private String epouxConjointLieuNaissance;
    private LocalDate epouxConjointDateNaissance;
    private String epouxConjointDomicile;
    private String epouxConjointSituationMatrimoniale;
    
    
    private String epouxPereNom;
    private String epouxPerePrenoms;
    private String epouxPereProfession;
    private String epouxPereDomicile;
    private boolean epouxPereDecede;
    private String epouxPereLieuDeces;
    private LocalDate epouxPereDateDeces;
    
    private String epouxMereNom;
    private String epouxMerePrenoms;
    private String epouxMereProfession;
    private String epouxMereDomicile;
    private boolean epouxMereDecede;
    private String epouxMereLieuDeces;
    private LocalDate epouxMereDateDeces;
  
    
    private String epouxTemoinNom;
    private String epouxTemoinPrenoms;
    private String epouxTemoinProfession;
    private String epouxTemoinDomicile;
    private LocalDate epouxTemoinDateNaissance;
    
    private String epouseConjointNom;
    private String epouseConjointPrenoms;
    private String epouseConjointProfession;
    private String epouseConjointLieuNaissance;
    private LocalDate epouseConjointDateNaissance;
    private String epouseConjointDomicile;
    private String epouseConjointSituationMatrimoniale;
    
    private String epousePereNom;
    private String epousePerePrenoms;
    private String epousePereProfession;
    private String epousePereDomicile;
    private boolean epousePereDecede;
    private String epousePereLieuDeces;
    private LocalDate epousePereDateDeces;
  
    
    private String epouseMereNom;
    private String epouseMerePrenoms;
    private String epouseMereProfession;
    private String epouseMereDomicile;
    private boolean epouseMereDecede;
    private String epouseMereLieuDeces;
    private LocalDate epouseMereDateDeces;
  
    
    private String epouseTemoinNom;
    private String epouseTemoinPrenoms;
    private String epouseTemoinProfession;
    private String epouseTemoinDomicile;
    private LocalDate epouseTemoinDateNaissance;

    
}
