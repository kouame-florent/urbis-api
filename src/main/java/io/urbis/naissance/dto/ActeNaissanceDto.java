/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;


import io.urbis.naissance.domain.Operation;
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
public class ActeNaissanceDto {
    
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
    
    private String enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    private String enfantLocalite;
    private String enfantNom;
    private String enfantPrenoms;
    private String enfantNationalite;  

    private String dateDeclaration;
    private String dateDressage;
   // private String dateEnregistrement;
    
    private String jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
    
    private String modeDeclaration;
    private String typeNaissance;
    
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private String pereDateNaissance;
    private String pereDateDeces;
    private String pereLieuDeces;
    private String pereLocalite;
    private String pereTypePiece;
    private String pereNumeroPiece;
    private String pereDatePiece;
    private String pereLieuPiece;
    
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private String mereDateNaissance;
    private String mereDateDeces;
    private String mereLieuDeces;
    private String mereLocalite;
    private String mereTypePiece;
    private String mereNumeroPiece;
    private String mereDatePiece;
    private String mereLieuPiece;
    
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private String declarantProfession;
    private String declarantLieuNaissance;
    private String declarantNationalite;
    private String declarantDateNaissance;
    private String declarantLocalite;
    private String declarantTypePiece;
    private String declarantNumeroPiece;
    private String declarantDatePiece;
    private String declarantLieuPiece;
    
    private String interpreteNom;
    private String interpretePrenoms;
    private String interpreteProfession;
    private String interpreteDateNaissance;
    private String interpreteDomicile;
    private String interpreteLangue;
    
    private String temoinPremierNom;
    private String temoinPremierPrenoms;
    private String temoinPremierDateNaissance;
    private String temoinPremierProfession;
    private String temoinPremierDomicile;
    
    private String temoinDeuxiemeNom;
    private String temoinDeuxiemePrenoms;
    private String temoinDeuxiemeDateNaissance;
    private String temoinDeuxiemeProfession;
    private String temoinDeuxiemeDomicile;
  
    private String statut;
    private String motifAnnulation;
    private int nombreCopiesIntegrales;
    private int nombreExtraits;
    
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private int registreAnnee;
    private int registreNumero;
    
}