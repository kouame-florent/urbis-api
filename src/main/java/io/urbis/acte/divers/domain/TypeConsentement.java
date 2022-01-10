/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import io.urbis.acte.divers.dto.TypeConsentementDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeConsentement {
    
    EPOUSE_PRESENTE("Epouse présente"),
    ACTE_AUTHENTIQUE("Acte authentique");
    
    private String libelle;
    
    private TypeConsentement(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
     
    public static TypeConsentement fromString(String type){
        for(var t : TypeConsentement.values()){
            if(t.name().equalsIgnoreCase(type)){
                return TypeConsentement.valueOf(t.name());
            }
        }
       
        Logger log = Logger.getLogger(TypeConsentement.class.getName());
        log.log(Level.SEVERE,"cannot get Enum 'SituationMatrimoniale' from: {0}", type);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(type)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeConsentementDto mapToDto(TypeConsentement type){
        return new TypeConsentementDto(type.name(), type.getLibelle());
    }
}
