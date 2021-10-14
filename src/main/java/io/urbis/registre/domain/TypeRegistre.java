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
    
    NAISSANCE("Registre de naissance"),
    MARIAGE("Registre de mariage"),
    DECES("Registre de decès"),
    DIVERS("Registre d'actes divers"),
    SPECIAL_NAISSANCE("Registre spécial de naissance");
    
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
        System.out.printf("CANNOT GET ENUM TYPE REGISTRE FROM: %s", typeString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(typeString)).build();
        throw new WebApplicationException(res);
         
    }
    
}
