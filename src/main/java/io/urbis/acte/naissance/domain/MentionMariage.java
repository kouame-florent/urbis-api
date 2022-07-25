/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionMariage.findMostRecent",
    query = "Select m FROM MentionMariage m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionMariage ma) "
            + " AND m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
//@Table(name = "mention_mariage")
public class MentionMariage extends MentionActeNaissance{
    
    @Column(name = "mariage_lieu")
    public String lieuMariage;
    
    @Column(name = "mariage_date")
    public LocalDate dateMariage;
       
    @Column(name = "mariage_conjoint_nom")
    public String conjointNom;
    
    @Column(name = "mariage_conjoint_prenoms")
    public String conjointPrenoms;
    
    @Column(name = "mariage_conjoint_domicile")
    public String conjointDomicile;
    
    @Column(name = "mariage_conjoint_profession")
    public String conjointProfession;
    
   
}
