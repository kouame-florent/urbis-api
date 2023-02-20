/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.dto;

import java.time.LocalDateTime;

/**
 *
 * @author florent
 */

public class OfficierEtatCivilDto {
    
    private String id ;
    private LocalDateTime created ;
    private LocalDateTime updated ;
    private String nom;
    private String prenoms;
    private String profession;
    private String titre; 
    private boolean actif;

    public OfficierEtatCivilDto(String id, LocalDateTime created, LocalDateTime updated, String nom, String prenoms, String profession, String titre, boolean actif) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.nom = nom;
        this.prenoms = prenoms;
        this.profession = profession;
        this.titre = titre;
        this.actif = actif;
    }

    public OfficierEtatCivilDto() {
    }
    
    

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    
 
}
