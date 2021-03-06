/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;


import io.urbis.acte.naissance.dto.TypeNaissanceDto;
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
    ENFANT_NATUREL("Enfant naturel"),
    ENFANT_SANS_MERE("Enfant sans mère/Enfant trouvé"),
    ENFANT_TROUVE("Enfant trouvé"),
    ENFANT_ADULTERIN_RECONNU_PAR_ACTE_DE_CONSENTEMENT("Enfant adultérin reconnu par acte de consentement"),
    ENFANT_ADULTERIN_RECONNU_PAR_PROCURATION("Enfant adultérin reconnu par procuration");
    
   
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
        System.out.printf("CANNOT GET ENUM TYPE NAISSANCE FROM: %s", typeNaissance);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(typeNaissance)).build();
        throw new WebApplicationException(res);
    }
    
    public static TypeNaissanceDto mapToDto(TypeNaissance type){
        return new TypeNaissanceDto(type.name(), type.getLibelle());
    }
}
