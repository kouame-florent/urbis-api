/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.registre.domain.Localite;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_reconnaissance")
public class Reconnaissance extends Mention{
    
    public LocalDateTime date;
    public LocalDateTime dateDressage;
    public String lieu;
    @ManyToOne
    public Localite localite;
    
    public String pereNom;
    public String perePrenoms;
    public String pereDomocole;
    public String pereProfession;
    
    public String epouseNom;
    public String epousePrenoms;
    
    @Enumerated(EnumType.STRING)
    public TypeReconnaissance typeReconnaissance;
    
    
    
}
