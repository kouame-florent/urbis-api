/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Table(name = "acte_divers_reconnaissance_enfant_adulterin",uniqueConstraints = { 
    @UniqueConstraint(columnNames = {"registre_id","numero"})
}) 
@Entity
public class ActeRecEnfantAdulterin extends ActeDivers {
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "registre_id")
    public Registre registre;
    
    @Column(name = "numero")
    public int numero;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="date",
                           column=@Column(name="reconnaissance_date")),
        @AttributeOverride(name="lieu",
                           column=@Column(name="reconnaissance_lieu")),
        @AttributeOverride(name="natureCirconscription",
                           column=@Column(name="reconnaissance_nature_circonscription")),
        @AttributeOverride(name="nomCirconscription",
                           column=@Column(name="reconnaissance_nom_circonscription")),
       
    })
    public Reconnaissance reconnaissance;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="numeroActe",
                           column=@Column(name="enfant_numero_acte")),
        @AttributeOverride(name="dateActe",
                           column=@Column(name="enfant_date_acte")),
        @AttributeOverride(name="nom",
                           column=@Column(name="enfant_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="enfant_prenoms")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="enfant_date_naissance")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="enfant_lieu_naissance")),
        @AttributeOverride(name="sexe",
                           column=@Column(name="enfant_sexe")),
       
    })
    public Enfant enfant;
    
    @Embedded
    @AttributeOverrides({
       
        @AttributeOverride(name="nom",
                           column=@Column(name="reconnaissant_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="reconnaissant_prenoms")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="reconnaissant_date_naissance")),
        @AttributeOverride(name="profession",
                           column=@Column(name="reconnaissant_profession")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="reconnaissant_domicile")),
       
    })
    public Reconnaissant reconnaissant;
    
    @Enumerated(EnumType.STRING)
    public StatutActeDivers statut;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil; 
    
    @Column(name = "consentement_epouse_nom")
    public String consentementEpouseNom;
    
    @Column(name = "consentement_epouse_prenoms")
    public String consentementEpousePrenoms;
    
    @Column(name = "consentement_epouse_type")
    @Enumerated(EnumType.STRING)
    public TypeConsentement consentementEpouseType;
    
}
