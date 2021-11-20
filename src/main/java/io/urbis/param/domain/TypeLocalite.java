/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.param.dto.TypeLocaliteDto;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeLocalite {
    
    
    COMMUNE("commune"),
    SOUS_PREFECTURE("sous-préfecture");
    
    public String libelle;
    
    private TypeLocalite(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    
    
    public static TypeLocalite fromString(String localite){
        for(var t : TypeLocalite.values()){
            if(t.name().equalsIgnoreCase(localite)){
                return TypeLocalite.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM 'TypeLocalite' FROM: %s", localite);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(localite)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeLocaliteDto mapToDto(TypeLocalite type){
        return new TypeLocaliteDto(type.name(), type.getLibelle());
    }
    
}
