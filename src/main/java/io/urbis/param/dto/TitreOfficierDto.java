/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.dto;

/**
 *
 * @author florent
 */

public class TitreOfficierDto {
    private String code;
    private String libelle;

    public TitreOfficierDto(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public TitreOfficierDto() {
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
