/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.Acte;
import io.urbis.registre.domain.Registre;
import io.urbis.param.domain.OfficierEtatCivil;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */

@Table(name = "acte_mariage",uniqueConstraints = { 
    @UniqueConstraint(columnNames = {"registre_id","numero"})
})
@Entity
public class ActeMariage extends Acte{
        
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "registre_id")
    public Registre registre;
    
    @Column(name = "numero")
    public int numero;

    
    public LocalDateTime dateMariage;
    public String lieuMariage;
    public Regime regime;
    
    @Embedded
    public Epoux epoux;
    
    @Embedded
    public Epouse epouse;
    
    public OfficierEtatCivil officierEtatCivil;

    public ActeMariage() {
    }
    
    public ActeMariage(Epoux epoux, Epouse epouse) {
        this.epoux = epoux;
        this.epouse = epouse;
        
    }
    
    
}
