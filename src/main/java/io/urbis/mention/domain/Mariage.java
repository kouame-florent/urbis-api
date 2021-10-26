/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_mariage")
public class Mariage extends Mention{
    
    public String lieu;
    public LocalDateTime date;
    public LocalDateTime dateDressage;
    
    public String epouseNom;
    public String epousePrenoms;
    public String epouseDomicile;
    public String epouseProfession;
    
    public String epouxNom;
    public String epouxPrenoms;
    public String epouxDomicile;
    public String epouxProfession;
}
