/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.domain;

import io.urbis.registre.domain.Localite;
import io.urbis.registre.domain.Tribunal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "mention_legitimation")
public class Legitimation extends Mention{
    
    public String jugementNumero;
    public LocalDateTime jugementDateMariageJugement;
    public String jugementLieu;
    @ManyToOne
    public Localite jugementLocalite;
    
    @ManyToOne
    public Tribunal jugementTribunal;
    
    public LocalDateTime jugementDateDressage;
    
    public String pereNom;
    public String perePrenoms;
    public String pereDomicile;
    public String pereProfession;
    
    public String mereNom;
    public String merePrenoms;
    public String mereDomicile;
    public String mereProfession;
    
    
    
}
