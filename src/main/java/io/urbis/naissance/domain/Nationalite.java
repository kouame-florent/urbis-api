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
public enum Nationalite {
    
    IVOIRIENNE,
    FRANCAISE;
    
    public static Nationalite fromString(String nationalite){
        for(var t : Nationalite.values()){
            if(t.name().equalsIgnoreCase(nationalite)){
                return Nationalite.valueOf(t.name());
            }
        }
        
        throw new IllegalArgumentException(nationalite);
    }
}
