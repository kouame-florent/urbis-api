/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
public class BaseMentionDecesDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    
    @NotNull
    private String id = UUID.randomUUID().toString();
    
    @NotBlank(message = "Le champ 'Decision' ne peut être vide")
    private String decision;
    
    @NotNull
    private LocalDate dateDressage;
  
    @NotBlank(message = "Le champ 'Officier' ne peut être vide")
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private String acteMariageID;

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

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public LocalDate getDateDressage() {
        return dateDressage;
    }

    public void setDateDressage(LocalDate dateDressage) {
        this.dateDressage = dateDressage;
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

    public String getActeMariageID() {
        return acteMariageID;
    }

    public void setActeMariageID(String acteMariageID) {
        this.acteMariageID = acteMariageID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseMentionDecesDto other = (BaseMentionDecesDto) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
    
}
