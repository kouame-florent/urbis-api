/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.domain;

import io.urbis.acte.Acte;
import io.urbis.common.domain.BaseEntity;
import io.urbis.common.domain.TypeRegistre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Table(name = "demande",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"numero"})}
)
@Entity
public class Demande extends BaseEntity{
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="demandeur_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="demandeur_prenoms")),
        @AttributeOverride(name="numero",
                           column=@Column(name="demandeur_numero")),
        @AttributeOverride(name="email",
                           column=@Column(name="demandeur_email")),
        @AttributeOverride(name="typePiece",
                           column=@Column(name="demandeur_type_piece")),
        @AttributeOverride(name="numeroPiece",
                           column=@Column(name="demandeur_numero_piece")),
        @AttributeOverride(name="qualiteDemandeur",
                           column=@Column(name="demandeur_qualite")),
        
    })
    public Demandeur demandeur;
    
    @NotNull
    public int numero; 

    @NotNull
    @Column(name = "type_registre",nullable = false)
    @Enumerated(EnumType.STRING)   
    public TypeRegistre typeRegistre;

    @Column(name = "numero_acte")
    public int numeroActe;

    @Column(name = "date_ouverture_registre")
    public LocalDate dateOuvertureRegistre;

    @Column(name = "nombre_extraits")
    public int nombreExtraits;

    @Column(name = "nombre_copies")
    public int nombreCopies;
   
    @Column(name = "date_heure_demande")
    public LocalDateTime dateHeureDemande;
   
    @Column(name = "date_heure_rdv_retrait")
    public LocalDateTime dateHeureRdvRetrait;
    
    @ManyToOne
    public Acte acte;

    public Demande(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Demande() {
    }
    
    
}
