/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.domain;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeRegistre { 
    
    NAISSANCE("Naissance"),
    MARIAGE("Mariage"),
    DIVERS("Divers"),
    DECES("Décès"),
    SPECIAL_NAISSANCE("Spécial naissance"),
    SPECIAL_DECES("Spécial décès");
    
    private final String libelle;
    
    private TypeRegistre(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeRegistre fromString(String typeString){
        for(var t : TypeRegistre.values()){
            if(t.name().equalsIgnoreCase(typeString)){
                return TypeRegistre.valueOf(t.name());
            }
        }

        throw new IllegalStateException("cannot get enum type registre");
       
         
    }
    
}
