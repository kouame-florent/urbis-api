/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.naissance.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionAnnulation.findMostRecent",
    query = "Select m FROM MentionAnnulation m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionAnnulation ma) "
            + " AND m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
public class MentionAnnulation extends MentionActeNaissance{
    
}
