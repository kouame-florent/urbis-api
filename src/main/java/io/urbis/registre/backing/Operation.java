/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Operation {
    
    CREATION("Création"),
    CREATION_SERIE("Création en serie"),
    MODIFICATION("Modification"),
    VALIDATION("Validation"),
    CONSULTATION("Consultation"),
    ANNULATION("Annulation");
    
    
    private final String libelle;
    
    private Operation(String libelle){
        this.libelle = libelle;
    }
    
    public static Operation fromString(String operation){
        for(var t : Operation.values()){
            if(t.name().equalsIgnoreCase(operation)){
                return Operation.valueOf(t.name());
            }
        }
        System.out.printf("cannot get Operation from: %s", operation);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(operation)).build();
        throw new WebApplicationException(res);
    }

    public String getLibelle() {
        return libelle;
    }
}
