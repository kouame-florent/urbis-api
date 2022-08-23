/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionRectificationDeces.findMostRecent",
    query = "Select m FROM MentionRectificationDeces m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionRectificationDeces ma) "
            + " AND m.acteMariage = :acteMariage"
            
    ),
})
@Entity
public class MentionRectificationDeces extends MentionActeDeces{
    
}
