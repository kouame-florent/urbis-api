/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.util.Objects;

/**
 *
 * @author florent
 */

public class TypeRegistreDto {
    
    private String code;
    private String libelle;

    public TypeRegistreDto() {
    }

    public TypeRegistreDto(String code, String libelle) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.code);
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
        final TypeRegistreDto other = (TypeRegistreDto) obj;
        return Objects.equals(this.code, other.code);
    }
    
    
    
}
