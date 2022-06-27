/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

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
    @NamedQuery(name = "MentionDissolutionMariage.findMostRecent",
    query = "Select m FROM MentionDissolutionMariage m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionDissolutionMariage ma) "
            + " WHERE m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
@Table(name = "mention_dissolution_mariage")
public class MentionDissolutionMariage extends Mention{
  
    @Column(name = "mention_dissolution_mariage_date_jugement")
    public LocalDate dateJugement;
    
}
