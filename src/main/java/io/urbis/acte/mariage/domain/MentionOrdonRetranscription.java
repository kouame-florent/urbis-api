/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.mariage.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionOrdonRetranscription.findMostRecent",
    query = "Select m FROM MentionOrdonRetranscription m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionOrdonRetranscription ma) "
            + " AND m.acteMariage = :acteMariage"
            
    ),
})
@Entity
//@Table(name = "mention_ordonnance_retranscription")
public class MentionOrdonRetranscription extends MentionActeMariage{
    
}
