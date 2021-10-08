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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author florent
 */
@Table(name = "type_mention_variante")
@Entity
public class TypeMentionVariante extends PanacheEntityBase {
    
    @Id
    public String code;
    
    public LocalDateTime created = LocalDateTime.now();
    public LocalDateTime updated = LocalDateTime.now();
    
    @Version
    public long version;
    
    public String libelle;
    
    @ManyToOne
    public TypeMention typeMention;

    public TypeMentionVariante() {
    }

    public TypeMentionVariante(String code, String libelle, TypeMention typeMention) {
        this.code = code;
        this.libelle = libelle;
        this.typeMention = typeMention;
    }
    
    
}
