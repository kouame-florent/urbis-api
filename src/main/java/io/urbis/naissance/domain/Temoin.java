/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Temoin implements Serializable{
    
    public String premierNom;
    public String premierPrenoms;
    public int premierAge;
    public String premierProfession;
    public String premierDomicile;
    
    public String deuxiemeNom;
    public String deuxiemePrenoms;
    public int deuxiemeAge;
    public String deuxiemeProfession;
    public String deuxiemeDomicile;
}
