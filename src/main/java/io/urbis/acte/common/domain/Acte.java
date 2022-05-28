/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.common.domain;

import io.urbis.common.domain.BaseEntity;
import io.urbis.registre.domain.Registre;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "Acte.findByDemandeCreteria",
            query = "Select a FROM Acte a WHERE a.numero = :numero "
                    + "AND a.registre.dateOuverture = :dateOuvertureRegistre "
                    + "AND a.registre.typeRegistre = :typeRegistre"
    ),
    
})
@Table(name = "acte",uniqueConstraints = { 
    @UniqueConstraint(columnNames = {"registre_id","numero"})
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Acte extends BaseEntity{
   
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "registre_id")
    public Registre registre;
    
    @Column(name = "numero")
    public int numero;
}
