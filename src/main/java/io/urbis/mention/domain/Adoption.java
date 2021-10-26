/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_adoption")
public class Adoption extends Mention{
    
    public String jugementNumero;
    public LocalDateTime jugementDate;
    public String jugementTribunal;
    
    public String adoptantNom;
    public String adoptantsPrenoms;
    
    public LocalDateTime dateDressage;
    
    public EnTantQue enTantQue;
    
    public String adopteNom;
    public String adoptePrenoms;
    
    public TypeAdoption typeAdoption;
    
    public static enum EnTantQue{
        PERE("Père"),
        MERE("Mère");
        
        private String libelle;
        
        private EnTantQue(String libelle){
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
        
        
    }
}
