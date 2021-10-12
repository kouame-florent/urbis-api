/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Temoins implements Serializable{
    
    public String premierNom;
    public String premierPrenoms;
    public LocalDateTime premierDateNaissance;
    public String premierProfession;
    public String premierDomicile;
    
    public String deuxiemeNom;
    public String deuxiemePrenoms;
    public LocalDateTime deuxiemeDateNaissance;
    public String deuxiemeProfession;
    public String deuxiemeDomicile;
}
