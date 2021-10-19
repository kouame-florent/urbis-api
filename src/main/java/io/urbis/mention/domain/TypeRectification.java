/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.mention.dto.TypeRectificationDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeRectification {
    RECTIFICATION_ADMINISTRATIVE("Rectification administrative"),
    RECTIFICATION_JUDICIAIRE("Rectification judiciaire");
    
    private final String libelle;
    
    private TypeRectification(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeRectification fromString(String libelle){
        for(var t : TypeRectification.values()){
            if(t.name().equalsIgnoreCase(libelle)){
                return TypeRectification.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(libelle)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeRectificationDto mapToDto(TypeDissolutionMariage type){
        return new TypeRectificationDto(type.name(), type.getLibelle());
    }
}
