/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Mere implements Serializable{
    
    public boolean decede;
    public String nom;
    public String prenoms;
    public String profession;
    public String lieuNaissance;
    public LocalDate dateNaissance;
    public LocalDate dateDeces;
    public String lieuDeces;
 
}
