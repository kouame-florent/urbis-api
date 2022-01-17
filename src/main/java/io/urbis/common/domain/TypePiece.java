/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.domain;


import io.urbis.common.dto.TypePieceDto;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypePiece {
    
    CNI("Carte nationale d'indentite"),
    PASSEPORT("Passeport"),
    CARTE_CONSULAIRE("Carte consulaire"),
    CARTE_DE_SEJOUR("Carte de séjour"),
    ATTESTATION_IDENTITE("Attestation d’identité");
    
    private final String libelle;
    
    private TypePiece(String libelle){
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public static TypePiece fromString(String type){
        for(var t : TypePiece.values()){
            if(t.name().equalsIgnoreCase(type)){
                return TypePiece.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM TYPE PIECE FROM: %s", type);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(type)).build();
        throw new WebApplicationException(res);
       
    }
    
    public static TypePieceDto mapToDto(TypePiece typePiece){
        return new TypePieceDto(typePiece.name(), typePiece.getLibelle());
    }
}
