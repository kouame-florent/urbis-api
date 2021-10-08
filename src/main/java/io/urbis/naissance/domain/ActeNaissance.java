/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.urbis.registre.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author florent
 */
@Table(name = "acte_naissance")
@Entity
public class ActeNaissance extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
    
    @Version
    public long version;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    public Registre registre;
    
    public int numero;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="enfant_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="enfant_prenoms")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="enfant_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="enfant_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="enfant_date_naissance")),
        @AttributeOverride(name="localite",
                           column=@Column(name="enfant_localite")),
    })
    public Enfant enfant;
    
    @Column(name = "date_declaration")
    public LocalDateTime dateDeclaration;
    
    @Column(name = "date_dressage")
    public LocalDateTime dateDressage;
    
    @Column(name = "date_enregistrement")
    public LocalDateTime dateEnregistrement;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="date",
                           column=@Column(name="jugement_date")),
        @AttributeOverride(name="numero",
                           column=@Column(name="jugement_numero")),
        @AttributeOverride(name="tribunal",
                           column=@Column(name="jugement_tribunal")),
        
    })
    public Jugement jugement;
    
    @Column(name = "mode_declaration")
    @Enumerated(EnumType.ORDINAL)
    public ModeDeclaration modeDeclaration;
    
    @Column(name = "type_naissance")
    @Enumerated(EnumType.STRING)
    public TypeNaissance typeNaissance;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="pere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="pere_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="pere_profession")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="pere_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="pere_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="pere_date_naissance")),
        @AttributeOverride(name="dateDeces",
                           column=@Column(name="pere_date_deces")),
        @AttributeOverride(name="lieuDeces",
                           column=@Column(name="pere_lieu_deces")),
        @AttributeOverride(name="localite",
                           column=@Column(name="pere_localite")),
        @AttributeOverride(name="typePiece",
                           column=@Column(name="pere_type_piece")),
        @AttributeOverride(name="numeroPiece",
                           column=@Column(name="pere_numero_piece")),
        @AttributeOverride(name="datePiece",
                           column=@Column(name="pere_date_piece")),
        @AttributeOverride(name="lieuPiece",
                           column=@Column(name="pere_lieu_piece")),
    })
    public Pere pere;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="mere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="mere_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="mere_profession")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="mere_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="mere_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="mere_date_naissance")),
        @AttributeOverride(name="dateDeces",
                           column=@Column(name="mere_date_deces")),
        @AttributeOverride(name="lieuDeces",
                           column=@Column(name="mere_lieu_deces")),
        @AttributeOverride(name="localite",
                           column=@Column(name="mere_localite")),
        @AttributeOverride(name="typePiece",
                           column=@Column(name="mere_type_piece")),
        @AttributeOverride(name="numeroPiece",
                           column=@Column(name="mere_numero_piece")),
        @AttributeOverride(name="datePiece",
                           column=@Column(name="mere_date_piece")),
        @AttributeOverride(name="lieuPiece",
                           column=@Column(name="mere_lieu_piece")),
    })
    public Mere mere;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="lien",
                           column=@Column(name="declarant_lien")),
        @AttributeOverride(name="nom",
                           column=@Column(name="declarant_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="declarant_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="declarant_profession")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="declarant_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="declarant_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="declarant_date_naissance")),
        @AttributeOverride(name="localite",
                           column=@Column(name="declarant_localite")),
        @AttributeOverride(name="typePiece",
                           column=@Column(name="declarant_type_piece")),
        @AttributeOverride(name="numeroPiece",
                           column=@Column(name="declarant_numero_piece")),
        @AttributeOverride(name="datePiece",
                           column=@Column(name="declarant_date_piece")),
        @AttributeOverride(name="lieuPiece",
                           column=@Column(name="declarant_lieu_piece")),
    })
    public Declarant declarant;
    
    @Column(name = "statut")
    @Enumerated(EnumType.ORDINAL)
    public StatutActeNaissance statut;
    
    @Column(name = "motif_annulation")
    public String motifAnnulation;
    
    @Column(name = "nombre_copies_integrales")
    public int nombreCopiesIntegrales;
    
    @Column(name = "nombre_extraits")
    public int nombreExtraits;
    
    
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil;
    
} 
