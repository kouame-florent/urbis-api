/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.registre.domain.OfficierEtatCivil;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mention extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    @Version
    public long version;
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
    
    @NotBlank
    @Lob
    @Column(nullable = false)
    public String decision;
   
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public OfficierEtatCivil officierEtatCivil;
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    public ActeNaissance acteNaissance;
}
