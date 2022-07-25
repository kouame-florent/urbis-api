/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import java.time.LocalDate;
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
    @NamedQuery(name = "MentionDeces.findMostRecent",
    query = "Select m FROM MentionDeces m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionDeces ma) "
            + " AND m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
//@Table(name = "mention_deces")
public class MentionDeces extends MentionActeNaissance{
    
    @Column(name = "deces_date")
    public LocalDate date;
    
    @Column(name = "deces_lieu")
    public String lieu;
    
    @Column(name = "deces_localite")
    public String localite;
    
}
