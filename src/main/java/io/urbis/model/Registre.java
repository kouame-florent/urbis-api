/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 *
 * @author florent
 */
@Table(name = "registre")
@Entity
public class Registre extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
       
    @Version
    public long version;
    
    @Column(name = "type_registre")
    @Enumerated(EnumType.STRING)    
    public TypeRegistre typeRegistre;
    
    @Size(max = 255)
    public String libelle;
   
    @ManyToOne
    public Localite localite;
    
    @ManyToOne
    public Centre centre;
    
    public long annee;
    
    public long numero;
    
    @ManyToOne
    public Tribunal tribunal;
    
    @Column(name = "officier_etat_civil_id")
    @Size(max = 255)
    public String officierEtatCivilID;
    
    @Column(name = "numero_premier_acte")
    public long numeroPremierActe; 
    
    @Column(name = "numero_dernier_acte")
    public long numeroDernierActe;
    
    @Column(name = "nombre_de_feuillets")
    public long nombreDeFeuillets;
    
}
