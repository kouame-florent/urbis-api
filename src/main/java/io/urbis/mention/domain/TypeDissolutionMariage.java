/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.mention.dto.TypeDissolutionMariageDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeDissolutionMariage {
    SÉPARATION_DE_CORPS("Séparation de corps"),
    DIVORCE("Divorce"),
    DISSOLUTION_DE_MARIAGE_PAR_DÉCÈS("Dissolution de mariage par Décès");
    
    private final String libelle;
    
    private TypeDissolutionMariage(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeDissolutionMariage fromString(String libelle){
        for(var t : TypeDissolutionMariage.values()){
            if(t.name().equalsIgnoreCase(libelle)){
                return TypeDissolutionMariage.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(libelle)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeDissolutionMariageDto mapToDto(TypeDissolutionMariage type){
        return new TypeDissolutionMariageDto(type.name(), type.getLibelle());
    }
}
