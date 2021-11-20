/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.domain;

import io.urbis.security.dto.StatutUtilisateurDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutUtilisateur {
    
    ACTIF("actif"),
    INACTIF("inactif"),
    ABSENT("absent");
    
    private final String libelle;
    
    private StatutUtilisateur(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static StatutUtilisateur fromString(String statut){
        for(var t : StatutUtilisateur.values()){
            if(t.name().equalsIgnoreCase(statut)){
                return StatutUtilisateur.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM StatutUtilisateur FROM: %s", statut);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statut)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static StatutUtilisateurDto mapToDto(StatutUtilisateur statut){
        return new StatutUtilisateurDto(statut.name(), statut.getLibelle());
    }
}
