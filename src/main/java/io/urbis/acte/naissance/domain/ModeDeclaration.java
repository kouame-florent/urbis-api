/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;
  
import io.urbis.acte.naissance.dto.ModeDeclarationDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum ModeDeclaration { 
    
    DIRECT("Direct"),
    JUGEMENT("Jugement");
    
    private final String libelle;
    
    private ModeDeclaration(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static ModeDeclaration fromString(String modeDeclaration){
        for(var t : ModeDeclaration.values()){
            if(t.name().equalsIgnoreCase(modeDeclaration)){
                return ModeDeclaration.valueOf(t.name());
            }
        }
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(modeDeclaration)).build();
        throw new WebApplicationException(res);
        
    }
    
    public static ModeDeclarationDto mapToDto(ModeDeclaration type){
        return new ModeDeclarationDto(type.name(), type.getLibelle());
    }
}
