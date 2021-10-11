/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.urbis.naissance.dto.TypeNaissanceDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeNaissance {
    
    ENFANT_RECONNU_PERE_PRESENT("Enfant reconnu père present"),
    ENFANT_LEGITIME("Enfant légitime"),
    ENFANT_RECONNU_PAR_PROCURATION("Enfant reconnu par procuration"),
    ENFANT_NATUREL("Enfant naturle"),
    ENFANT_SANS_MERE("Enfant sans mère/Enfant trouvé");
   
    private final String libelle;
    
    private TypeNaissance(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
   
    
    public static TypeNaissance fromString(String typeNaissance){
        for(var t : TypeNaissance.values()){
            if(t.name().equalsIgnoreCase(typeNaissance)){
                return TypeNaissance.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(typeNaissance)).build();
        throw new WebApplicationException(res);
    }
    
    public static TypeNaissanceDto mapToDto(TypeNaissance type){
        return new TypeNaissanceDto(type.name(), type.getLibelle());
    }
}
