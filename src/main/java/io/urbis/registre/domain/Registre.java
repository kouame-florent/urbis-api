/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.domain;


import io.urbis.param.domain.Tribunal;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.urbis.common.domain.BaseEntity;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "Registre.findMaxNumero",
    query = "Select max(r.reference.numero) FROM Registre r WHERE r.typeRegistre = :typeRegistre "
            + "AND r.reference.annee = :annee"
    ),
    @NamedQuery(name = "Registre.findNumeroDernierActe",
    query = "Select max(r.numeroDernierActe) FROM Registre r WHERE r.typeRegistre = :typeRegistre "
            + "AND r.reference.annee = :annee"
    ),
    @NamedQuery(name = "Registre.findByUniqueConstraint",
    query = "Select r FROM Registre r WHERE r.typeRegistre = :typeRegistre AND r.reference.localite = :localite "
            + "AND r.reference.centre = :centre AND r.reference.annee = :annee AND r.reference.numero = :numero"
    ),
    @NamedQuery(name = "Registre.findWithMaxNumero",
    query = "SELECT r FROM Registre r WHERE r.typeRegistre = :typeRegistre"
            + " AND r.reference.annee = :annee AND r.statut = :statut AND r.reference.numero = "
            + " (SELECT MAX(r.reference.numero) FROM Registre r WHERE r.typeRegistre = :typeRegistre "
            + " AND r.reference.annee = :annee)"
    ),
    
})

@FilterDef(
    name = "anneeFilter", 
    parameters = @ParamDef(name = "anneeLimit", type = "int")
)
@Filter(
    name = "anneeFilter", 
    condition = "annee = :anneeLimit"
)
@FilterDef(
    name = "numeroFilter", 
    parameters = @ParamDef(name = "numeroLimit", type = "int")
)
@Filter(
    name = "numeroFilter", 
    condition = "numero = :numeroLimit"
)
@Table(name = "registre",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"type_registre","localite_id", "centre_id","annee","numero"})}
)
@Entity
public class Registre extends BaseEntity{
   
        
    @NotNull
    @Column(name = "type_registre",nullable = false)
    @Enumerated(EnumType.STRING)    
    public TypeRegistre typeRegistre;
    
    @NotNull
    @Column(nullable = false)
    @Size(max = 255)
    public String libelle;
    
    @NotNull
    @Embedded
    public Reference reference;
  
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public Tribunal tribunal;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "officier_etat_civil_id",nullable = false)
    public OfficierEtatCivil officierEtatCivil;
    
    @DecimalMin("1")
    @NotNull
    @Column(name = "numero_premier_acte",nullable = false)
    public int numeroPremierActe; 
    
    @DecimalMin("1")
    @Column(name = "numero_dernier_acte",nullable = false)
    public int numeroDernierActe;
    
    @Column(name = "numero_prochain_acte")
    public int numeroProchainActe;
    
    @DecimalMin("1")
    @NotNull
    @Column(name = "nombre_de_feuillets",nullable = false)
    public int nombreDeFeuillets;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public StatutRegistre statut;
    
    @Size(max = 250)
    public String observation;
    
    @Column(name = "date_annulation")
    public LocalDateTime dateAnnulation;
    
    @Size(max = 250)
    @Column(name = "motif_annulation")
    public String motifAnnulation;
    
    public Registre(){}

    public Registre(TypeRegistre typeRegistre, String libelle, Reference reference, Tribunal tribunal,
            OfficierEtatCivil officierEtatCivil, 
            int numeroPremierActe, int numeroDernierActe, int nombreDeFeuillets,
            StatutRegistre statut) {
        
        this.typeRegistre = typeRegistre;
        this.libelle = libelle;
        this.reference = reference;
        this.tribunal = tribunal;
        this.officierEtatCivil = officierEtatCivil;
        this.numeroPremierActe = numeroPremierActe;
        this.numeroDernierActe = numeroDernierActe;
        this.nombreDeFeuillets = nombreDeFeuillets;
        this.statut = statut;
    }

   
   
}
