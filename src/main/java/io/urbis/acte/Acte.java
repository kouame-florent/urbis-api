/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte;

import io.urbis.common.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Table(name = "acte")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Acte extends BaseEntity{
   
}
