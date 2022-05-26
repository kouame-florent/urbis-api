/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */

public abstract class MentionDto {
   
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
    
    private String acteNaissanceID;

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

    public String getActeNaissanceID() {
        return acteNaissanceID;
    }

    public void setActeNaissanceID(String acteNaissanceID) {
        this.acteNaissanceID = acteNaissanceID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final MentionDto other = (MentionDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
