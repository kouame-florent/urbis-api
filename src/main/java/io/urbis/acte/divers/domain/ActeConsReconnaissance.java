/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import java.time.LocalDate;
import javax.persistence.Column;
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
@Table(name = "acte_divers_consentement_reconnaissance") 
@Entity
public class ActeConsReconnaissance extends ActeDivers{
    
    /*
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "registre_id")
    public Registre registre;
    
    @Column(name = "numero")
    public int numero;
    */
    
    
    @Enumerated(EnumType.STRING)
    public StatutActeDivers statut;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil; 
    
   
    
    @Column(name = "consentement_date")
    public LocalDate consentementDate;
    @Column(name = "consentement_nature_circonscription")
    public String  consentementNatureCirconscription;
    @Column(name = "consentement_nom_circonscription")
    public String  consentementNomCirconscription;
    
    
    @Column(name = "consentement_nom")
    public String  consentementNom;
     
    @Column(name = "consentement_prenoms")
    public String  consentementPrenoms;
    
     
    @Column(name = "consentement_domicile")
    public String  consentementDomicile;
    
   
    @Column(name = "consentement_profession")
    public String  consentementProfession;
        
    @Column(name = "enfant_consenti_date_naissance")
    public LocalDate enfantConsentiDateNaissance;
    @Column(name = "enfant_consenti_nom")
    public String  enfantConsentiNom;
    @Column(name = "enfant_consenti_prenoms")
    public String  enfantConsentiPrenoms;
    @Column(name = "enfant_consenti_profession")
    public String  enfantConsentiProfession;
    @Column(name = "enfant_consenti_domicile")
    public String  enfantConsentiDomicile;
    
    @Column(name = "contractant_nom")
    public String  contractantNom;
    @Column(name = "contractant_prenoms")
    public String  contractantPrenoms;
    @Column(name = "contractant_profession")
    public String  contractantProfession;
    @Column(name = "contractant_domicile")
    public String  contractantDomicile;
   
    
}
