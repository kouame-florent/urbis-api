/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/**
 *
 * @author florent
 */

public class RegistreDto {
    
    private String id ;
    
    private LocalDateTime created; 
    private LocalDateTime updated;
    
    private String typeRegistre;
    
    private String libelle;
   
    private String localite; 
    private String localiteID; 
    
    private String centre;
    private String centreID;
    
    @Min(value = 1900, message = "valeur minimale 1900")
    private int annee;
    
    @Min(value = 1, message = "valeur minimale 1")
    private int numero;
    
    private LocalDate dateOuverture;
    
    private String tribunal;
    private String tribunalID;
    
    private String officierEtatCivilNomComplet;
    private String officierEtatCivilID;
    
    private int numeroPremierActe; 
    
    private int numeroDernierActe;
    
    @Min(value = 1, message = "valeur minimale 50")
    private int nombreDeFeuillets = 50;
    
    private int nombreActe;
    
    private String statut;
    
    private LocalDateTime dateAnnulation;
    
    @Size(max = 250)
    private String motifAnnulation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTypeRegistre() {
        return typeRegistre;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getLocaliteID() {
        return localiteID;
    }

    public void setLocaliteID(String localiteID) {
        this.localiteID = localiteID;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public String getCentreID() {
        return centreID;
    }

    public void setCentreID(String centreID) {
        this.centreID = centreID;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getTribunalID() {
        return tribunalID;
    }

    public void setTribunalID(String tribunalID) {
        this.tribunalID = tribunalID;
    }

    public String getOfficierEtatCivilNomComplet() {
        return officierEtatCivilNomComplet;
    }

    public void setOfficierEtatCivilNomComplet(String officierEtatCivilNomComplet) {
        this.officierEtatCivilNomComplet = officierEtatCivilNomComplet;
    }

    public String getOfficierEtatCivilID() {
        return officierEtatCivilID;
    }

    public void setOfficierEtatCivilID(String officierEtatCivilID) {
        this.officierEtatCivilID = officierEtatCivilID;
    }

    public int getNumeroPremierActe() {
        return numeroPremierActe;
    }

    public void setNumeroPremierActe(int numeroPremierActe) {
        this.numeroPremierActe = numeroPremierActe;
    }

    public int getNumeroDernierActe() {
        return numeroDernierActe;
    }

    public void setNumeroDernierActe(int numeroDernierActe) {
        this.numeroDernierActe = numeroDernierActe;
    }

    public int getNombreDeFeuillets() {
        return nombreDeFeuillets;
    }

    public void setNombreDeFeuillets(int nombreDeFeuillets) {
        this.nombreDeFeuillets = nombreDeFeuillets;
    }

    public int getNombreActe() {
        return nombreActe;
    }

    public void setNombreActe(int nombreActe) {
        this.nombreActe = nombreActe;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }
    
    
}
