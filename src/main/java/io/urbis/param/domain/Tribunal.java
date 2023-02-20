/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.common.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author florent
 */

@Table(name = "tribunal")
@Entity
public class Tribunal extends  BaseEntity{
        
    public String code;
    public String libelle;
    
   // public StatutParametre statut;

    public Tribunal(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Tribunal() {
    }
    
    
    
}
