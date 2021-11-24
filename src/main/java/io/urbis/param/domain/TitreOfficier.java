/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.param.dto.TitreOfficierDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TitreOfficier {
    
    MAIRE("Maire"),
    ADJOINT_AU_MAIRE("Adjoint au maire");
    
    public String libelle;
    
    private TitreOfficier(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    
    public static TitreOfficier fromString(String titre){
        for(var t : TitreOfficier.values()){
            if(t.name().equalsIgnoreCase(titre)){
                return TitreOfficier.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM 'TitreOfficier' FROM: %s", titre);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(titre)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TitreOfficierDto mapToDto(TitreOfficier titre){
        return new TitreOfficierDto(titre.name(), titre.getLibelle());
    }
}
