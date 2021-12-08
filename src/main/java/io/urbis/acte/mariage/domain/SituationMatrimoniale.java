/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.mariage.dto.SituationMatrimonialeDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum SituationMatrimoniale {
    CELIBATAIRE("Célibataire"),
    DIVORCE("Divorcé");
    
    private String libelle;
    
    private SituationMatrimoniale(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
     
    public static SituationMatrimoniale fromString(String situation){
        for(var t : SituationMatrimoniale.values()){
            if(t.name().equalsIgnoreCase(situation)){
                return SituationMatrimoniale.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM 'SituationMatrimoniale' FROM: %s", situation);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(situation)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static SituationMatrimonialeDto mapToDto(SituationMatrimoniale situation){
        return new SituationMatrimonialeDto(situation.name(), situation.getLibelle());
    }
}
