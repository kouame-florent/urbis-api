/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.urbis.common.domain.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author florent
 */
@Table(name = "centre")
@Entity
public class Centre extends  BaseEntity{
     
    public String code;
    public String libelle;
    
    
}
