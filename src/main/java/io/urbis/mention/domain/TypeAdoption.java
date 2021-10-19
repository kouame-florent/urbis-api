/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.mention.dto.TypeAdoptionDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeAdoption {
    ADOPTION_SIMPLE("Adoption simple"),
    ADOPTION_PLENIERE("Adoption plenière");
    
    private final String libelle;
    
    private TypeAdoption(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeAdoption fromString(String libelle){
        for(var t : TypeAdoption.values()){
            if(t.name().equalsIgnoreCase(libelle)){
                return TypeAdoption.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM TypeAdoption FROM: %s", libelle);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(libelle)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeAdoptionDto mapToDto(TypeAdoption type){
        return new TypeAdoptionDto(type.name(), type.getLibelle());
    }
    
}
