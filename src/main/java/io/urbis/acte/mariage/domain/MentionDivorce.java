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
    @NamedQuery(name = "MentionDivorce.findMostRecent",
    query = "Select m FROM MentionDivorce m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionDivorce ma) "
            + " AND m.acteMariage = :acteMariage"
            
    ),
})
@Entity
//@Table(name = "mention_divorce")
public class MentionDivorce extends MentionActeMariage{
    
}
