/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutActeMariage {
    
    PROJET("Projet"),
    VALIDE("Validé"),
    ANNULE("Annulé");
    
    public String value;
    
    private StatutActeMariage(String value){
        this.value = value;
    }
    
    public static StatutActeMariage fromString(String statutString){
        for(var t : StatutActeMariage.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActeMariage.valueOf(t.name());
            }
        }
        
        Logger log = Logger.getLogger(SituationMatrimoniale.class.getName());
        log.log(Level.SEVERE,"cannot get Enum 'StatutActeMariage' from: {0}", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
}
