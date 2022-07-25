/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.common.domain.Acte;
import io.urbis.param.domain.OfficierEtatCivil;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */

@Table(name = "acte_mariage")
@Entity
public class ActeMariage extends Acte{
      
   
    @Column(name = "date_mariage",nullable = false)
    public LocalDateTime dateMariage;
    
    @Column(name = "lieu_mariage",nullable = false)
    public String lieuMariage;
    
    @Column(name = "regime",nullable = false)
    public Regime regime;
    
    @Column(name = "statut",nullable = false)
    public StatutActeMariage statut;    
        
    @Embedded
    public Epoux epoux;
    
    @Embedded
    public Epouse epouse;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil; 

    public ActeMariage() {
    }
    
    public ActeMariage(Epoux epoux, Epouse epouse) {
        this.epoux = epoux;
        this.epouse = epouse;
        
    }
    
    
}
