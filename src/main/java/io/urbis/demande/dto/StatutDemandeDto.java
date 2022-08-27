/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.demande.dto;

/**
 *
 * @author florent
 */
public class StatutDemandeDto {
    private String code;
    private String libelle;

    public StatutDemandeDto(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
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
    
    
}
