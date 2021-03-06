/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.domain;

import io.urbis.param.domain.Centre;
import io.urbis.param.domain.Localite;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Embeddable
public class Reference implements Serializable{
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "localite_id")
    public Localite localite; 
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "centre_id")
    public Centre centre;
    
    @DecimalMin("1900")
    @NotNull
    @Column(nullable = false)
    public int annee;
    
    @DecimalMin("1")
    @NotNull
    @Column(nullable = false)
    public int numero; 

    public Reference() {}
    
    public Reference(Localite localite, Centre centre, int annee, int numero) {
        this.localite = localite;
        this.centre = centre;
        this.annee = annee;
        this.numero = numero;
    }
    
    
}
