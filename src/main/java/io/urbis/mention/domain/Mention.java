/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.common.domain.BaseEntity;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.param.domain.OfficierEtatCivil;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Mention extends BaseEntity{
        
    @Column(nullable = false,name = "mention_date_dressage")
    public LocalDate dateDressage;
    
    @NotBlank
    @Lob
    @Column(nullable = false,name = "mention_decision")
    public String decision;
   
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "mention_officier_etat_civil_id")
    public OfficierEtatCivil officierEtatCivil;
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false,name = "mention_acte_naissance_id")
    public ActeNaissance acteNaissance;
}
