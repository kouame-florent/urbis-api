/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.mariage.dto.RegimeDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Regime {
    COMMUNAUTE_DE_BIEN("Communauté de bien"),
    SEPARATION_DE_BIEN("Séparation de bien");
    
    private String libelle;
    
    private Regime(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
     
    public static Regime fromString(String regime){
        for(var t : SituationMatrimoniale.values()){
            if(t.name().equalsIgnoreCase(regime)){
                return Regime.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM 'Regime' FROM: %s", regime);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(regime)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static RegimeDto mapToDto(Regime regime){
        return new RegimeDto(regime.name(), regime.getLibelle());
    }
}
