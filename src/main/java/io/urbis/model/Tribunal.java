/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
@Table(name = "tribunal")
@Entity
public class Tribunal extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
       
    @Version
    public long version;
    
    public String code;
    public String libelle;
    
}
