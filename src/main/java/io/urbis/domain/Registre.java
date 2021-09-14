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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "Registre.findMaxNumero",
    query = "Select max(r.reference.numero) FROM Registre r WHERE r.typeRegistre = :typeRegistre AND r.reference.annee = :annee"
    ),
    @NamedQuery(name = "Registre.findNumeroDernierActe",
    query = "Select max(r.numeroDernierActe) FROM Registre r WHERE r.typeRegistre = :typeRegistre AND r.reference.annee = :annee"
    ),
    
})
@Table(name = "registre",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"type_registre","localite_id", "centre_id","annee","numero"})}
)
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
    
    @Embedded
    public Reference reference;
   
    /*
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public Localite localite; 
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public Centre centre;
    
    @NotNull
    @Column(nullable = false)
    public int annee;
    
    @NotNull
    @Column(nullable = false)
    public long numero;
*/
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public Tribunal tribunal;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil;
    
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
    
    @Size(max = 250)
    public String observation;
    
    public LocalDateTime dateAnnulation;
    
    @Size(max = 250)
    public String motifAnnulation;
    
    public Registre(){}

    public Registre(TypeRegistre typeRegistre, String libelle, Reference reference, Tribunal tribunal,
            OfficierEtatCivil officierEtatCivil, 
            long numeroPremierActe, long numeroDernierActe, long nombreDeFeuillets,
            StatutRegistre statut) {
        
        this.typeRegistre = typeRegistre;
        this.libelle = libelle;
        this.reference = reference;
       // this.reference.localite = localite;
       // this.reference.centre = centre;
       // this.reference.annee = annee;
       // this.reference.numero = numero;
        this.tribunal = tribunal;
        this.officierEtatCivil = officierEtatCivil;
        this.numeroPremierActe = numeroPremierActe;
        this.numeroDernierActe = numeroDernierActe;
        this.nombreDeFeuillets = nombreDeFeuillets;
        this.statut = statut;
    }

   
   
}
