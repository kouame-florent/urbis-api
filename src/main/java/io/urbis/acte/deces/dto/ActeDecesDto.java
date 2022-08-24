/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.dto;

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

public class ActeDecesDto { 
    
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
    
    private int registreAnnee;
    private int registreNumero;
    
    private String statut;
    
    public String defuntNom;
    public String defuntPrenoms;
    public LocalDateTime defuntDateDeces;
    public String defuntProfession;
    public String defuntLieuDeces;
    public String defuntDomicile;
    public LocalDate defuntDateNaissance;
    public String defuntLieuNaissance;
    public String defuntLocaliteNaissance;
    public String defuntSexe;
    public String defuntSituationMatrimoniale;
    

    private LocalDate dateDeclaration;
    private LocalDateTime dateDressage;
    
    private LocalDate jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
  
    private boolean pereDecede;
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private LocalDate pereDateNaissance;
    private LocalDate pereDateDeces;
    private String pereLieuDeces;
    private String pereLocaliteDeces;
    
    private boolean mereDecede;
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private LocalDate mereDateNaissance;
    private LocalDate mereDateDeces;
    private String mereLieuDeces;
    private String mereLocaliteDeces;
      
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private LocalDate declarantDateNaissance;
    private String declarantProfession;
    private String declarantDomicile;
        
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private Set<MentionAnnulationDecesDto> mentionAnnulationDtos = new HashSet<>();
    private Set<MentionRectificationDecesDto> mentionRectificationDtos = new HashSet<>();


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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDefuntNom() {
        return defuntNom;
    }

    public void setDefuntNom(String defuntNom) {
        this.defuntNom = defuntNom;
    }

    public String getDefuntPrenoms() {
        return defuntPrenoms;
    }

    public void setDefuntPrenoms(String defuntPrenoms) {
        this.defuntPrenoms = defuntPrenoms;
    }

    public LocalDateTime getDefuntDateDeces() {
        return defuntDateDeces;
    }

    public void setDefuntDateDeces(LocalDateTime defuntDateDeces) {
        this.defuntDateDeces = defuntDateDeces;
    }

    public String getDefuntProfession() {
        return defuntProfession;
    }

    public void setDefuntProfession(String defuntProfession) {
        this.defuntProfession = defuntProfession;
    }

    public String getDefuntLieuDeces() {
        return defuntLieuDeces;
    }

    public void setDefuntLieuDeces(String defuntLieuDeces) {
        this.defuntLieuDeces = defuntLieuDeces;
    }

    public String getDefuntDomicile() {
        return defuntDomicile;
    }

    public void setDefuntDomicile(String defuntDomicile) {
        this.defuntDomicile = defuntDomicile;
    }

    public LocalDate getDefuntDateNaissance() {
        return defuntDateNaissance;
    }

    public void setDefuntDateNaissance(LocalDate defuntDateNaissance) {
        this.defuntDateNaissance = defuntDateNaissance;
    }

    public String getDefuntLieuNaissance() {
        return defuntLieuNaissance;
    }

    public void setDefuntLieuNaissance(String defuntLieuNaissance) {
        this.defuntLieuNaissance = defuntLieuNaissance;
    }

    public String getDefuntLocaliteNaissance() {
        return defuntLocaliteNaissance;
    }

    public void setDefuntLocaliteNaissance(String defuntLocaliteNaissance) {
        this.defuntLocaliteNaissance = defuntLocaliteNaissance;
    }

    public String getDefuntSexe() {
        return defuntSexe;
    }

    public void setDefuntSexe(String defuntSexe) {
        this.defuntSexe = defuntSexe;
    }

    public String getDefuntSituationMatrimoniale() {
        return defuntSituationMatrimoniale;
    }

    public void setDefuntSituationMatrimoniale(String defuntSituationMatrimoniale) {
        this.defuntSituationMatrimoniale = defuntSituationMatrimoniale;
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

    public boolean isPereDecede() {
        return pereDecede;
    }

    public void setPereDecede(boolean pereDecede) {
        this.pereDecede = pereDecede;
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

    public String getPereLocaliteDeces() {
        return pereLocaliteDeces;
    }

    public void setPereLocaliteDeces(String pereLocaliteDeces) {
        this.pereLocaliteDeces = pereLocaliteDeces;
    }

    public boolean isMereDecede() {
        return mereDecede;
    }

    public void setMereDecede(boolean mereDecede) {
        this.mereDecede = mereDecede;
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

    public String getMereLocaliteDeces() {
        return mereLocaliteDeces;
    }

    public void setMereLocaliteDeces(String mereLocaliteDeces) {
        this.mereLocaliteDeces = mereLocaliteDeces;
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

    public LocalDate getDeclarantDateNaissance() {
        return declarantDateNaissance;
    }

    public void setDeclarantDateNaissance(LocalDate declarantDateNaissance) {
        this.declarantDateNaissance = declarantDateNaissance;
    }

    public String getDeclarantProfession() {
        return declarantProfession;
    }

    public void setDeclarantProfession(String declarantProfession) {
        this.declarantProfession = declarantProfession;
    }

    public String getDeclarantDomicile() {
        return declarantDomicile;
    }

    public void setDeclarantDomicile(String declarantDomicile) {
        this.declarantDomicile = declarantDomicile;
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

    public Set<MentionAnnulationDecesDto> getMentionAnnulationDtos() {
        return mentionAnnulationDtos;
    }

    public void setMentionAnnulationDtos(Set<MentionAnnulationDecesDto> mentionAnnulationDtos) {
        this.mentionAnnulationDtos = mentionAnnulationDtos;
    }

    public Set<MentionRectificationDecesDto> getMentionRectificationDtos() {
        return mentionRectificationDtos;
    }

    public void setMentionRectificationDtos(Set<MentionRectificationDecesDto> mentionRectificationDtos) {
        this.mentionRectificationDtos = mentionRectificationDtos;
    }

    
    
    
  
}
