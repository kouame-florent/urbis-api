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

public class CentreDto {
      
    private String id;
    
    private LocalDateTime created;
    private LocalDateTime updated ;
    
    private String code;
    private String libelle;
    
    private String localiteID;
    private String localite;

    public CentreDto(String id, LocalDateTime created, LocalDateTime updated, String code, String libelle, String localiteID, String localite) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.code = code;
        this.libelle = libelle;
        this.localiteID = localiteID;
        this.localite = localite;
    }

    

    public CentreDto() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLocaliteID() {
        return localiteID;
    }

    public void setLocaliteID(String localiteID) {
        this.localiteID = localiteID;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }
    
   
    
}
