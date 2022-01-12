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
        @AttributeOverride(name="decedee",
                           column=@Column(name="decedee")),
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
        @AttributeOverride(name="decedee",
                           column=@Column(name="decedee")),
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
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="interprete_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="interprete_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="interprete_profession")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="interprete_date_naissance")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="interprete_domicile")),
        @AttributeOverride(name="langue",
                           column=@Column(name="interprete_langue")),
    })
    public Interprete interprete;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="premierNom",
                           column=@Column(name="temoin_1_nom")),
        @AttributeOverride(name="premierPrenoms",
                           column=@Column(name="temoin_1_prenoms")),
        @AttributeOverride(name="premierDateNaissance",
                           column=@Column(name="temoin_1_date_naissance")),
        @AttributeOverride(name="premierProfession",
                           column=@Column(name="temoin_1_profession")),
        @AttributeOverride(name="premierDomicile",
                           column=@Column(name="temoin_1_domicile")),
        @AttributeOverride(name="deuxiemeNom",
                           column=@Column(name="temoin_2_nom")),
        @AttributeOverride(name="deuxiemePrenoms",
                           column=@Column(name="temoin_2_prenoms")),
        @AttributeOverride(name="deuxiemeDateNaissance",
                           column=@Column(name="temoin_2_date_naissance")),
        @AttributeOverride(name="deuxiemeProfession",
                           column=@Column(name="temoin_2_profession")),
        @AttributeOverride(name="deuxiemeDomicile",
                           column=@Column(name="temoin_2_domicile")),
        
    })
    public Temoins temoins;
    
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
            Declarant declarant, Interprete interprete, Temoins temoins) {
        this.defunt = defunt;
        this.jugement = jugement;
        this.pere = pere;
        this.mere = mere;
        this.declarant = declarant;
        this.interprete = interprete;
        this.temoins = temoins;
    }
    
}
