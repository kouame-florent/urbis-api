/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author florent
 */
@NamedQuery(name = "Registre.findMaxNumero", query = "Select max(r.numero) FROM Registre r")
@Table(name = "registre")
@Entity
public class Registre extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
       
    @Version
    public long version;
    
    @NotNull
    @Column(name = "type_registre",nullable = false)
    @Enumerated(EnumType.STRING)    
    public TypeRegistre typeRegistre;
    
    @NotNull
    @Column(nullable = false)
    @Size(max = 255)
    public String libelle;
   
    @NotNull
    @ManyToOne
    public Localite localite; 
    
    @NotNull
    @ManyToOne
    public Centre centre;
    
    @NotNull
    @Column(nullable = false)
    public int annee;
    
    @NotNull
    @Column(nullable = false)
    public long numero;
    
    @NotNull
    @ManyToOne
    public Tribunal tribunal;
    
    @NotNull
    @Column(name = "officier_etat_civil_id",nullable = false)
    @Size(max = 255)
    public String officierEtatCivilID;
    
    @NotNull
    @Column(name = "numero_premier_acte",nullable = false)
    public long numeroPremierActe; 
    
    
    @Column(name = "numero_dernier_acte",nullable = false)
    public long numeroDernierActe;
    
    @NotNull
    @Column(name = "nombre_de_feuillets",nullable = false)
    public long nombreDeFeuillets;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public StatutRegistre statut;
    
    public LocalDateTime dateAnnulation;
    
    @Size(max = 250)
    public String motifAnnulation;
    
    public Registre(){}

    public Registre(TypeRegistre typeRegistre, String libelle, Localite localite, Centre centre, 
            int annee, long numero, Tribunal tribunal, String officierEtatCivilID, 
            long numeroPremierActe, long numeroDernierActe, long nombreDeFeuillets,
            StatutRegistre statut) {
        
        this.typeRegistre = typeRegistre;
        this.libelle = libelle;
        this.localite = localite;
        this.centre = centre;
        this.annee = annee;
        this.numero = numero;
        this.tribunal = tribunal;
        this.officierEtatCivilID = officierEtatCivilID;
        this.numeroPremierActe = numeroPremierActe;
        this.numeroDernierActe = numeroDernierActe;
        this.nombreDeFeuillets = nombreDeFeuillets;
        this.statut = statut;
    }

   
   
}
