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

/**
 *
 * @author florent
 */

public class ActeConsReconnaissanceDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String id;
    
    @NotBlank
    private String operation;
       
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
    
    private LocalDate consentementDate;
    private String  consentementNatureCirconscription;
    private String  consentementNomCirconscription;
    
    private String  consentementNom;
    private String  consentementPrenoms;
    private String  consentementProfession;
    private String  consentementDomicile;
    
    private LocalDate enfantConsentiDateNaissance;
    private String  enfantConsentiNom;
    private String  enfantConsentiPrenoms;
    private String  enfantConsentiProfession;
    private String  enfantConsentiDomicile;
    
    private String  contractantNom;
    private String  contractantPrenoms;
    private String  contractantProfession;
    private String  contractantDomicile;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public LocalDate getConsentementDate() {
        return consentementDate;
    }

    public void setConsentementDate(LocalDate consentementDate) {
        this.consentementDate = consentementDate;
    }

    public String getConsentementNatureCirconscription() {
        return consentementNatureCirconscription;
    }

    public void setConsentementNatureCirconscription(String consentementNatureCirconscription) {
        this.consentementNatureCirconscription = consentementNatureCirconscription;
    }

    public String getConsentementNomCirconscription() {
        return consentementNomCirconscription;
    }

    public void setConsentementNomCirconscription(String consentementNomCirconscription) {
        this.consentementNomCirconscription = consentementNomCirconscription;
    }

    public String getConsentementNom() {
        return consentementNom;
    }

    public void setConsentementNom(String consentementNom) {
        this.consentementNom = consentementNom;
    }

    public String getConsentementPrenoms() {
        return consentementPrenoms;
    }

    public void setConsentementPrenoms(String consentementPrenoms) {
        this.consentementPrenoms = consentementPrenoms;
    }

    public String getConsentementProfession() {
        return consentementProfession;
    }

    public void setConsentementProfession(String consentementProfession) {
        this.consentementProfession = consentementProfession;
    }

    public String getConsentementDomicile() {
        return consentementDomicile;
    }

    public void setConsentementDomicile(String consentementDomicile) {
        this.consentementDomicile = consentementDomicile;
    }

    public LocalDate getEnfantConsentiDateNaissance() {
        return enfantConsentiDateNaissance;
    }

    public void setEnfantConsentiDateNaissance(LocalDate enfantConsentiDateNaissance) {
        this.enfantConsentiDateNaissance = enfantConsentiDateNaissance;
    }

    public String getEnfantConsentiNom() {
        return enfantConsentiNom;
    }

    public void setEnfantConsentiNom(String enfantConsentiNom) {
        this.enfantConsentiNom = enfantConsentiNom;
    }

    public String getEnfantConsentiPrenoms() {
        return enfantConsentiPrenoms;
    }

    public void setEnfantConsentiPrenoms(String enfantConsentiPrenoms) {
        this.enfantConsentiPrenoms = enfantConsentiPrenoms;
    }

    public String getEnfantConsentiProfession() {
        return enfantConsentiProfession;
    }

    public void setEnfantConsentiProfession(String enfantConsentiProfession) {
        this.enfantConsentiProfession = enfantConsentiProfession;
    }

    public String getEnfantConsentiDomicile() {
        return enfantConsentiDomicile;
    }

    public void setEnfantConsentiDomicile(String enfantConsentiDomicile) {
        this.enfantConsentiDomicile = enfantConsentiDomicile;
    }

    public String getContractantNom() {
        return contractantNom;
    }

    public void setContractantNom(String contractantNom) {
        this.contractantNom = contractantNom;
    }

    public String getContractantPrenoms() {
        return contractantPrenoms;
    }

    public void setContractantPrenoms(String contractantPrenoms) {
        this.contractantPrenoms = contractantPrenoms;
    }

    public String getContractantProfession() {
        return contractantProfession;
    }

    public void setContractantProfession(String contractantProfession) {
        this.contractantProfession = contractantProfession;
    }

    public String getContractantDomicile() {
        return contractantDomicile;
    }

    public void setContractantDomicile(String contractantDomicile) {
        this.contractantDomicile = contractantDomicile;
    }
    
    
}
