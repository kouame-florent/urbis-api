/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.mention.dto.TypeReconnaissanceDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeReconnaissance {
    RECONNAISSANCE_ENFANT_NATUREL("Reconnaissance enfant naturel"),
    RECONNAISSANCE_ENFANT_ADULTERIN("Reconnaissance enfant adulterin"),
    CONSENTEMENT_RECONNAISSANCE("Consentement à reconnaissance");
    
    private final String libelle;
    
    private TypeReconnaissance(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeReconnaissance fromString(String libelle){
        for(var t : TypeReconnaissance.values()){
            if(t.name().equalsIgnoreCase(libelle)){
                return TypeReconnaissance.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(libelle)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeReconnaissanceDto mapToDto(TypeDissolutionMariage type){
        return new TypeReconnaissanceDto(type.name(), type.getLibelle());
    }
}
