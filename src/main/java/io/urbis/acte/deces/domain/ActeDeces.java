/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.domain;

import io.urbis.acte.Acte;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import java.sql.Clob;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Table(name = "acte_deces",uniqueConstraints = { 
    @UniqueConstraint(columnNames = {"registre_id","numero"})
})
@Entity
public class ActeDeces extends Acte{
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "registre_id")
    public Registre registre;
    
    @Column(name = "numero")
    public int numero;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="enfant_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="enfant_prenoms")),
        @AttributeOverride(name="nomComplet",
                           column=@Column(name="nom_complet")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="enfant_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="enfant_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="enfant_date_naissance")),
        @AttributeOverride(name="localite",
                           column=@Column(name="enfant_localite")),
    })
    public Defunt defunt;
    
   
    
    
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
    
        
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="pere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="pere_prenoms")),
        @AttributeOverride(name="nomComplet",
                           column=@Column(name="pere_nom_complet")),
        @AttributeOverride(name="profession",
                           column=@Column(name="pere_profession")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="pere_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="pere_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="pere_date_naissance")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="pere_date_naissance")),
        @AttributeOverride(name="decede",
                           column=@Column(name="pere_decede")),
        @AttributeOverride(name="dateDeces",
                           column=@Column(name="pere_date_deces")),
        @AttributeOverride(name="lieuDeces",
                           column=@Column(name="pere_lieu_deces")),
        
        
    })
    public Pere pere;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="mere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="mere_prenoms")),
        @AttributeOverride(name="nomComplet",
                           column=@Column(name="mere_nom_complet")),
        @AttributeOverride(name="profession",
                           column=@Column(name="mere_profession")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="mere_lieu_naissance")),
        @AttributeOverride(name="nationalite",
                           column=@Column(name="mere_nationalite")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="mere_date_naissance")),
        @AttributeOverride(name="decede",
                           column=@Column(name="mere_decede")),
        @AttributeOverride(name="dateDeces",
                           column=@Column(name="mere_date_deces")),
        @AttributeOverride(name="lieuDeces",
                           column=@Column(name="mere_lieu_deces")),
        
        
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
        @AttributeOverride(name="domicile",
                           column=@Column(name="declarant_domicile")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="declarant_lieu_naissance")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="declarant_date_naissance")),
       
    })
    public Declarant declarant;
    
  
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    public StatutActeDeces statut;
    
    @Column(name = "date_dressage")
    public LocalDateTime dateDressage;
    
        
    @Lob
    @Column(name = "extrait_texte")
    public Clob extraitTexte;
    
    @Lob
    @Column(name = "copie_texte")
    public Clob copieTexte;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil; 

    public ActeDeces() {
    }

    public ActeDeces(Defunt defunt, Jugement jugement, Pere pere, Mere mere,
            Declarant declarant) {
        this.defunt = defunt;
        this.jugement = jugement;
        this.pere = pere;
        this.mere = mere;
        this.declarant = declarant;
        
    }
    
}
