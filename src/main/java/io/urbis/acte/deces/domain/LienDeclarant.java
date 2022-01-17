/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.domain;


import io.urbis.acte.deces.dto.LienDeclarantDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum LienDeclarant {
    
    FILS("Fils"),
    FILLE("Fille"),
    AUTRES("Autres");
    
    private String libelle;
    
    private LienDeclarant(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
   
    public static LienDeclarant fromString(String lien){
        for(var t : LienDeclarant.values()){
            if(t.name().equalsIgnoreCase(lien)){
                return LienDeclarant.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(lien)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static LienDeclarantDto mapToDto(LienDeclarant lien){
        return new LienDeclarantDto(lien.name(), lien.getLibelle());
    }
}
