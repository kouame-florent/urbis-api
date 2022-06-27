/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionLegitimation.findMostRecent",
    query = "Select m FROM MentionLegitimation m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionLegitimation ma) "
            + " WHERE m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
@Table(name = "mention_legitimation")
public class MentionLegitimation extends Mention{
    
    
}
