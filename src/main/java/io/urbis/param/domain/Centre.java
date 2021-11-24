/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.common.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "centre")
@Entity
public class Centre extends  BaseEntity{
     
    public String code;
    public String libelle;
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "localite")
    public Localite localite;
    
    //public StatutParametre statut;
    
}
