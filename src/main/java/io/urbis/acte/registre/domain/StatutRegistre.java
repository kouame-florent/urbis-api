/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.registre.domain;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutRegistre {
    
    PROJET("projet"),
    VALIDE("validé"),
    CLOTURE("cloturé"),
    ANNULE("annulé");
    
    public String value;
    
    private StatutRegistre(String value){
        this.value = value;
    }
    
    public static StatutRegistre fromString(String statutString){
        for(var t : StatutRegistre.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutRegistre.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM STATUT REGISTRE FROM: %s", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
}
