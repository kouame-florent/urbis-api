/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;


import io.urbis.acte.naissance.domain.*;
import io.urbis.common.dto.SexeDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Sexe {
    MASCULIN("MASCULIN"),
    FEMININ("FEMININ");
    
    private String libelle;
    
    private Sexe(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
     
    public static Sexe fromString(String sexe){
        for(var t : Sexe.values()){
            if(t.name().equalsIgnoreCase(sexe)){
                return Sexe.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM SEXE FROM: %s", sexe);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(sexe)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static SexeDto mapToDto(Sexe sexe){
        return new SexeDto(sexe.name(), sexe.getLibelle());
    }
}
