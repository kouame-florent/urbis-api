/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Table(name = "mention")
@Entity
public class Mention extends PanacheEntityBase{
    
    @Id
    public String id = UUID.randomUUID().toString();
    
    @ManyToOne
    public TypeMentionVariante typeMentionVariante;
}
