/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.common.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */

@Table(name = "localite")
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class Localite extends  BaseEntity{
    
    public String code;
    public String libelle;
        
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_localite",nullable = false)
    public TypeLocalite typeLocalite;
    
   // public StatutParametre statut;

    public Localite(String code, String libelle, TypeLocalite typeLocalite) {
        this.code = code;
        this.libelle = libelle;
        this.typeLocalite = typeLocalite;
    }

    public Localite() {
    }
    
    
}
