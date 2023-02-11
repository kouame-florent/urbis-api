/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 * @author florent
 */

public class DemandeDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String operation;
   
    private String id;
    
    private int numero;
    
    private String demandeurNom;
    
    private String demandeurPrenoms;
    
    private String demandeurNumeroTelephone;
    
    private String demandeurEmail;
    
    private String demandeurTypePiece;
    
    private String demandeurNumeroPiece;
    
    private String demandeurQualite;
    

    private String typeRegistre;

    private int numeroActe;

    private LocalDate dateOuvertureRegistre;
  
    private int nombreExtraits;
 
    private int nombreCopies;
    
    private LocalDateTime dateHeureDemande;
    
    private LocalDateTime dateHeureRdvRetrait;
    
    //@NotBlank
    private String acteID;
    
    private String statutDemande;
    
    private String statutActe;

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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDemandeurNom() {
        return demandeurNom;
    }

    public void setDemandeurNom(String demandeurNom) {
        this.demandeurNom = demandeurNom;
    }

    public String getDemandeurPrenoms() {
        return demandeurPrenoms;
    }

    public void setDemandeurPrenoms(String demandeurPrenoms) {
        this.demandeurPrenoms = demandeurPrenoms;
    }

    public String getDemandeurNumeroTelephone() {
        return demandeurNumeroTelephone;
    }

    public void setDemandeurNumeroTelephone(String demandeurNumeroTelephone) {
        this.demandeurNumeroTelephone = demandeurNumeroTelephone;
    }

    public String getDemandeurEmail() {
        return demandeurEmail;
    }

    public void setDemandeurEmail(String demandeurEmail) {
        this.demandeurEmail = demandeurEmail;
    }

    public String getDemandeurTypePiece() {
        return demandeurTypePiece;
    }

    public void setDemandeurTypePiece(String demandeurTypePiece) {
        this.demandeurTypePiece = demandeurTypePiece;
    }

    public String getDemandeurNumeroPiece() {
        return demandeurNumeroPiece;
    }

    public void setDemandeurNumeroPiece(String demandeurNumeroPiece) {
        this.demandeurNumeroPiece = demandeurNumeroPiece;
    }

    public String getDemandeurQualite() {
        return demandeurQualite;
    }

    public void setDemandeurQualite(String demandeurQualite) {
        this.demandeurQualite = demandeurQualite;
    }

    public String getTypeRegistre() {
        return typeRegistre;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    public int getNumeroActe() {
        return numeroActe;
    }

    public void setNumeroActe(int numeroActe) {
        this.numeroActe = numeroActe;
    }

    public LocalDate getDateOuvertureRegistre() {
        return dateOuvertureRegistre;
    }

    public void setDateOuvertureRegistre(LocalDate dateOuvertureRegistre) {
        this.dateOuvertureRegistre = dateOuvertureRegistre;
    }

    public int getNombreExtraits() {
        return nombreExtraits;
    }

    public void setNombreExtraits(int nombreExtraits) {
        this.nombreExtraits = nombreExtraits;
    }

    public int getNombreCopies() {
        return nombreCopies;
    }

    public void setNombreCopies(int nombreCopies) {
        this.nombreCopies = nombreCopies;
    }

    public LocalDateTime getDateHeureDemande() {
        return dateHeureDemande;
    }

    public void setDateHeureDemande(LocalDateTime dateHeureDemande) {
        this.dateHeureDemande = dateHeureDemande;
    }

    public LocalDateTime getDateHeureRdvRetrait() {
        return dateHeureRdvRetrait;
    }

    public void setDateHeureRdvRetrait(LocalDateTime dateHeureRdvRetrait) {
        this.dateHeureRdvRetrait = dateHeureRdvRetrait;
    }

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }

    public String getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(String statutDemande) {
        this.statutDemande = statutDemande;
    }

    public String getStatutActe() {
        return statutActe;
    }

    public void setStatutActe(String statutActe) {
        this.statutActe = statutActe;
    }
    
    
    
    
}
