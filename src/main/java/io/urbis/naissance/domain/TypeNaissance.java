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
public enum TypeNaissance {
    
    ENFANT_RECONNU_PERE_PRESENT("Enfant reconnu père present"),
    ENFANT_LEGITIME("Enfant légitime"),
    ENFANT_RECONNU_PAR_PROCURATION("Enfant reconnu par procuration"),
    ENFANT_NATUREL("Enfant naturle"),
    ENFANT_SANS_MERE("Enfant sans mère/Enfant trouvé");
   
    private String libelle;
    
    private TypeNaissance(String libelle){
        this.libelle = libelle;
    }
    
    public static TypeNaissance fromString(String typeNaissance){
        for(var t : TypeNaissance.values()){
            if(t.name().equalsIgnoreCase(typeNaissance)){
                return TypeNaissance.valueOf(t.name());
            }
        }
        
        throw new IllegalArgumentException(typeNaissance);
    }
}
