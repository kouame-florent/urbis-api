/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.urbis.naissance.dto.NationaliteDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Nationalite {
    
    ALGERIENNE("Algerienne"),
    BENINOISE("Beninoise"),
    BURKINABE("Burkinabe"),
    CAMEROUNAISE("Camerounaise"),
    GHANEENNE("Ghanéenne"),
    GUINEENNE("Guinéenne"),
    IVOIRIENNE("Ivoirienne"),
    FRANCAISE("Francaise"),
    LIBERIENNE("Liberienne"),
    MALIENNE("Malienne");
    
    private String libelle;
    
    private Nationalite(String libelle){
        this.libelle = libelle;
    }
    
    public static Nationalite fromString(String nationalite){
        for(var t : Nationalite.values()){
            if(t.name().equalsIgnoreCase(nationalite)){
                return Nationalite.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM NATIONALITE FROM: %s", nationalite);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(nationalite)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static NationaliteDto mapToDto(Nationalite nationalite){
        return new NationaliteDto(nationalite.name(), nationalite.getLibelle());
    }

    public String getLibelle() {
        return libelle;
    }
    
    
}
