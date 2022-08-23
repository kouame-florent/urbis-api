/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author florent
 */


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
    
    private LocalDateTime enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    private String enfantLocalite;
    private String enfantNom;
    private String enfantPrenoms;
    private String enfantNationalite;  

    private LocalDate dateDeclaration;
    private LocalDateTime dateDressage;
   // private String dateEnregistrement;
    
    private LocalDate jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
    
    private String modeDeclaration;
    private String typeNaissance;
    
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private LocalDate pereDateNaissance;
    private LocalDate pereDateDeces;
    private String pereLieuDeces;
    private String pereLocalite;
    private String pereTypePiece;
    private String pereNumeroPiece;
    private LocalDate pereDatePiece;
    private String pereLieuPiece;
    
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private LocalDate mereDateNaissance;
    private LocalDate mereDateDeces;
    private String mereLieuDeces;
    private String mereLocalite;
    private String mereTypePiece;
    private String mereNumeroPiece;
    private LocalDate mereDatePiece;
    private String mereLieuPiece;
    
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private String declarantProfession;
    private String declarantLieuNaissance;
    private String declarantNationalite;
    private LocalDate declarantDateNaissance;
    private String declarantLocalite;
    private String declarantTypePiece;
    private String declarantNumeroPiece;
    private LocalDate declarantDatePiece;
    private String declarantLieuPiece;
    
    private String interpreteNom;
    private String interpretePrenoms;
    private String interpreteProfession;
    private LocalDate interpreteDateNaissance;
    private String interpreteDomicile;
    private String interpreteLangue;
    
    private String temoinPremierNom;
    private String temoinPremierPrenoms;
    private LocalDate temoinPremierDateNaissance;
    private String temoinPremierProfession;
    private String temoinPremierDomicile;
    
    private String temoinDeuxiemeNom;
    private String temoinDeuxiemePrenoms;
    private LocalDate temoinDeuxiemeDateNaissance;
    private String temoinDeuxiemeProfession;
    private String temoinDeuxiemeDomicile;
  
    private String statut;
    private String motifAnnulation;
    //private int nombreCopiesIntegrales;
    //private int nombreExtraits;
    
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private int registreAnnee;
    private int registreNumero;
    
        
    private Set<MentionMariageDto> mentionMariageDtos = new HashSet<>();
    private Set<MentionAdoptionDto> mentionAdoptionDtos = new HashSet<>();
    private Set<MentionDecesDto> mentionDecesDtos = new HashSet<>();
    private Set<MentionDissolutionMariageDto> mentionDissolutionMariageDtos = new HashSet<>();
    private Set<MentionLegitimationDto> mentionLegitimationDtos = new HashSet<>();
    private Set<MentionReconnaissanceDto> mentionReconnaissanceDtos = new HashSet<>();
    private Set<MentionRectificationDto> mentionRectificationDtos = new HashSet<>();
    private Set<MentionAnnulationDto> mentionAnnulationDtos = new HashSet<>();
    
    //private ActeNaissanceEtatDto acteNaissanceEtatDto = new ActeNaissanceEtatDto();

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDateTime getEnfantDateNaissance() {
        return enfantDateNaissance;
    }

    public void setEnfantDateNaissance(LocalDateTime enfantDateNaissance) {
        this.enfantDateNaissance = enfantDateNaissance;
    }

    public String getEnfantLieuNaissance() {
        return enfantLieuNaissance;
    }

    public void setEnfantLieuNaissance(String enfantLieuNaissance) {
        this.enfantLieuNaissance = enfantLieuNaissance;
    }

    public String getEnfantSexe() {
        return enfantSexe;
    }

    public void setEnfantSexe(String enfantSexe) {
        this.enfantSexe = enfantSexe;
    }

    public String getEnfantLocalite() {
        return enfantLocalite;
    }

    public void setEnfantLocalite(String enfantLocalite) {
        this.enfantLocalite = enfantLocalite;
    }

    public String getEnfantNom() {
        return enfantNom;
    }

    public void setEnfantNom(String enfantNom) {
        this.enfantNom = enfantNom;
    }

    public String getEnfantPrenoms() {
        return enfantPrenoms;
    }

    public void setEnfantPrenoms(String enfantPrenoms) {
        this.enfantPrenoms = enfantPrenoms;
    }

    public String getEnfantNationalite() {
        return enfantNationalite;
    }

    public void setEnfantNationalite(String enfantNationalite) {
        this.enfantNationalite = enfantNationalite;
    }

    public LocalDate getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public LocalDateTime getDateDressage() {
        return dateDressage;
    }

    public void setDateDressage(LocalDateTime dateDressage) {
        this.dateDressage = dateDressage;
    }

    public LocalDate getJugementDate() {
        return jugementDate;
    }

    public void setJugementDate(LocalDate jugementDate) {
        this.jugementDate = jugementDate;
    }

    public String getJugementNumero() {
        return jugementNumero;
    }

    public void setJugementNumero(String jugementNumero) {
        this.jugementNumero = jugementNumero;
    }

    public String getJugementTribunal() {
        return jugementTribunal;
    }

    public void setJugementTribunal(String jugementTribunal) {
        this.jugementTribunal = jugementTribunal;
    }

    public String getModeDeclaration() {
        return modeDeclaration;
    }

    public void setModeDeclaration(String modeDeclaration) {
        this.modeDeclaration = modeDeclaration;
    }

    public String getTypeNaissance() {
        return typeNaissance;
    }

    public void setTypeNaissance(String typeNaissance) {
        this.typeNaissance = typeNaissance;
    }

    public String getPereNom() {
        return pereNom;
    }

    public void setPereNom(String pereNom) {
        this.pereNom = pereNom;
    }

    public String getPerePrenoms() {
        return perePrenoms;
    }

    public void setPerePrenoms(String perePrenoms) {
        this.perePrenoms = perePrenoms;
    }

    public String getPereProfession() {
        return pereProfession;
    }

    public void setPereProfession(String pereProfession) {
        this.pereProfession = pereProfession;
    }

    public String getPereLieuNaissance() {
        return pereLieuNaissance;
    }

    public void setPereLieuNaissance(String pereLieuNaissance) {
        this.pereLieuNaissance = pereLieuNaissance;
    }

    public String getPereNationalite() {
        return pereNationalite;
    }

    public void setPereNationalite(String pereNationalite) {
        this.pereNationalite = pereNationalite;
    }

    public LocalDate getPereDateNaissance() {
        return pereDateNaissance;
    }

    public void setPereDateNaissance(LocalDate pereDateNaissance) {
        this.pereDateNaissance = pereDateNaissance;
    }

    public LocalDate getPereDateDeces() {
        return pereDateDeces;
    }

    public void setPereDateDeces(LocalDate pereDateDeces) {
        this.pereDateDeces = pereDateDeces;
    }

    public String getPereLieuDeces() {
        return pereLieuDeces;
    }

    public void setPereLieuDeces(String pereLieuDeces) {
        this.pereLieuDeces = pereLieuDeces;
    }

    public String getPereLocalite() {
        return pereLocalite;
    }

    public void setPereLocalite(String pereLocalite) {
        this.pereLocalite = pereLocalite;
    }

    public String getPereTypePiece() {
        return pereTypePiece;
    }

    public void setPereTypePiece(String pereTypePiece) {
        this.pereTypePiece = pereTypePiece;
    }

    public String getPereNumeroPiece() {
        return pereNumeroPiece;
    }

    public void setPereNumeroPiece(String pereNumeroPiece) {
        this.pereNumeroPiece = pereNumeroPiece;
    }

    public LocalDate getPereDatePiece() {
        return pereDatePiece;
    }

    public void setPereDatePiece(LocalDate pereDatePiece) {
        this.pereDatePiece = pereDatePiece;
    }

    public String getPereLieuPiece() {
        return pereLieuPiece;
    }

    public void setPereLieuPiece(String pereLieuPiece) {
        this.pereLieuPiece = pereLieuPiece;
    }

    public String getMereNom() {
        return mereNom;
    }

    public void setMereNom(String mereNom) {
        this.mereNom = mereNom;
    }

    public String getMerePrenoms() {
        return merePrenoms;
    }

    public void setMerePrenoms(String merePrenoms) {
        this.merePrenoms = merePrenoms;
    }

    public String getMereProfession() {
        return mereProfession;
    }

    public void setMereProfession(String mereProfession) {
        this.mereProfession = mereProfession;
    }

    public String getMereLieuNaissance() {
        return mereLieuNaissance;
    }

    public void setMereLieuNaissance(String mereLieuNaissance) {
        this.mereLieuNaissance = mereLieuNaissance;
    }

    public String getMereNationalite() {
        return mereNationalite;
    }

    public void setMereNationalite(String mereNationalite) {
        this.mereNationalite = mereNationalite;
    }

    public LocalDate getMereDateNaissance() {
        return mereDateNaissance;
    }

    public void setMereDateNaissance(LocalDate mereDateNaissance) {
        this.mereDateNaissance = mereDateNaissance;
    }

    public LocalDate getMereDateDeces() {
        return mereDateDeces;
    }

    public void setMereDateDeces(LocalDate mereDateDeces) {
        this.mereDateDeces = mereDateDeces;
    }

    public String getMereLieuDeces() {
        return mereLieuDeces;
    }

    public void setMereLieuDeces(String mereLieuDeces) {
        this.mereLieuDeces = mereLieuDeces;
    }

    public String getMereLocalite() {
        return mereLocalite;
    }

    public void setMereLocalite(String mereLocalite) {
        this.mereLocalite = mereLocalite;
    }

    public String getMereTypePiece() {
        return mereTypePiece;
    }

    public void setMereTypePiece(String mereTypePiece) {
        this.mereTypePiece = mereTypePiece;
    }

    public String getMereNumeroPiece() {
        return mereNumeroPiece;
    }

    public void setMereNumeroPiece(String mereNumeroPiece) {
        this.mereNumeroPiece = mereNumeroPiece;
    }

    public LocalDate getMereDatePiece() {
        return mereDatePiece;
    }

    public void setMereDatePiece(LocalDate mereDatePiece) {
        this.mereDatePiece = mereDatePiece;
    }

    public String getMereLieuPiece() {
        return mereLieuPiece;
    }

    public void setMereLieuPiece(String mereLieuPiece) {
        this.mereLieuPiece = mereLieuPiece;
    }

    public String getDeclarantLien() {
        return declarantLien;
    }

    public void setDeclarantLien(String declarantLien) {
        this.declarantLien = declarantLien;
    }

    public String getDeclarantNom() {
        return declarantNom;
    }

    public void setDeclarantNom(String declarantNom) {
        this.declarantNom = declarantNom;
    }

    public String getDeclarantPrenoms() {
        return declarantPrenoms;
    }

    public void setDeclarantPrenoms(String declarantPrenoms) {
        this.declarantPrenoms = declarantPrenoms;
    }

    public String getDeclarantProfession() {
        return declarantProfession;
    }

    public void setDeclarantProfession(String declarantProfession) {
        this.declarantProfession = declarantProfession;
    }

    public String getDeclarantLieuNaissance() {
        return declarantLieuNaissance;
    }

    public void setDeclarantLieuNaissance(String declarantLieuNaissance) {
        this.declarantLieuNaissance = declarantLieuNaissance;
    }

    public String getDeclarantNationalite() {
        return declarantNationalite;
    }

    public void setDeclarantNationalite(String declarantNationalite) {
        this.declarantNationalite = declarantNationalite;
    }

    public LocalDate getDeclarantDateNaissance() {
        return declarantDateNaissance;
    }

    public void setDeclarantDateNaissance(LocalDate declarantDateNaissance) {
        this.declarantDateNaissance = declarantDateNaissance;
    }

    public String getDeclarantLocalite() {
        return declarantLocalite;
    }

    public void setDeclarantLocalite(String declarantLocalite) {
        this.declarantLocalite = declarantLocalite;
    }

    public String getDeclarantTypePiece() {
        return declarantTypePiece;
    }

    public void setDeclarantTypePiece(String declarantTypePiece) {
        this.declarantTypePiece = declarantTypePiece;
    }

    public String getDeclarantNumeroPiece() {
        return declarantNumeroPiece;
    }

    public void setDeclarantNumeroPiece(String declarantNumeroPiece) {
        this.declarantNumeroPiece = declarantNumeroPiece;
    }

    public LocalDate getDeclarantDatePiece() {
        return declarantDatePiece;
    }

    public void setDeclarantDatePiece(LocalDate declarantDatePiece) {
        this.declarantDatePiece = declarantDatePiece;
    }

    public String getDeclarantLieuPiece() {
        return declarantLieuPiece;
    }

    public void setDeclarantLieuPiece(String declarantLieuPiece) {
        this.declarantLieuPiece = declarantLieuPiece;
    }

    public String getInterpreteNom() {
        return interpreteNom;
    }

    public void setInterpreteNom(String interpreteNom) {
        this.interpreteNom = interpreteNom;
    }

    public String getInterpretePrenoms() {
        return interpretePrenoms;
    }

    public void setInterpretePrenoms(String interpretePrenoms) {
        this.interpretePrenoms = interpretePrenoms;
    }

    public String getInterpreteProfession() {
        return interpreteProfession;
    }

    public void setInterpreteProfession(String interpreteProfession) {
        this.interpreteProfession = interpreteProfession;
    }

    public LocalDate getInterpreteDateNaissance() {
        return interpreteDateNaissance;
    }

    public void setInterpreteDateNaissance(LocalDate interpreteDateNaissance) {
        this.interpreteDateNaissance = interpreteDateNaissance;
    }

    public String getInterpreteDomicile() {
        return interpreteDomicile;
    }

    public void setInterpreteDomicile(String interpreteDomicile) {
        this.interpreteDomicile = interpreteDomicile;
    }

    public String getInterpreteLangue() {
        return interpreteLangue;
    }

    public void setInterpreteLangue(String interpreteLangue) {
        this.interpreteLangue = interpreteLangue;
    }

    public String getTemoinPremierNom() {
        return temoinPremierNom;
    }

    public void setTemoinPremierNom(String temoinPremierNom) {
        this.temoinPremierNom = temoinPremierNom;
    }

    public String getTemoinPremierPrenoms() {
        return temoinPremierPrenoms;
    }

    public void setTemoinPremierPrenoms(String temoinPremierPrenoms) {
        this.temoinPremierPrenoms = temoinPremierPrenoms;
    }

    public LocalDate getTemoinPremierDateNaissance() {
        return temoinPremierDateNaissance;
    }

    public void setTemoinPremierDateNaissance(LocalDate temoinPremierDateNaissance) {
        this.temoinPremierDateNaissance = temoinPremierDateNaissance;
    }

    public String getTemoinPremierProfession() {
        return temoinPremierProfession;
    }

    public void setTemoinPremierProfession(String temoinPremierProfession) {
        this.temoinPremierProfession = temoinPremierProfession;
    }

    public String getTemoinPremierDomicile() {
        return temoinPremierDomicile;
    }

    public void setTemoinPremierDomicile(String temoinPremierDomicile) {
        this.temoinPremierDomicile = temoinPremierDomicile;
    }

    public String getTemoinDeuxiemeNom() {
        return temoinDeuxiemeNom;
    }

    public void setTemoinDeuxiemeNom(String temoinDeuxiemeNom) {
        this.temoinDeuxiemeNom = temoinDeuxiemeNom;
    }

    public String getTemoinDeuxiemePrenoms() {
        return temoinDeuxiemePrenoms;
    }

    public void setTemoinDeuxiemePrenoms(String temoinDeuxiemePrenoms) {
        this.temoinDeuxiemePrenoms = temoinDeuxiemePrenoms;
    }

    public LocalDate getTemoinDeuxiemeDateNaissance() {
        return temoinDeuxiemeDateNaissance;
    }

    public void setTemoinDeuxiemeDateNaissance(LocalDate temoinDeuxiemeDateNaissance) {
        this.temoinDeuxiemeDateNaissance = temoinDeuxiemeDateNaissance;
    }

    public String getTemoinDeuxiemeProfession() {
        return temoinDeuxiemeProfession;
    }

    public void setTemoinDeuxiemeProfession(String temoinDeuxiemeProfession) {
        this.temoinDeuxiemeProfession = temoinDeuxiemeProfession;
    }

    public String getTemoinDeuxiemeDomicile() {
        return temoinDeuxiemeDomicile;
    }

    public void setTemoinDeuxiemeDomicile(String temoinDeuxiemeDomicile) {
        this.temoinDeuxiemeDomicile = temoinDeuxiemeDomicile;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }

    public String getOfficierEtatCivilID() {
        return officierEtatCivilID;
    }

    public void setOfficierEtatCivilID(String officierEtatCivilID) {
        this.officierEtatCivilID = officierEtatCivilID;
    }

    public String getOfficierEtatCivilNom() {
        return officierEtatCivilNom;
    }

    public void setOfficierEtatCivilNom(String officierEtatCivilNom) {
        this.officierEtatCivilNom = officierEtatCivilNom;
    }

    public String getOfficierEtatCivilPrenoms() {
        return officierEtatCivilPrenoms;
    }

    public void setOfficierEtatCivilPrenoms(String officierEtatCivilPrenoms) {
        this.officierEtatCivilPrenoms = officierEtatCivilPrenoms;
    }

    public String getOfficierEtatCivilQualite() {
        return officierEtatCivilQualite;
    }

    public void setOfficierEtatCivilQualite(String officierEtatCivilQualite) {
        this.officierEtatCivilQualite = officierEtatCivilQualite;
    }

    public String getOfficierEtatCivilTitre() {
        return officierEtatCivilTitre;
    }

    public void setOfficierEtatCivilTitre(String officierEtatCivilTitre) {
        this.officierEtatCivilTitre = officierEtatCivilTitre;
    }

    public int getRegistreAnnee() {
        return registreAnnee;
    }

    public void setRegistreAnnee(int registreAnnee) {
        this.registreAnnee = registreAnnee;
    }

    public int getRegistreNumero() {
        return registreNumero;
    }

    public void setRegistreNumero(int registreNumero) {
        this.registreNumero = registreNumero;
    }

    public Set<MentionMariageDto> getMentionMariageDtos() {
        return mentionMariageDtos;
    }

    public void setMentionMariageDtos(Set<MentionMariageDto> mentionMariageDtos) {
        this.mentionMariageDtos = mentionMariageDtos;
    }

    public Set<MentionAdoptionDto> getMentionAdoptionDtos() {
        return mentionAdoptionDtos;
    }

    public void setMentionAdoptionDtos(Set<MentionAdoptionDto> mentionAdoptionDtos) {
        this.mentionAdoptionDtos = mentionAdoptionDtos;
    }

    public Set<MentionDecesDto> getMentionDecesDtos() {
        return mentionDecesDtos;
    }

    public void setMentionDecesDtos(Set<MentionDecesDto> mentionDecesDtos) {
        this.mentionDecesDtos = mentionDecesDtos;
    }

    public Set<MentionDissolutionMariageDto> getMentionDissolutionMariageDtos() {
        return mentionDissolutionMariageDtos;
    }

    public void setMentionDissolutionMariageDtos(Set<MentionDissolutionMariageDto> mentionDissolutionMariageDtos) {
        this.mentionDissolutionMariageDtos = mentionDissolutionMariageDtos;
    }

    public Set<MentionLegitimationDto> getMentionLegitimationDtos() {
        return mentionLegitimationDtos;
    }

    public void setMentionLegitimationDtos(Set<MentionLegitimationDto> mentionLegitimationDtos) {
        this.mentionLegitimationDtos = mentionLegitimationDtos;
    }

    public Set<MentionReconnaissanceDto> getMentionReconnaissanceDtos() {
        return mentionReconnaissanceDtos;
    }

    public void setMentionReconnaissanceDtos(Set<MentionReconnaissanceDto> mentionReconnaissanceDtos) {
        this.mentionReconnaissanceDtos = mentionReconnaissanceDtos;
    }

    public Set<MentionRectificationDto> getMentionRectificationDtos() {
        return mentionRectificationDtos;
    }

    public void setMentionRectificationDtos(Set<MentionRectificationDto> mentionRectificationDtos) {
        this.mentionRectificationDtos = mentionRectificationDtos;
    }

    public Set<MentionAnnulationDto> getMentionAnnulationDtos() {
        return mentionAnnulationDtos;
    }

    public void setMentionAnnulationDtos(Set<MentionAnnulationDto> mentionAnnulationDtos) {
        this.mentionAnnulationDtos = mentionAnnulationDtos;
    }
    
    
}