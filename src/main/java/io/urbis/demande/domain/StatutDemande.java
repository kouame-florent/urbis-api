/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package io.urbis.demande.domain;

import io.urbis.demande.dto.StatutDemandeDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutDemande {
    
    EN_ATTENTE("En attente"), //nouvelle demande avec acte absent
    PRIS_EN_CHARGE("Pris en charge"), //nouvelle demande avec acte present
    En_SIGNATURE("Acte en signature"),
    SIGNE("Acte signé"), 
    CLOTURE("clôturée"); 
    
    
    public String libelle;
    
    private StatutDemande(String value){
        this.libelle = value;
    }

    public String getLibelle() {
        return libelle;
    }
    
        
    public static StatutDemande fromString(String statutString){
        for(var t : StatutDemande.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutDemande.valueOf(t.name());
            }
        }
        
        System.out.printf("CANNOT GET ENUM STATUT FROM: %s", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
    
    public static StatutDemandeDto mapToDto(StatutDemande type){
        return new StatutDemandeDto(type.name(), type.getLibelle());
    }
}
