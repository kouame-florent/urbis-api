/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.quantum.urbis.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

/**
 *
 * @author florent
 */
@Entity
public class Registre extends PanacheEntity{
    public String libelle;
}
