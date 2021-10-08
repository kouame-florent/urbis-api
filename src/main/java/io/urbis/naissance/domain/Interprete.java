/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDate;
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
@Table(name = "interprete")
@Entity
public class Interprete extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    @Version
    public long version;
    
    public String nom;
    public String prenoms;
    public String profession;
    public LocalDateTime dateNaissance;
    public String domicile;
    public String langue;
} 
