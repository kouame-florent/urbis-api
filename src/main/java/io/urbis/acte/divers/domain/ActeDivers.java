/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import io.urbis.acte.Acte;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Table(name = "acte_divers")
@Entity
public abstract class ActeDivers extends Acte{
    
    
    
}
