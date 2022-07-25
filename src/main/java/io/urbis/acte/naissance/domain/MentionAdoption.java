/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@NamedQueries({
    @NamedQuery(name = "MentionAdoption.findMostRecent",
    query = "Select m FROM MentionAdoption m WHERE m.dateDressage = (Select Max(ma.dateDressage) FROM MentionAdoption ma) "
            + " AND m.acteNaissance = :acteNaissance"
            
    ),
})
@Entity
//@Table(name = "mention_adoption")
public class MentionAdoption extends MentionActeNaissance{
    
    
}
