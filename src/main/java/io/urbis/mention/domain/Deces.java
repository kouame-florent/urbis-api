/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.registre.domain.Localite;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_deces")
public class Deces extends Mention{
    
    public LocalDateTime date;
    public String lieu;
    
    @ManyToOne
    public String localite;
    public LocalDateTime dateDressage;
}
