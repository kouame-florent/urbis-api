/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

/**
 *
 * @author florent
 */
public enum ModeDeclaration {
    
    DIRECT("Direct"),
    JUGEMENT("Jugement");
    
    private final String libelle;
    
    private ModeDeclaration(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static ModeDeclaration fromString(String sexe){
        for(var t : ModeDeclaration.values()){
            if(t.name().equalsIgnoreCase(sexe)){
                return ModeDeclaration.valueOf(t.name());
            }
        }
        
        throw new IllegalArgumentException(sexe);
    }
}
