/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;


import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionRectification.findMostRecent",
    query = "Select m FROM MentionRectification m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionRectification ma) "
            + " AND m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
public class MentionRectificationMariage extends MentionActeMariage{
    
}
