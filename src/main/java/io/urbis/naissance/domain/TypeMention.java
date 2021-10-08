/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

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

@Table(name = "type_mention")
@Entity
public class TypeMention extends PanacheEntityBase {
    
    @Id
    public String code;
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
    
    @Version
    public long version;
    
    public String libelle;

    public TypeMention() {
    }
    

    public TypeMention(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    
    
}
