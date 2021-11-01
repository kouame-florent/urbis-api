/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_mariage")
public class MentionMariage extends Mention{
    
    public String lieu;
    public LocalDate date;
       
    @Column(name = "conjoint_nom")
    public String conjointNom;
    
    @Column(name = "conjoint_prenoms")
    public String conjointPrenoms;
    
    @Column(name = "conjoint_domicile")
    public String conjointDomicile;
    
    @Column(name = "conjoint_profession")
    public String conjointProfession;
    
   
}
