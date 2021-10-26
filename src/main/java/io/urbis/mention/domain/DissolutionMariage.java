/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.registre.domain.Tribunal;
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
@Table(name = "mention_dissolution_mariage")
public class DissolutionMariage extends Mention{
    
    public String nom;
    public String prenoms;
    public String domicile;
    public String profession;
    public String lieu;
    public String dateMariage;
    
    public String numeroJugement;
    public LocalDateTime dateJugement;
    
    @Enumerated(EnumType.STRING)
    public TypeDissolutionMariage typeDissolution;
    
    @ManyToOne
    public Tribunal tribunal;
    public LocalDateTime dateDressage;
    
    
}
