/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.domain;

/**
 *
 * @author florent
 */
public enum TypeLocalite {
    
    COMMUNE("commune"),
    SOUS_PREFECTURE("sous-préfecture");
    
    public String value;
    
    private TypeLocalite(String value){
        this.value = value;
    }
    
}
