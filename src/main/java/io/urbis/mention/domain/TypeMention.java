/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.naissance.dto.TypeMentionDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeMention {
    ADOPTION("Adoption"),
    DECES("Décès"),
    DISSOLUTION_MARIAGE("Dissolution de mariage"),
    LEGITIMATION("Légitimation"),
    MARIAGE("Mariage"),
    RECONNAISSANCE("Reconnaissance"),
    RECTIFICATION("Rectification");
    
    private String libelle;
    
    private TypeMention(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
   
    
    public static TypeMention fromString(String libelle){
        for(var t : TypeMention.values()){
            if(t.name().equalsIgnoreCase(libelle)){
                return TypeMention.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(libelle)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypeMentionDto mapToDto(TypeMention type){
        return new TypeMentionDto(type.name(), type.getLibelle());
    }
}
