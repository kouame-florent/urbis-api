/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.dto;

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

public class ActeRecEnfantNaturelDto{
    
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
    
    @NotBlank
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilTitre;
    
    private LocalDateTime reconnaissanceDate;
    private String reconnaissanceLieu;
    private String reconnaissanceNatureCirconscription;
    private String reconnaissanceNomCirconscription;
    
    private int enfantNumeroActe;
    private LocalDate enfantDateActe;
    private String enfantNom;
    private String enfantPrenoms;
    private LocalDate enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    
    private String reconnaissantNom;
    private String reconnaissantPrenoms;
    private LocalDate reconnaissantDateNaissance;
    private String reconnaissantProfession;
    private String reconnaissantDomicile;
    

    private String mereEnfantNom;
    private String mereEnfantPrenoms;
    private LocalDate mereEnfantDateNaissance;
    private String mereEnfantProfession;
    private String mereEnfantDomicile;

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

    public String getOfficierEtatCivilTitre() {
        return officierEtatCivilTitre;
    }

    public void setOfficierEtatCivilTitre(String officierEtatCivilTitre) {
        this.officierEtatCivilTitre = officierEtatCivilTitre;
    }

    public LocalDateTime getReconnaissanceDate() {
        return reconnaissanceDate;
    }

    public void setReconnaissanceDate(LocalDateTime reconnaissanceDate) {
        this.reconnaissanceDate = reconnaissanceDate;
    }

    public String getReconnaissanceLieu() {
        return reconnaissanceLieu;
    }

    public void setReconnaissanceLieu(String reconnaissanceLieu) {
        this.reconnaissanceLieu = reconnaissanceLieu;
    }

    public String getReconnaissanceNatureCirconscription() {
        return reconnaissanceNatureCirconscription;
    }

    public void setReconnaissanceNatureCirconscription(String reconnaissanceNatureCirconscription) {
        this.reconnaissanceNatureCirconscription = reconnaissanceNatureCirconscription;
    }

    public String getReconnaissanceNomCirconscription() {
        return reconnaissanceNomCirconscription;
    }

    public void setReconnaissanceNomCirconscription(String reconnaissanceNomCirconscription) {
        this.reconnaissanceNomCirconscription = reconnaissanceNomCirconscription;
    }

    public int getEnfantNumeroActe() {
        return enfantNumeroActe;
    }

    public void setEnfantNumeroActe(int enfantNumeroActe) {
        this.enfantNumeroActe = enfantNumeroActe;
    }

    public LocalDate getEnfantDateActe() {
        return enfantDateActe;
    }

    public void setEnfantDateActe(LocalDate enfantDateActe) {
        this.enfantDateActe = enfantDateActe;
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

    public LocalDate getEnfantDateNaissance() {
        return enfantDateNaissance;
    }

    public void setEnfantDateNaissance(LocalDate enfantDateNaissance) {
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

    public String getReconnaissantNom() {
        return reconnaissantNom;
    }

    public void setReconnaissantNom(String reconnaissantNom) {
        this.reconnaissantNom = reconnaissantNom;
    }

    public String getReconnaissantPrenoms() {
        return reconnaissantPrenoms;
    }

    public void setReconnaissantPrenoms(String reconnaissantPrenoms) {
        this.reconnaissantPrenoms = reconnaissantPrenoms;
    }

    public LocalDate getReconnaissantDateNaissance() {
        return reconnaissantDateNaissance;
    }

    public void setReconnaissantDateNaissance(LocalDate reconnaissantDateNaissance) {
        this.reconnaissantDateNaissance = reconnaissantDateNaissance;
    }

    public String getReconnaissantProfession() {
        return reconnaissantProfession;
    }

    public void setReconnaissantProfession(String reconnaissantProfession) {
        this.reconnaissantProfession = reconnaissantProfession;
    }

    public String getReconnaissantDomicile() {
        return reconnaissantDomicile;
    }

    public void setReconnaissantDomicile(String reconnaissantDomicile) {
        this.reconnaissantDomicile = reconnaissantDomicile;
    }

    public String getMereEnfantNom() {
        return mereEnfantNom;
    }

    public void setMereEnfantNom(String mereEnfantNom) {
        this.mereEnfantNom = mereEnfantNom;
    }

    public String getMereEnfantPrenoms() {
        return mereEnfantPrenoms;
    }

    public void setMereEnfantPrenoms(String mereEnfantPrenoms) {
        this.mereEnfantPrenoms = mereEnfantPrenoms;
    }

    public LocalDate getMereEnfantDateNaissance() {
        return mereEnfantDateNaissance;
    }

    public void setMereEnfantDateNaissance(LocalDate mereEnfantDateNaissance) {
        this.mereEnfantDateNaissance = mereEnfantDateNaissance;
    }

    public String getMereEnfantProfession() {
        return mereEnfantProfession;
    }

    public void setMereEnfantProfession(String mereEnfantProfession) {
        this.mereEnfantProfession = mereEnfantProfession;
    }

    public String getMereEnfantDomicile() {
        return mereEnfantDomicile;
    }

    public void setMereEnfantDomicile(String mereEnfantDomicile) {
        this.mereEnfantDomicile = mereEnfantDomicile;
    }
    
    
}
