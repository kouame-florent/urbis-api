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
public enum StatutActeNaissance {
    
    PROJET("projet"),
    VALIDE("validé"),
    ANNULE("annulé");
    
    public String value;
    
    private StatutActeNaissance(String value){
        this.value = value;
    }
    
    public static StatutActeNaissance fromString(String statutString){
        for(var t : StatutActeNaissance.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActeNaissance.valueOf(t.name());
            }
        }
        
        throw new IllegalArgumentException(statutString);
    }
}
