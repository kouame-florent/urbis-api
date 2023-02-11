/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.demande.domain;

import javax.persistence.EntityNotFoundException;

/**
 *
 * @author florent
 */
public enum StatutActe {
    
    ACTE_PRESENT("Acte absent"),
    ACTE_ABSENT("Acte présent");
   
    
    public String libelle;
    
    private StatutActe (String value){
        this.libelle = value;
    }

    public String getLibelle() {
        return libelle;
    }
    
        
    public static StatutActe fromString(String statutString){
        for(var t : StatutActe.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActe.valueOf(t.name());
            }
        }
        
        String msg = String.format("cannot get enum from: %s", statutString);
        throw new EntityNotFoundException(msg);
    }
    
   
    
}
