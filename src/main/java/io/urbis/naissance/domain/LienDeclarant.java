/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.urbis.naissance.dto.LienDeclarantDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum LienDeclarant {
    
    PERE("Père"),
    MERE("Mère"),
    AUTRES("Autres");
    
    private String libelle;
    
    private LienDeclarant(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
   
    public static Sexe fromString(String sexe){
        for(var t : Sexe.values()){
            if(t.name().equalsIgnoreCase(sexe)){
                return Sexe.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(sexe)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static LienDeclarantDto mapToDto(LienDeclarant lien){
        return new LienDeclarantDto(lien.name(), lien.getLibelle());
    }
}
