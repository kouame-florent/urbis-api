/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.mariage.dto.RegimeDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Regime {
     
    COMMUNAUTE_DE_BIEN("Communauté de biens"),
    SEPARATION_DE_BIEN("Séparation de biens");
    
    private String libelle;
    
    private Regime(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
     
    public static Regime fromString(String regime){
        for(var t : Regime.values()){
            if(t.name().equalsIgnoreCase(regime)){
                return Regime.valueOf(t.name());
            }
        }
        Logger log = Logger.getLogger(Regime.class.getName());
        log.log(Level.SEVERE,"cannot get Enum 'Regime' from: {0}", regime);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(regime)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static RegimeDto mapToDto(Regime regime){
        return new RegimeDto(regime.name(), regime.getLibelle());
    }
}
